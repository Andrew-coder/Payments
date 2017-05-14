package payments.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import payments.dao.*;
import payments.dao.exception.DaoException;
import payments.model.dto.payment.CardTransferData;
import payments.model.entity.BankAccount;
import payments.model.entity.Card;
import payments.model.entity.payment.PaymentTariff;
import payments.model.entity.payment.PaymentType;
import payments.model.entity.user.User;
import payments.service.exception.ServiceException;
import payments.service.impl.PaymentServiceImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceImplTest {
    @Mock
    private ConnectionWrapper wrapper;
    @Mock
    private CardDao cardDao;
    @Mock
    private BankAccountDao accountDao;
    @Mock
    private PaymentTariffDao tariffDao;
    @Mock
    private DaoFactory daoFactory;
    private PaymentService paymentService;
    private static final String SENDER_ACCOUNT_NUMBER="12345";
    private static final String RECIPIENT_ACCOUNT_NUMBER="54321";
    private static final String SENDER_CARD_NUMBER="123";
    private static final String RECIPIENT_CARD_NUMBER="321";

    @Before
    public void init() throws SQLException{
        PaymentTariff tariff = new PaymentTariff.Builder()
                .setId(1)
                .setPaymentRate(0.05)
                .setType(PaymentType.TRANSFER_WITHIN_THIS_BANK)
                .setFixedRate(BigDecimal.valueOf(5))
                .build();
        BankAccount recipientAccount = new BankAccount.Builder()
                .setId(1)
                .setAccountNumber(RECIPIENT_ACCOUNT_NUMBER)
                .setBalance(BigDecimal.valueOf(10000))
                .build();
        BankAccount senderAccount = new BankAccount.Builder()
                .setId(2)
                .setAccountNumber(SENDER_ACCOUNT_NUMBER)
                .setBalance(BigDecimal.valueOf(15000))
                .build();
        Card recipientCard = new Card.Builder()
                .setId(1)
                .setCardNumber(RECIPIENT_CARD_NUMBER)
                .setAccount(recipientAccount)
                .setCvv("")
                .setPin("")
                .setExpireDate(new Date())
                .setUser(new User())
                .build();
        Card senderCard = new Card.Builder()
                .setId(2)
                .setCardNumber(SENDER_CARD_NUMBER)
                .setAccount(senderAccount)
                .setCvv("312")
                .setPin("1111")
                .setExpireDate(new Date())
                .setUser(new User())
                .build();
        Answer<Optional<Card>> cardAnswer = new Answer<Optional<Card>>() {
            @Override
            public Optional<Card> answer(InvocationOnMock invocation) throws Throwable {
                String string = invocation.getArgumentAt(0, String.class);
                if(string.equals(SENDER_CARD_NUMBER))
                    return Optional.of(senderCard);
                if(string.equals(RECIPIENT_CARD_NUMBER))
                    return Optional.of(recipientCard);
                else return Optional.empty();
            }
        };
        Answer<Optional<BankAccount>> accountAnswer = new Answer() {
            @Override
            public Optional<BankAccount> answer(InvocationOnMock invocation) throws Throwable {
                String string = invocation.getArgumentAt(0, String.class);
                if(string.equals(SENDER_ACCOUNT_NUMBER))
                    return Optional.of(senderAccount);
                if(string.equals(RECIPIENT_ACCOUNT_NUMBER))
                    return Optional.of(recipientAccount);
                else return Optional.empty();
            }
        };

        when(tariffDao.findByPaymentType(any(PaymentType.class))).thenReturn(Optional.of(tariff));

        when(cardDao.findAll()).thenReturn(Arrays.asList(senderCard, recipientCard));
        when(cardDao.findCardByNumber(anyString())).thenAnswer(cardAnswer);
        when(cardDao.findById(senderCard.getId())).thenReturn(Optional.of(senderCard));
        when(cardDao.findById(recipientCard.getId())).thenReturn(Optional.of(recipientCard));

        when(accountDao.findAll()).thenReturn(Arrays.asList(senderAccount, recipientAccount));
        when(accountDao.findBankAccountByNumber(SENDER_ACCOUNT_NUMBER)).thenReturn(Optional.of(senderAccount));
        when(accountDao.findBankAccountByNumber(RECIPIENT_ACCOUNT_NUMBER)).thenReturn(Optional.of(recipientAccount));

        when(daoFactory.getConnection()).thenReturn(wrapper);
        when(daoFactory.getBankAccountDao(wrapper)).thenReturn(accountDao);
        when(daoFactory.getCardDao(wrapper)).thenReturn(cardDao);
        when(daoFactory.getPaymentTariffDao(wrapper)).thenReturn(tariffDao);
        paymentService = new PaymentServiceImpl();
        ((PaymentServiceImpl)paymentService).setDaoFactory(daoFactory);
    }

    @Test
    public void testSuccessfulTransferBetweenCards(){
        CardTransferData data = new CardTransferData.Builder()
                .setRecipientCard(RECIPIENT_CARD_NUMBER)
                .setSenderCard(SENDER_CARD_NUMBER)
                .setSum(300)
                .build();
        paymentService.transferBetweenCards(data);
        verify(cardDao, times(2)).findCardByNumber(anyString());
        verify(wrapper).close();
        verifySuccessTransaction();
    }

    @Test(expected = ServiceException.class)
    public void testTransferBetweenCardsWithWrongNumber(){
        CardTransferData data = new CardTransferData.Builder()
                .setRecipientCard("")
                .setSenderCard(SENDER_CARD_NUMBER)
                .setSum(300)
                .build();
        paymentService.transferBetweenCards(data);
        verify(cardDao, times(2)).findCardByNumber(anyString());
        verify(wrapper, never()).beginTransaction();
    }

    @Test(expected = ServiceException.class)
    public void testTransferBetweenCardsWithBlockCard(){
        CardTransferData data = new CardTransferData.Builder()
                .setRecipientCard(RECIPIENT_CARD_NUMBER)
                .setSenderCard(SENDER_CARD_NUMBER)
                .setSum(300)
                .build();
        Card senderCard = cardDao.findCardByNumber(SENDER_CARD_NUMBER).get();
        when(cardDao.findAllBlockedCards()).thenReturn(Arrays.asList(senderCard));
        paymentService.transferBetweenCards(data);
        verify(cardDao, times(2)).findCardByNumber(anyString());
        verify(wrapper, never()).beginTransaction();
    }

    @Test(expected = ServiceException.class)
    public void testTransferToTheSameAccount(){
        CardTransferData data = new CardTransferData.Builder()
                .setRecipientCard(RECIPIENT_CARD_NUMBER)
                .setSenderCard(RECIPIENT_CARD_NUMBER)
                .setSum(300)
                .build();
        paymentService.transferBetweenCards(data);
        verify(cardDao, times(2)).findCardByNumber(anyString());
        verify(wrapper, never()).beginTransaction();
    }

    @Test(expected = DaoException.class)
    public void testPerformTransactionWhenPaymentFailed(){
        CardTransferData data = new CardTransferData.Builder()
                .setRecipientCard(RECIPIENT_CARD_NUMBER)
                .setSenderCard(SENDER_CARD_NUMBER)
                .setSum(300)
                .build();
        doThrow(DaoException.class).when(accountDao).update(any(BankAccount.class));
        paymentService.transferBetweenCards(data);
        verify(wrapper).beginTransaction();
        verify(wrapper).close();
        verify(wrapper, never()).commitTransaction();
    }

    public void verifySuccessTransaction(){
        verify(wrapper).beginSerializableTransaction();
        verify(wrapper).commitTransaction();
        verify(accountDao, times(2)).update(any(BankAccount.class));
    }
}
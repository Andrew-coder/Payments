package payments.service.impl;

import org.apache.log4j.Logger;
import payments.dao.BankAccountDao;
import payments.dao.CardDao;
import payments.dao.ConnectionWrapper;
import payments.dao.DaoFactory;
import payments.model.dto.payment.CardTransferData;
import payments.model.dto.payment.RefillData;
import payments.model.entity.BankAccount;
import payments.model.entity.Card;
import payments.service.CardService;
import payments.service.exception.ServiceException;
import payments.utils.constants.ErrorMessages;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CardServiceImpl implements CardService{
    private static final Logger logger = Logger.getLogger(CardServiceImpl.class);

    private DaoFactory daoFactory = DaoFactory.getInstance();

    private CardServiceImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static class InstanceHolder {
        private static final CardService instance = new CardServiceImpl(DaoFactory.getInstance());
    }

    public static CardService getInstance(){
        return InstanceHolder.instance;
    }

    @Override
    public Optional<Card> findById(long id) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            return cardDao.findById(id);
        }
    }

    @Override
    public List<Card> findAll() {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            return cardDao.findAll();
        }
    }

    @Override
    public void create(Card card) {

    }

    @Override
    public void block(long id) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            if(!isCardBlocked(id)){
                cardDao.blockCard(id);
            }
        }
    }

    @Override
    public void refillCard(RefillData data) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            BankAccountDao accountDao = daoFactory.getBankAccountDao(wrapper);
            Card card = findCardByIdOrThrowException(data.getIdRefilledCard(), cardDao);
            Optional<Card> sourceCard = cardDao.findCardByNumber(data.getCardNumber());
            if(sourceCard.isPresent()){
                checkCardInfo(sourceCard, data);
                BankAccount recipientAccount = card.getAccount();
                BankAccount senderAccount  = sourceCard.get().getAccount();
                senderAccount.setBalance(decreaseAccountBalance(senderAccount, data.getSum()));
                recipientAccount.setBalance(increaseAccountBalance(recipientAccount, data.getSum()));
                wrapper.beginTransaction();
                accountDao.update(senderAccount);
                accountDao.update(recipientAccount);
                wrapper.commitTransaction();
            }
            else {
                BankAccount account = card.getAccount();
                BigDecimal newBalance = increaseAccountBalance(account, data.getSum());
                account.setBalance(newBalance);
                accountDao.update(account);
            }
        }
    }

    @Override
    public void transferBetweenCards(CardTransferData transferData) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            BankAccountDao accountDao = daoFactory.getBankAccountDao(wrapper);
            Card senderCard = findCardByNumberOrThrowException(transferData.getSenderCard(), cardDao);
            Card recipientCard = findCardByNumberOrThrowException(transferData.getRecipientCard(), cardDao);
            BankAccount senderAccount = senderCard.getAccount();
            BankAccount recipientAccount  = recipientCard.getAccount();
            senderAccount.setBalance(decreaseAccountBalance(senderAccount, transferData.getSum()));
            recipientAccount.setBalance(increaseAccountBalance(recipientAccount, transferData.getSum()));
            wrapper.beginTransaction();
            accountDao.update(senderAccount);
            accountDao.update(recipientAccount);
            wrapper.commitTransaction();
        }
    }

    @Override
    public boolean isCardBlocked(long id) {
        try(ConnectionWrapper wrapper= daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            boolean result = cardDao.findAllBlockedCards()
                    .stream()
                    .mapToLong(Card::getId)
                    .anyMatch(cardId -> cardId==id);
            return result;
        }
    }

    private Card findCardByIdOrThrowException(long id, CardDao cardDao){
        Optional<Card> card = cardDao.findById(id);
        if(!card.isPresent()){
            logger.error(ErrorMessages.CARD_NOT_EXIST);
            throw new ServiceException(ErrorMessages.CARD_NOT_EXIST);
        }
        return card.get();
    }

    private Card findCardByNumberOrThrowException(String number, CardDao cardDao){
        Optional<Card> card = cardDao.findCardByNumber(number);
        if(!card.isPresent()){
            logger.error(ErrorMessages.CARD_NOT_EXIST);
            throw new ServiceException(ErrorMessages.CARD_NOT_EXIST);
        }
        return card.get();
    }

    private void checkCardInfo(Optional<Card> card, RefillData data){
        card.filter(c -> c.getPin().equals(data.getPin()))
                .filter(c -> c.getCvv().equals(data.getCvv()))
                .filter(c -> compareDates(c.getExpireDate().toString(), data.getExpireDate()))
                .orElseThrow(() -> new ServiceException(ErrorMessages.WRONG_CARD_DATA));
    }

    private boolean compareDates(String str1, String str2){
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = format.parse(str1);
            Date date2 = format.parse(str2);
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);
            return cal1.equals(cal2);
            //return date1.compareTo(date2)==0;
        }
        catch (ParseException ex){}
        return false;
    }

    private BigDecimal increaseAccountBalance(BankAccount account, double sum){
        return account.getBalance().add(new BigDecimal(sum));
    }

    private BigDecimal decreaseAccountBalance(BankAccount account, double sum){
        return account.getBalance().subtract(new BigDecimal(sum));
    }
}
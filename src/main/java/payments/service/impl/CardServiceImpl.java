package payments.service.impl;

import org.apache.log4j.Logger;
import payments.dao.*;
import payments.model.dto.payment.AccountTransferData;
import payments.model.dto.payment.CardTransferData;
import payments.model.dto.payment.RefillData;
import payments.model.entity.BankAccount;
import payments.model.entity.Card;
import payments.model.entity.payment.PaymentTariff;
import payments.model.entity.payment.PaymentType;
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
    private static final double ZERO = 0.0;
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
    public List<Card> findCardsByUser(long id) {
        try (ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            return cardDao.findCardsByUser(id);
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
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            cardDao.create(card);
        }
    }

    @Override
    public void blockCard(long id) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            if(!isCardBlocked(id)){
                cardDao.blockCard(id);
            }
        }
    }

    @Override
    public void unblockCard(long id) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            if(!isCardBlocked(id)){
                cardDao.unblockCard(id);
            }
        }
    }

    @Override
    public void refillCard(RefillData data) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            BankAccountDao accountDao = daoFactory.getBankAccountDao(wrapper);
            PaymentTariffDao tariffDao = daoFactory.getPaymentTariffDao(wrapper);
            Card card = findCardByIdOrThrowException(data.getIdRefilledCard(), cardDao);
            Optional<Card> sourceCard = cardDao.findCardByNumber(data.getCardNumber());
            if(sourceCard.isPresent()){
                checkCardInfo(sourceCard, data);
                checkCardIsNotBlocked(sourceCard.get().getId(), cardDao);
                BankAccount recipientAccount = card.getAccount();
                BankAccount senderAccount  = sourceCard.get().getAccount();
                BigDecimal senderBalance = calculateBalanceByTariff(senderAccount, data.getSum(),
                        tariffDao, PaymentType.REFILL);
                senderAccount.setBalance(senderBalance);
                recipientAccount.setBalance(increaseAccountBalance(recipientAccount, data.getSum()));
                performTransaction(senderAccount, recipientAccount, wrapper, accountDao);
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
    public void transferBetweenCards(CardTransferData data) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            BankAccountDao accountDao = daoFactory.getBankAccountDao(wrapper);
            PaymentTariffDao tariffDao = daoFactory.getPaymentTariffDao(wrapper);
            Card senderCard =
                    findCardByNumberOrThrowException(data.getSenderCard(), cardDao);
            Card recipientCard =
                    findCardByNumberOrThrowException(data.getRecipientCard(), cardDao);
            checkCardIsNotBlocked(senderCard.getId(), cardDao);
            BankAccount senderAccount = senderCard.getAccount();
            BankAccount recipientAccount  = recipientCard.getAccount();
            BigDecimal senderBalance = calculateBalanceByTariff(senderAccount, data.getSum(),
                    tariffDao, PaymentType.TRANSFER_WITHIN_THIS_BANK);
            senderAccount.setBalance(senderBalance);
            recipientAccount.setBalance(increaseAccountBalance(recipientAccount, data.getSum()));
            performTransaction(senderAccount, recipientAccount, wrapper, accountDao);
        }
    }

    @Override
    public void accountTransfer(AccountTransferData data) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            BankAccountDao accountDao = daoFactory.getBankAccountDao(wrapper);
            PaymentTariffDao tariffDao = daoFactory.getPaymentTariffDao(wrapper);
            Card senderCard = findCardByNumberOrThrowException(data.getSenderCard(), cardDao);
            checkCardIsNotBlocked(senderCard.getId(), cardDao);
            BankAccount senderAccount = senderCard.getAccount();
            BankAccount recipientAccount =
                    findAccountByNumberOrThrowException(data.getAccountNumber(), accountDao);
            BigDecimal senderBalance = calculateBalanceByTariff(senderAccount, data.getSum(),
                    tariffDao, PaymentType.TRANSFER_WITHIN_THIS_BANK);
            senderAccount.setBalance(senderBalance);
            recipientAccount.setBalance(increaseAccountBalance(recipientAccount, data.getSum()));
            performTransaction(senderAccount, recipientAccount, wrapper, accountDao);
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

    private void checkCardIsNotBlocked(long id, CardDao cardDao){
        cardDao.findAllBlockedCards()
                .stream()
                .mapToLong(Card::getId)
                .filter(cardId -> cardId==id)
                .findFirst()
                .ifPresent((a)->{throw new ServiceException(ErrorMessages.CARD_IS_BLOCKED);});
    }

    private void performTransaction(BankAccount sender, BankAccount recipient,
                                    ConnectionWrapper wrapper, BankAccountDao accountDao){
        checkIsNotTheSameAccount(sender, recipient);
        wrapper.beginTransaction();
        accountDao.update(sender);
        accountDao.update(recipient);
        wrapper.commitTransaction();
    }

    private BigDecimal calculateBalanceByTariff(BankAccount account, double sum,
                                             PaymentTariffDao tariffDao, PaymentType type){
        PaymentTariff tariff = tariffDao.findByPaymentType(type)
                .orElseThrow(ServiceException::new);
        BigDecimal fixedRate = tariff.getFixedRate();
        BigDecimal currentBalance = account.getBalance();
        BigDecimal paymentRateValue = new BigDecimal(tariff.getPaymentRate())
                .multiply(new BigDecimal(sum));
        BigDecimal remainder = currentBalance
                .subtract(fixedRate)
                .subtract(paymentRateValue)
                .subtract(new BigDecimal(sum));
        if(remainder.doubleValue()<ZERO){
            throw new ServiceException(ErrorMessages.NOT_ENOUGH_MONEY);
        }
        return remainder;
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

    private BankAccount findAccountByNumberOrThrowException(String number, BankAccountDao accountDao){
        Optional<BankAccount> account = accountDao.findBankAccountByNumber(number);
        if(!account.isPresent()){
            logger.error(ErrorMessages.ACCOUNT_NOT_EXIST);
            throw new ServiceException(ErrorMessages.ACCOUNT_NOT_EXIST);
        }
        return account.get();
    }

    private void checkCardInfo(Optional<Card> card, RefillData data){
        card.filter(c -> c.getPin().equals(data.getPin()))
                .filter(c -> c.getCvv().equals(data.getCvv()))
                .filter(c -> compareDates(c.getExpireDate().toString(), data.getExpireDate()))
                .orElseThrow(() -> new ServiceException(ErrorMessages.WRONG_CARD_DATA));
    }

    private void checkIsNotTheSameAccount(BankAccount sender, BankAccount recipient){
        if(sender.getAccountNumber().equals(recipient.getAccountNumber())){
            throw new ServiceException(ErrorMessages.TRANSFER_TO_THE_SAME_ACCOUNT);
        }
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
        }
        catch (ParseException ex){}
        return false;
    }

    private BigDecimal increaseAccountBalance(BankAccount account, double sum){
        return account.getBalance().add(new BigDecimal(sum));
    }
}
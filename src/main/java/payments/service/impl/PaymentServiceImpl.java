package payments.service.impl;

import org.apache.log4j.Logger;
import payments.dao.*;
import payments.model.dto.payment.AccountTransferData;
import payments.model.dto.payment.CardTransferData;
import payments.model.dto.payment.RefillData;
import payments.model.entity.BankAccount;
import payments.model.entity.Card;
import payments.model.entity.payment.Payment;
import payments.model.entity.payment.PaymentTariff;
import payments.model.entity.payment.PaymentType;
import payments.service.PaymentService;
import payments.service.exception.ServiceException;
import payments.utils.constants.ErrorMessages;

import java.util.List;
import java.util.Optional;

public class PaymentServiceImpl implements PaymentService{
    private static final Logger logger = Logger.getLogger(PaymentServiceImpl.class);

    private DaoFactory daoFactory = DaoFactory.getInstance();

    private PaymentServiceImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static class InstanceHolder {
        private static final PaymentService instance = new PaymentServiceImpl(DaoFactory.getInstance());
    }

    public static PaymentService getInstance(){
        return InstanceHolder.instance;
    }

    @Override
    public Optional<Payment> findById(long id) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            PaymentDao paymentDao = daoFactory.getPaymentDao(wrapper);
            return paymentDao.findById(id);
        }
    }

    @Override
    public List<Payment> findAll() {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            PaymentDao paymentDao = daoFactory.getPaymentDao(wrapper);
            return paymentDao.findAll();
        }
    }

    @Override
    public void saveRefillPayment(Payment payment, RefillData data) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            PaymentDao paymentDao = daoFactory.getPaymentDao(wrapper);
            PaymentTariffDao tariffDao = daoFactory.getPaymentTariffDao(wrapper);
            Card refilledCard = findCardByIdOrThrowException(data.getIdRefilledCard(), cardDao);
            payment.setRecipient(refilledCard.getAccount());
            PaymentTariff tariff =
                    tariffDao.findByPaymentType(PaymentType.REFILL)
                    .orElseThrow(ServiceException::new);
            payment.setTariff(tariff);
            paymentDao.create(payment);
        }
    }

    @Override
    public void saveCardTransfer(Payment payment, CardTransferData data) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            PaymentDao paymentDao = daoFactory.getPaymentDao(wrapper);
            PaymentTariffDao tariffDao = daoFactory.getPaymentTariffDao(wrapper);
            Card recipientCard = findCardByNumberOrThrowException(data.getRecipientCard(), cardDao);
            Card senderCard = findCardByNumberOrThrowException(data.getSenderCard(), cardDao);
            payment.setRecipient(recipientCard.getAccount());
            payment.setSender(senderCard.getAccount());
            PaymentTariff tariff =
                    tariffDao.findByPaymentType(PaymentType.TRANSFER_WITHIN_THIS_BANK)
                            .orElseThrow(ServiceException::new);
            payment.setTariff(tariff);
            paymentDao.create(payment);
        }
    }

    @Override
    public void saveAccountTransfer(Payment payment, AccountTransferData data) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            BankAccountDao accountDao = daoFactory.getBankAccountDao(wrapper);
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            PaymentDao paymentDao = daoFactory.getPaymentDao(wrapper);
            PaymentTariffDao tariffDao = daoFactory.getPaymentTariffDao(wrapper);
            Card senderCard = findCardByNumberOrThrowException(data.getSenderCard(), cardDao);
            BankAccount recipientAccount = findAccountByNumberOrThrowException(data.getAccountNumber(), accountDao);
            payment.setSender(senderCard.getAccount());
            payment.setRecipient(recipientAccount);
            PaymentTariff tariff =
                    tariffDao.findByPaymentType(PaymentType.TRANSFER_WITHIN_THIS_BANK)
                            .orElseThrow(ServiceException::new);
            payment.setTariff(tariff);
            paymentDao.create(payment);
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

    private BankAccount findAccountByNumberOrThrowException(String number, BankAccountDao accountDao){
        Optional<BankAccount> account = accountDao.findBankAccountByNumber(number);
        if(!account.isPresent()){
            logger.error(ErrorMessages.ACCOUNT_NOT_EXIST);
            throw new ServiceException(ErrorMessages.ACCOUNT_NOT_EXIST);
        }
        return account.get();
    }
}
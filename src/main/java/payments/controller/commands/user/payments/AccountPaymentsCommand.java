package payments.controller.commands.user.payments;

import org.apache.log4j.Logger;
import payments.controller.commands.CommandExecutor;
import payments.controller.validators.AccountTransferValidator;
import payments.controller.validators.Errors;
import payments.model.dto.payment.AccountTransferData;
import payments.model.entity.payment.Payment;
import payments.service.CardService;
import payments.service.PaymentService;
import payments.service.impl.CardServiceImpl;
import payments.service.impl.PaymentServiceImpl;
import payments.utils.constants.Attributes;
import payments.utils.constants.LoggerMessages;
import payments.utils.constants.MessageKeys;
import payments.utils.constants.PagesPath;
import payments.utils.extractors.RequestParamExtractor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class AccountPaymentsCommand extends CommandExecutor {
    private static final Logger logger = Logger.getLogger(AccountPaymentsCommand.class);
    private CardService cardService = CardServiceImpl.getInstance();
    private PaymentService paymentService = PaymentServiceImpl.getInstance();
    private AccountTransferValidator accountValidator;
    private RequestParamExtractor paramExtractor;

    public AccountPaymentsCommand() {
        super(PagesPath.PAYMENTS_PAGE);
        accountValidator = new AccountTransferValidator();
        paramExtractor = new RequestParamExtractor();
    }

    @Override
    public String performExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Errors errors = new Errors();
        saveAccountTransferDataToRequest(request);
        AccountTransferData transferData = extractAccountTransferDataFromRequest(request);
        errors.addErrors(accountValidator.validate(transferData).getErrors());
        if(errors.hasErrors()){
            processErrors(request, errors);
            request.getRequestDispatcher(PagesPath.PAYMENTS_PAGE).forward(request, response);
            return PagesPath.FORWARD;
        }
        cardService.accountTransfer(transferData);
        Payment payment = extractPaymentFromAccountTransferData(transferData);
        paymentService.saveAccountTransfer(payment, transferData);
        logger.info(String.format(LoggerMessages.SUCCESSFULL_ACCOUNT_TRANSFER));
        clearAccountTransferDataFromRequest(request);
        request.setAttribute(Attributes.CONFIRM_MESSAGE, MessageKeys.SUCCESSFUL_PAYMENT);
        request.getRequestDispatcher(PagesPath.CONFIRMATION_PAGE).forward(request, response);
        return PagesPath.FORWARD;
    }

    private AccountTransferData extractAccountTransferDataFromRequest(HttpServletRequest request){
        AccountTransferData.Builder builder = new AccountTransferData.Builder()
                .setAccountNumber(request.getParameter("account_number"))
                .setMfo(request.getParameter("mfo"))
                .setPaymentPurpose(request.getParameter("purpose"))
                .setSenderCard(request.getParameter("cards"))
                .setUsreou(request.getParameter("usreou"))
                .setSum(paramExtractor.extractDouble(request, "sum"));
        return builder.build();
    }

    private Payment extractPaymentFromAccountTransferData(AccountTransferData data){
        return new Payment.Builder()
                .setSum(BigDecimal.valueOf(data.getSum()))
                .setCurrentDate()
                .setMfo(data.getMfo())
                .setUsreou(data.getUsreou())
                .setPaymentPurpose(data.getPaymentPurpose())
                .build();
    }

    private void saveAccountTransferDataToRequest(HttpServletRequest request){
        request.setAttribute(Attributes.PREVIOUS_ACCOUNT_NUMBER, request.getParameter("account_number"));
        request.setAttribute(Attributes.PREVIOUS_MFO, request.getParameter("mfo"));
        request.setAttribute(Attributes.PREVIOUS_USREOU, request.getParameter("usreou"));
        request.setAttribute(Attributes.PREVIOUS_CARD_NUMBER, request.getParameter("cards"));
        request.setAttribute(Attributes.PREVIOUS_PURPOSE, request.getParameter("purpose"));
        request.setAttribute(Attributes.PREVIOUS_SUM, request.getParameter("sum"));
        request.setAttribute(Attributes.TAB, Attributes.ACCOUNT_TAB);
    }

    private void clearAccountTransferDataFromRequest(HttpServletRequest request){
        request.removeAttribute(Attributes.PREVIOUS_ACCOUNT_NUMBER);
        request.removeAttribute(Attributes.PREVIOUS_MFO);
        request.removeAttribute(Attributes.PREVIOUS_USREOU);
        request.removeAttribute(Attributes.PREVIOUS_CARD_NUMBER);
        request.removeAttribute(Attributes.PREVIOUS_PURPOSE);
        request.removeAttribute(Attributes.PREVIOUS_SUM);
        request.removeAttribute(Attributes.TAB);
    }

    private void processErrors(HttpServletRequest request, Errors errors){
        logger.error("Wrong input data in account payment info");
        request.setAttribute(Attributes.ERRORS, errors);
    }
}
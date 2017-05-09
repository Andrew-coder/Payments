package payments.controller.commands.user.payments;

import org.apache.log4j.Logger;
import payments.controller.commands.CommandExecutor;
import payments.controller.validators.CardTransferValidator;
import payments.controller.validators.Errors;
import payments.model.dto.payment.CardTransferData;
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

public class CardPaymentsCommand extends CommandExecutor {
    private static final Logger logger = Logger.getLogger(CardPaymentsCommand.class);
    private CardService cardService = CardServiceImpl.getInstance();
    private PaymentService paymentService = PaymentServiceImpl.getInstance();
    private CardTransferValidator cardValidator;
    private RequestParamExtractor paramExtractor;

    public CardPaymentsCommand() {
        super(PagesPath.PAYMENTS_PAGE);
        cardValidator = new CardTransferValidator();
        paramExtractor = new RequestParamExtractor();
    }

    @Override
    public String performExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Errors errors = new Errors();
        saveCardTransferDataToRequest(request);
        CardTransferData cardData = extractCardTransferDataFromRequest(request);
        errors.addErrors(cardValidator.validate(cardData).getErrors());
        if(errors.hasErrors()){
            processErrors(request, errors);
            request.getRequestDispatcher(PagesPath.PAYMENTS_PAGE).forward(request, response);
            return PagesPath.FORWARD;
        }
        cardService.transferBetweenCards(cardData);
        Payment payment = extractPaymentFromCardTransferData(cardData);
        paymentService.saveCardTransfer(payment, cardData);
        logger.info(String.format(LoggerMessages.SUCCESSFULL_CARD_TRANSFER));
        clearCardTransferDataFromRequest(request);
        request.setAttribute(Attributes.CONFIRM_MESSAGE, MessageKeys.SUCCESSFUL_PAYMENT);
        request.getRequestDispatcher(PagesPath.CONFIRMATION_PAGE).forward(request, response);
        return PagesPath.FORWARD;
    }

    private CardTransferData extractCardTransferDataFromRequest(HttpServletRequest request){
        CardTransferData.Builder builder = new CardTransferData.Builder()
                .setSenderCard(request.getParameter("cards"))
                .setRecipientCard(request.getParameter("target_card"))
                .setPaymentPurpose("purpose")
                .setSum(paramExtractor.extractDouble(request, "sum"));
        return builder.build();
    }

    private Payment extractPaymentFromCardTransferData(CardTransferData data){
        return new Payment.Builder()
                .setSum(BigDecimal.valueOf(data.getSum()))
                .setCurrentDate()
                .setPaymentPurpose(data.getPaymentPurpose())
                .build();
    }

    private void saveCardTransferDataToRequest(HttpServletRequest request){
        request.setAttribute(Attributes.PREVIOUS_CARD_NUMBER, request.getParameter("cards"));
        request.setAttribute(Attributes.PREVIOUS_TARGET_CARD, request.getParameter("target_card"));
        request.setAttribute(Attributes.PREVIOUS_PURPOSE, request.getParameter("purpose"));
        request.setAttribute(Attributes.PREVIOUS_SUM, request.getParameter("sum"));
    }

    private void clearCardTransferDataFromRequest(HttpServletRequest request){
        request.removeAttribute(Attributes.PREVIOUS_CARD_NUMBER);
        request.removeAttribute(Attributes.PREVIOUS_TARGET_CARD);
        request.removeAttribute(Attributes.PREVIOUS_PURPOSE);
        request.removeAttribute(Attributes.PREVIOUS_SUM);
    }

    private void processErrors(HttpServletRequest request, Errors errors){
        logger.error("Wrong input data in card payment info");
        request.setAttribute(Attributes.ERRORS, errors);
    }
}
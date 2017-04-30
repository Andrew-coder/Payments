package payments.controller.commands.user.payments;

import org.apache.log4j.Logger;
import payments.controller.commands.CommandExecutor;
import payments.controller.validators.CardTransferValidator;
import payments.controller.validators.Errors;
import payments.model.dto.payment.CardTransferData;
import payments.service.CardService;
import payments.service.impl.CardServiceImpl;
import payments.utils.constants.Attributes;
import payments.utils.constants.LoggerMessages;
import payments.utils.constants.PagesPath;
import payments.utils.extractors.RequestParamExtractor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CardPaymentsCommand extends CommandExecutor {
    private static final Logger logger = Logger.getLogger(CardPaymentsCommand.class);
    private CardService cardService = CardServiceImpl.getInstance();
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
        CardTransferData cardData = extractCardTransferDataFromRequest(request);
        errors.addErrors(cardValidator.validate(cardData).getErrors());
        if(errors.hasErrors()){
            saveCardTransferDataToRequest(request);
            processErrors(request, errors);
            request.getRequestDispatcher(PagesPath.PAYMENTS_PAGE).forward(request, response);
            return PagesPath.FORWARD;
        }
        cardService.transferBetweenCards(cardData);
        logger.info(String.format(LoggerMessages.SUCCESSFULL_CARD_TRANSFER));
        request.setAttribute(Attributes.CONFIRM_MESSAGE, LoggerMessages.SUCCESSFULL_CARD_TRANSFER);
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

    private void saveCardTransferDataToRequest(HttpServletRequest request){
        request.setAttribute(Attributes.PREVIOUS_CARD_NUMBER, request.getParameter("cards"));
        request.setAttribute(Attributes.PREVIOUS_TARGET_CARD, request.getParameter("target_card"));
        request.setAttribute(Attributes.PREVIOUS_PURPOSE, request.getParameter("purpose"));
        request.setAttribute(Attributes.PREVIOUS_SUM, request.getParameter("sum"));
    }

    private void processErrors(HttpServletRequest request, Errors errors){
        logger.error("Wrong input data in card payment info");
        request.setAttribute(Attributes.ERRORS, errors);
    }
}
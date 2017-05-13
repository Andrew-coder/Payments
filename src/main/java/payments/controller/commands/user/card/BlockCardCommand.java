package payments.controller.commands.user.card;

import payments.controller.commands.CommandExecutor;
import payments.service.PaymentService;
import payments.service.impl.PaymentServiceImpl;
import payments.utils.constants.Attributes;
import payments.utils.constants.PagesPath;
import payments.utils.extractors.RequestParamExtractor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlockCardCommand extends CommandExecutor {
    private PaymentService paymentService = PaymentServiceImpl.getInstance();
    private RequestParamExtractor requestExtractor = new RequestParamExtractor();

    public BlockCardCommand() {
        super(PagesPath.CARDS_PAGE);
    }

    @Override
    public String performExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long cardId = requestExtractor.extractLong(request, Attributes.CARD_ID);
        paymentService.blockCard(cardId);
        return PagesPath.CARDS_MANAGE;
    }
}

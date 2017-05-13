package payments.controller.commands.user.payments;

import payments.controller.commands.CommandExecutor;
import payments.model.entity.Card;
import payments.service.PaymentService;
import payments.service.impl.PaymentServiceImpl;
import payments.utils.constants.Attributes;
import payments.utils.constants.PagesPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class PaymentsCommand extends CommandExecutor {
    private PaymentService paymentService = PaymentServiceImpl.getInstance();

    public PaymentsCommand() {
        super(PagesPath.PAYMENTS_PAGE);
    }

    @Override
    public String performExecute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        long userId = extractUserIdFromSession(session);
        List<Card> cards = paymentService.findCardsByUser(userId);
        request.setAttribute(Attributes.CARDS, cards);
        return PagesPath.PAYMENTS_PAGE;
    }

    private long extractUserIdFromSession(HttpSession session){
        return (long)session.getAttribute(Attributes.USER_ID);
    }
}
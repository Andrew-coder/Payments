package payments.controller.commands.admin;

import payments.controller.commands.CommandExecutor;
import payments.model.entity.Card;
import payments.service.PaymentService;
import payments.service.impl.PaymentServiceImpl;
import payments.utils.constants.Attributes;
import payments.utils.constants.PagesPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CardsAdministrationCommand extends CommandExecutor{
    PaymentService paymentService = PaymentServiceImpl.getInstance();

    public CardsAdministrationCommand() {
        super(PagesPath.CARDS_ADMINISTRATION_PAGE);
    }

    @Override
    public String performExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Card> cards = paymentService.findAll();
        request.setAttribute(Attributes.CARDS, cards);
        cards.stream().
                mapToLong(Card::getId).
                forEach(id -> request.setAttribute(String.valueOf(id),
                        paymentService.isCardBlocked(id)));
        return PagesPath.CARDS_ADMINISTRATION_PAGE;
    }
}
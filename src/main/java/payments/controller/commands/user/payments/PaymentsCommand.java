package payments.controller.commands.user.payments;

import payments.controller.commands.Command;
import payments.controller.commands.CommandExecutor;
import payments.model.entity.Card;
import payments.service.CardService;
import payments.service.impl.CardServiceImpl;
import payments.utils.constants.Attributes;
import payments.utils.constants.PagesPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PaymentsCommand extends CommandExecutor {
    private CardService cardService = CardServiceImpl.getInstance();

    public PaymentsCommand() {
        super(PagesPath.PAYMENTS_PAGE);
    }

    @Override
    public String performExecute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Card> cards = cardService.findAll();
        request.setAttribute(Attributes.CARDS, cards);
        return PagesPath.PAYMENTS_PAGE;
    }
}
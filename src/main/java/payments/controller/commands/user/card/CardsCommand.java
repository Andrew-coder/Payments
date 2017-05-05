package payments.controller.commands.user.card;

import payments.controller.commands.Command;
import payments.controller.commands.CommandExecutor;
import payments.model.entity.Card;
import payments.model.entity.user.User;
import payments.service.CardService;
import payments.service.impl.CardServiceImpl;
import payments.utils.constants.Attributes;
import payments.utils.constants.PagesPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CardsCommand extends CommandExecutor {
    private CardService cardService = CardServiceImpl.getInstance();

    public CardsCommand() {
        super(PagesPath.CARDS_PAGE);
    }

    @Override
    public String performExecute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        long userId = extractUserIdFromSession(session);
        List<Card> cards = cardService.findCardsByUser(userId);
        request.setAttribute(Attributes.CARDS, cards);
        return PagesPath.CARDS_PAGE;
    }

    private long extractUserIdFromSession(HttpSession session){
        User user = (User)session.getAttribute(Attributes.USER);
        return user.getId();
    }
}
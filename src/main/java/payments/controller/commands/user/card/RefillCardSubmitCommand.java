package payments.controller.commands.user.card;

import payments.controller.commands.CommandExecutor;
import payments.utils.constants.PagesPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RefillCardSubmitCommand extends CommandExecutor {
    public RefillCardSubmitCommand() {
        super(PagesPath.REFILL_CARD_PAGE);
    }

    @Override
    public String performExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return null;
    }
}

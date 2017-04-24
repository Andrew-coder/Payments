package payments.controller.commands.user.card;

import payments.controller.commands.Command;
import payments.utils.constants.Attributes;
import payments.utils.constants.PagesPath;
import payments.utils.extractors.RequestParamExtractor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RefillCardCommand implements Command {
    private RequestParamExtractor requestExtractor = new RequestParamExtractor();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long cardId = requestExtractor.extractSingleLongPathParam(request);
        request.setAttribute(Attributes.CARD, cardId);
        return PagesPath.REFILL_CARD_PAGE;
    }
}
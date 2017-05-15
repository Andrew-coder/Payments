package payments.controller.commands.admin;

import payments.controller.commands.CommandExecutor;
import payments.model.entity.payment.Payment;
import payments.service.HistoryService;
import payments.service.impl.HistoryServiceImpl;
import payments.utils.constants.Attributes;
import payments.utils.constants.PagesPath;
import payments.utils.extractors.RequestParamExtractor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ViewPaymentAdminCommand extends CommandExecutor{
    private HistoryService historyService = HistoryServiceImpl.getInstance();
    private RequestParamExtractor requestExtractor = new RequestParamExtractor();

    public ViewPaymentAdminCommand() {
        super(PagesPath.PAYMENTS_ADMINISTRATION_PAGE);
    }

    @Override
    public String performExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long paymentId = requestExtractor.extractSingleLongPathParam(request);
        Optional<Payment> payment = historyService.findById(paymentId);
        if (payment.isPresent()){
            request.setAttribute(Attributes.PAYMENT, payment.get());
            return PagesPath.PAYMENT_DETAILS_PAGE;
        }
        response.sendRedirect(PagesPath.PAYMENTS_ADMINISTRATION);
        return PagesPath.REDIRECT;
    }
}
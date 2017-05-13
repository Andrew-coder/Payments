package payments.controller.commands.admin;

import payments.controller.commands.CommandExecutor;
import payments.model.entity.payment.Payment;
import payments.service.HistoryService;
import payments.service.impl.HistoryServiceImpl;
import payments.utils.constants.Attributes;
import payments.utils.constants.PagesPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PaymentsAdministrationCommand extends CommandExecutor{
    private HistoryService historyService = HistoryServiceImpl.getInstance();

    public PaymentsAdministrationCommand() {
        super(PagesPath.PAYMENTS_ADMINISTRATION_PAGE);
    }

    @Override
    public String performExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quantity = getLimitValueOrDefault(request);
        int offset = getOffsetValueOrDefault(request, quantity);
        int totalCount = historyService.getTotalCount();
        int totalPages = calculateOverallPagesCount(quantity, totalCount);
        List<Payment> payments = historyService.findAll(offset, quantity);
        request.setAttribute(Attributes.PAYMENTS, payments);
        request.setAttribute(Attributes.TOTAL_PAGES, totalPages);
        return PagesPath.PAYMENTS_ADMINISTRATION_PAGE;
    }
}
package payments.controller.commands.user.payments;

import org.apache.log4j.Logger;
import payments.controller.commands.CommandExecutor;
import payments.controller.validators.AccountTransferValidator;
import payments.utils.constants.PagesPath;
import payments.utils.extractors.RequestParamExtractor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountPaymentsCommand extends CommandExecutor {
    private static final Logger logger = Logger.getLogger(AccountPaymentsCommand.class);

    private AccountTransferValidator accountValidator;
    private RequestParamExtractor paramExtractor;

    public AccountPaymentsCommand() {
        super(PagesPath.PAYMENTS_PAGE);
        paramExtractor = new RequestParamExtractor();
    }

    @Override
    public String performExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        return null;
    }
}
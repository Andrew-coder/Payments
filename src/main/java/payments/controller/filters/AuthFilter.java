package payments.controller.filters;

import org.apache.log4j.Logger;
import payments.model.entity.user.RoleType;
import payments.model.entity.user.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.UIManager.put;

public class AuthFilter implements Filter{
    private static final Logger logger = Logger.getLogger(AuthFilter.class);
    private static final String ACCESS_DENIED = "Access denied for user %d";
    private static final String USER_NOT_AUTHORIZED = "User isn't authorized";

    /*private Map<RoleType, Authorizer> authorizeByRole = new HashMap<RoleType, Authorizer>() {{
        put(RoleType.USER, new UserAuthorizer());
        put(RoleType.ADMIN, new AdminAuthorizer());
    }};*/

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /*HttpServletRequest req = ((HttpServletRequest) request);
        HttpSession session = req.getSession();
        String uri = req.getRequestURI();
        User user = (User)session.getAttribute(Attributes.USER);
        if (user==null) {
            req.getRequestDispatcher(PagesPaths.HOME_PATH+PagesPaths.LOGIN).forward(request, response);
            logger.info(String.format(USER_NOT_AUTHORIZED));
            return;
        }

        if(!checkUserPermissions(uri, user)){
            req.getRequestDispatcher(PagesPaths.ACCESS_DENIED_PAGE).forward(request, response);
            logger.info(String.format(ACCESS_DENIED, user.getId()));
            return;
        }
*/
        chain.doFilter(request, response);
    }

    /*private boolean checkUserPermissions(String uri, User user){
        Authorizer authorizer = authorizeByRole.get(user.getRole());
        return authorizer.check(uri, user);
    }*/

    /*private interface Authorizer {
        boolean check(String uri, User user);
    }

    private class UserAuthorizer implements Authorizer {
        public boolean check(String uri, User user) {
            return !uri.startsWith(PagesPaths.ADMIN);

        }
    }

    private class AdminAuthorizer implements Authorizer {
        public boolean check(String uri, User user) {
            return  !uri.startsWith(PagesPaths.PURCHASE);
        }
    }

    private class SaleManagerAuthorizer implements Authorizer {
        public boolean check(String uri, User user) {
            return !(   uri.startsWith(PagesPaths.USERS_ADMINISTRATION)||
                    uri.startsWith(PagesPaths.ORDER_ADMINISTRATION)||
                    uri.startsWith(PagesPaths.PURCHASE));
        }
    }*/

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}

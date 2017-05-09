package payments.controller.filters;

import org.apache.log4j.Logger;
import payments.model.entity.user.RoleType;
import payments.model.entity.user.User;
import payments.utils.constants.Attributes;
import payments.utils.constants.PagesPath;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class AuthFilter implements Filter{
    private static final Logger logger = Logger.getLogger(AuthFilter.class);
    private static final String USER_NOT_AUTHORIZED = "User isn't authorized";

    private static EnumMap<RoleType, Authorizer> authorizeByRole = new EnumMap<>(RoleType.class);

    static {
        authorizeByRole.put(RoleType.USER, new UserAuthorizer());
        authorizeByRole.put(RoleType.ADMIN, new AdminAuthorizer());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest req = ((HttpServletRequest) request);
        HttpSession session = req.getSession();
        String uri = req.getRequestURI();
        User user = (User)session.getAttribute(Attributes.USER);
        if(!checkUserPermissions(uri, user)){
            req.getRequestDispatcher(PagesPath.LOGIN).forward(request, response);
            logger.info(String.format(USER_NOT_AUTHORIZED));
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean checkUserPermissions(String uri, User user){
        RoleType roleType=null;
        if(user!=null){
            roleType = user.getRole();
        }
        Authorizer authorizer = authorizeByRole.getOrDefault(roleType, new AnonymAuthorizer());
        return authorizer.check(uri, user);
    }

    private interface Authorizer {
        boolean check(String uri, User user);
    }

    private static class UserAuthorizer implements Authorizer {
        public boolean check(String uri, User user) {
            return user!=null && !uri.startsWith(PagesPath.ADMIN);

        }
    }

    private static class AdminAuthorizer implements Authorizer {
        public boolean check(String uri, User user) {
            return  user!=null && (uri.startsWith(PagesPath.ADMIN)||
                    uri.startsWith(PagesPath.LOGIN)||
                    uri.startsWith(PagesPath.REGISTER));
        }
    }

    private class AnonymAuthorizer implements Authorizer {
        public boolean check(String uri, User user){
            if(uri.endsWith(".css")||uri.endsWith(".js")||uri.endsWith(".png"))
                return true;
            return  uri.startsWith(PagesPath.LOGIN)||
                    uri.startsWith(PagesPath.REGISTER);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
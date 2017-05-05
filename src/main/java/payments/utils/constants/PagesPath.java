package payments.utils.constants;

public final class PagesPath {
    public static final String REDIRECT = "REDIRECT";
    public static final String FORWARD = "FORWARD";

    public static final String VIEW_JSP_CLASSPATH = "/WEB-INF/pages/";

    public static final String HOME = "/home";
    public static final String ADMIN = "/admin";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String CARDS_MANAGE = "/cards";

    public static final String HOME_PAGE = VIEW_JSP_CLASSPATH + "user/index.jsp";
    public static final String ADMIN_HOME_PAGE = VIEW_JSP_CLASSPATH + "admin/dashboard.jsp";
    public static final String LOGIN_PAGE = VIEW_JSP_CLASSPATH + "login.jsp";
    public static final String CARDS_PAGE = VIEW_JSP_CLASSPATH + "user/cardsManagment.jsp";
    public static final String PAYMENTS_PAGE = VIEW_JSP_CLASSPATH + "user/paymentsManagment.jsp";
    public static final String ERROR_PAGE = VIEW_JSP_CLASSPATH + "error.jsp";
    public static final String CONFIRMATION_PAGE = VIEW_JSP_CLASSPATH + "confirmation.jsp";
    public static final String ACCESS_DENIED_PAGE = VIEW_JSP_CLASSPATH + "accessDenied.jsp";
    public static final String REFILL_CARD_PAGE = VIEW_JSP_CLASSPATH + "user/cardRefill.jsp";
}
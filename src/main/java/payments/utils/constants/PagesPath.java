package payments.utils.constants;

/**
 * This class contains paths to jsp pages and URLs, which are supported
 */
public final class PagesPath {
    public static final String REDIRECT = "REDIRECT";
    public static final String FORWARD = "FORWARD";

    public static final String VIEW_JSP_CLASSPATH = "/WEB-INF/pages/";

    public static final String HOME = "/home";
    public static final String ADMIN = "/admin";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String CARDS_MANAGE = "/cards";
    public static final String CARDS_ADMINISTRATION = "/admin/cards";
    public static final String PAYMENTS_ADMINISTRATION = "/admin/payments";

    public static final String HOME_PAGE = VIEW_JSP_CLASSPATH + "user/index.jsp";
    public static final String ADMIN_HOME_PAGE = VIEW_JSP_CLASSPATH + "admin/dashboard.jsp";
    public static final String LOGIN_PAGE = VIEW_JSP_CLASSPATH + "login.jsp";
    public static final String CARDS_PAGE = VIEW_JSP_CLASSPATH + "user/cardsManagment.jsp";
    public static final String PAYMENTS_PAGE = VIEW_JSP_CLASSPATH + "user/paymentsManagment.jsp";
    public static final String ERROR_PAGE = VIEW_JSP_CLASSPATH + "error.jsp";
    public static final String CONFIRMATION_PAGE = VIEW_JSP_CLASSPATH + "confirmation.jsp";
    public static final String ACCESS_DENIED_PAGE = VIEW_JSP_CLASSPATH + "accessDenied.jsp";
    public static final String REFILL_CARD_PAGE = VIEW_JSP_CLASSPATH + "user/cardRefill.jsp";

    public static final String CARDS_ADMINISTRATION_PAGE = VIEW_JSP_CLASSPATH + "admin/cards.jsp";
    public static final String PAYMENTS_ADMINISTRATION_PAGE = VIEW_JSP_CLASSPATH + "admin/payments.jsp";
    public static final String PAYMENT_DETAILS_PAGE = VIEW_JSP_CLASSPATH + "admin/paymentDetails.jsp";
}
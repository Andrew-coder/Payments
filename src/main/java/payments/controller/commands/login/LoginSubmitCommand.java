package payments.controller.commands.login;

import payments.controller.commands.CommandExecutor;
import payments.model.entity.user.RoleType;
import payments.model.entity.user.User;
import payments.service.UserService;
import payments.service.impl.UserServiceImpl;
import payments.utils.constants.Attributes;
import payments.utils.constants.PagesPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class LoginSubmitCommand extends CommandExecutor {
    private static final String PARAM_EMAIL = "login_name";
    private static final String PARAM_PASSWORD ="login_password";

    private UserService userService = UserServiceImpl.getInstance();

    public LoginSubmitCommand() {
        super(PagesPath.LOGIN_PAGE);
    }

    @Override
    public String performExecute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        saveLoginDataToRequest(request);
        String pageToGo = PagesPath.LOGIN;
        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);
        if( email != null && password != null ){
            Optional<User> user = userService.login(email, password);
            if( user.isPresent() ){
                User person = user.get();
                pageToGo = getResultPageByUserRole(person);
                request.getSession().setAttribute(Attributes.USER, person);
            }
        }
        clearLoginDataFromRequest(request);
        return pageToGo;
    }

    private String getResultPageByUserRole(User user){
        String result = PagesPath.HOME;
        if(user.getRole()== RoleType.ADMIN) {
            result = PagesPath.ADMIN;
        }
        return result;
    }

    private void saveLoginDataToRequest(HttpServletRequest request){
        request.setAttribute(Attributes.PREVIOUS_LOGIN_EMAIL, request.getParameter(PARAM_EMAIL));
        request.setAttribute(Attributes.PREVIOUS_LOGIN_PASSWORD, request.getParameter(PARAM_PASSWORD));
    }

    private void clearLoginDataFromRequest(HttpServletRequest request){
        request.removeAttribute(Attributes.PREVIOUS_LOGIN_EMAIL);
        request.removeAttribute(Attributes.PREVIOUS_LOGIN_PASSWORD);
    }
}
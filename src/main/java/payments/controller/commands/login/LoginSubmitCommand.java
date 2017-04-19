package payments.controller.commands.login;

import payments.controller.commands.Command;
import payments.model.entity.user.RoleType;
import payments.model.entity.user.User;
import payments.service.UserService;
import payments.service.impl.UserServiceImpl;
import payments.utils.constants.Attributes;
import payments.utils.constants.PagesPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class LoginSubmitCommand implements Command {
    public static final String PARAM_EMAIL = "login";
    public static final String PARAM_PASSWORD ="password";

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String pageToGo = PagesPath.LOGIN;
        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);
        if( email != null && password != null ){
            Optional<User> user;
            user = userService.login(email, password);
            if( user.isPresent() ){
                User person = user.get();
                request.getSession().setAttribute(Attributes.USER, person);
                pageToGo=PagesPath.HOME;
                if(person.getRole()!= RoleType.USER) {
                    pageToGo = PagesPath.ADMIN;
                }
            }
        }
        return pageToGo;
    }
}

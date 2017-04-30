package payments.controller.commands.user;

import org.apache.log4j.Logger;
import payments.controller.commands.CommandExecutor;
import payments.controller.validators.Errors;
import payments.controller.validators.UserValidator;
import payments.model.dto.RegisterData;
import payments.model.entity.user.RoleType;
import payments.model.entity.user.User;
import payments.service.UserService;
import payments.service.impl.UserServiceImpl;
import payments.utils.constants.Attributes;
import payments.utils.constants.LoggerMessages;
import payments.utils.constants.PagesPath;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterSubmitCommand extends CommandExecutor {
    private static final Logger logger = Logger.getLogger(RegisterSubmitCommand.class);
    private UserService userService = UserServiceImpl.getInstance();
    private UserValidator userValidator;

    public RegisterSubmitCommand() {
        super(PagesPath.LOGIN_PAGE);
        userValidator = new UserValidator();
    }

    @Override
    public String performExecute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException {
        Errors errors = new Errors();
        RegisterData registerData = extractRegisterData(request);
        errors.addErrors(userValidator.validate(registerData).getErrors());
        if(errors.hasErrors()){
            saveRegisterDataToRequest(request);
            processErrors(request, errors);
            request.getRequestDispatcher(PagesPath.LOGIN_PAGE).forward(request, response);
            return PagesPath.FORWARD;
        }
        User user = extractUserFromRegisterData(registerData);
        userService.create(user);
        logger.info(String.format("User %s %s was successfully registered",registerData.getName(), registerData.getSurname()));
        request.setAttribute(Attributes.CONFIRM_MESSAGE, LoggerMessages.SUCCESSFUL_REGISTER);
        request.getRequestDispatcher(PagesPath.CONFIRMATION_PAGE).forward(request, response);
        return PagesPath.FORWARD;
    }

    private RegisterData extractRegisterData(HttpServletRequest request){
        RegisterData.Builder builder = new RegisterData.Builder()
                .setName(request.getParameter("name").toString())
                .setSurname(request.getParameter("surname").toString())
                .setEmail(request.getParameter("email").toString())
                .setPassword(request.getParameter("password").toString())
                .setBirthDate(request.getParameter("birthDate").toString());
        return builder.build();
    }

    private void processErrors(HttpServletRequest request, Errors errors){
        logger.error("Wrong input data in registration");
        request.setAttribute(Attributes.TAB, Attributes.REGISTER_TAB);
        request.setAttribute(Attributes.ERRORS, errors);
    }

    private void saveRegisterDataToRequest(HttpServletRequest request){
        request.setAttribute(Attributes.PREVIOUS_USER_NAME, request.getParameter("name"));
        request.setAttribute(Attributes.PREVIOUS_USER_SURNAME, request.getParameter("surname"));
        request.setAttribute(Attributes.PREVIOUS_USER_EMAIL, request.getParameter("email"));
        request.setAttribute(Attributes.PREVIOUS_USER_DATE, request.getParameter("birthDate"));
        request.setAttribute(Attributes.PREVIOUS_USER_PASSWORD, request.getParameter("password"));
    }


    private User extractUserFromRegisterData(RegisterData registerData){
        User.Builder builder = new User.Builder()
                .setName(registerData.getName())
                .setSurname(registerData.getSurname())
                .setEmail(registerData.getEmail())
                .setPassword(registerData.getPassword())
                .setRole(RoleType.USER);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date convertedDate = sdf.parse(registerData.getBirthDate());
            builder.setBirthDate(convertedDate);
        }
        catch (ParseException ex){}
        return builder.build();
    }
}
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
import payments.utils.extractors.RequestParamExtractor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterSubmitCommand extends CommandExecutor {
    private static final Logger logger = Logger.getLogger(RegisterSubmitCommand.class);
    private UserService userService = UserServiceImpl.getInstance();
    private UserValidator userValidator;
    private RequestParamExtractor paramExtractor;

    public RegisterSubmitCommand() {
        super(PagesPath.LOGIN_PAGE);
        userValidator = new UserValidator();
        paramExtractor = new RequestParamExtractor();
    }

    @Override
    public String performExecute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException {
        Errors errors = new Errors();
        saveRegisterDataToRequest(request);
        RegisterData registerData = extractRegisterData(request);
        errors.addErrors(userValidator.validate(registerData).getErrors());
        if(errors.hasErrors()){
            processErrors(request, errors);
            request.getRequestDispatcher(PagesPath.LOGIN_PAGE).forward(request, response);
            return PagesPath.FORWARD;
        }
        User user = extractUserFromRegisterData(registerData);
        userService.create(user);
        logger.info(String.format("User %s %s was successfully registered",registerData.getName(), registerData.getSurname()));
        clearRegisterDataFromRequest(request);
        request.setAttribute(Attributes.CONFIRM_MESSAGE, LoggerMessages.SUCCESSFUL_REGISTER);
        request.getRequestDispatcher(PagesPath.CONFIRMATION_PAGE).forward(request, response);
        return PagesPath.FORWARD;
    }

    private RegisterData extractRegisterData(HttpServletRequest request){
        RegisterData.Builder builder = new RegisterData.Builder()
                .setName(request.getParameter("name").toString())
                .setSurname(request.getParameter("surname").toString())
                .setCellphone(request.getParameter("cellphone").toString())
                .setPassword(request.getParameter("password").toString())
                .setBirthDate(request.getParameter("birthDate").toString());
        return builder.build();
    }

    private void processErrors(HttpServletRequest request, Errors errors){
        logger.error("Wrong input data in registration");
        request.setAttribute(Attributes.ERRORS, errors);
    }

    private void saveRegisterDataToRequest(HttpServletRequest request){
        request.setAttribute(Attributes.PREVIOUS_USER_NAME, request.getParameter("name"));
        request.setAttribute(Attributes.PREVIOUS_USER_SURNAME, request.getParameter("surname"));
        request.setAttribute(Attributes.PREVIOUS_USER_CELLPHONE, request.getParameter("cellphone"));
        request.setAttribute(Attributes.PREVIOUS_USER_DATE, request.getParameter("birthDate"));
        request.setAttribute(Attributes.PREVIOUS_USER_PASSWORD, request.getParameter("password"));
        request.setAttribute(Attributes.TAB, Attributes.REGISTER_TAB);
    }

    private void clearRegisterDataFromRequest(HttpServletRequest request){
        request.removeAttribute(Attributes.PREVIOUS_USER_NAME);
        request.removeAttribute(Attributes.PREVIOUS_USER_SURNAME);
        request.removeAttribute(Attributes.PREVIOUS_USER_CELLPHONE);
        request.removeAttribute(Attributes.PREVIOUS_USER_DATE);
        request.removeAttribute(Attributes.PREVIOUS_USER_PASSWORD);
        request.removeAttribute(Attributes.TAB);
    }

    private User extractUserFromRegisterData(RegisterData registerData){
        User.Builder builder = new User.Builder()
                .setName(registerData.getName())
                .setSurname(registerData.getSurname())
                .setCellphone(registerData.getCellphone())
                .setPassword(registerData.getPassword())
                .setRole(RoleType.USER)
                .setBirthDate(paramExtractor.extractDate(registerData.getBirthDate()));
        return builder.build();
    }
}
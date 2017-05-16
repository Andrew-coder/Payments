package payments.controller.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import payments.controller.commands.user.RegisterSubmitCommand;
import payments.controller.validators.Errors;
import payments.controller.validators.UserValidator;
import payments.model.dto.RegisterData;
import payments.model.entity.user.User;
import payments.service.UserService;
import payments.utils.constants.Attributes;
import payments.utils.constants.PagesPath;
import payments.utils.extractors.RequestParamExtractor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterSubmitCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private UserValidator userValidator;
    @Mock
    private RequestParamExtractor paramExtractor;
    @Mock
    private UserService userService;
    @InjectMocks
    private RegisterSubmitCommand command;

    @Before
    public void init() throws ServletException, IOException{
        when(request.getParameter(anyString())).thenReturn("");
        doNothing().when(request).setAttribute(anyString(), any());
        doNothing().when(request).removeAttribute(anyString());
        doNothing().when(userService).create(any(User.class));
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(request, response);
        when(paramExtractor.extractDate(anyString())).thenReturn(new Date());
    }

    @Test
    public void testRegisterWithWrongUserCredentials() throws IOException, ServletException{
        Errors errors = new Errors();
        errors.addError(Attributes.ERROR, "");
        when(userValidator.validate(any(RegisterData.class))).thenReturn(errors);
        command.execute(request, response);
        verify(userService, never()).create(any());
        verify(request, never()).removeAttribute(anyString());
        verify(request).getRequestDispatcher(PagesPath.LOGIN_PAGE);
    }

    @Test
    public void testRegisterWithCorrectUserCredentials() throws IOException, ServletException{
        Errors errors = new Errors();
        when(userValidator.validate(any(RegisterData.class))).thenReturn(errors);
        command.execute(request, response);
        verify(userService, times(1)).create(any());
        verify(request, atLeast(1)).removeAttribute(anyString());
        verify(request).getRequestDispatcher(PagesPath.CONFIRMATION_PAGE);
    }
}
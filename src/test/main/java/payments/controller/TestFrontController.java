package payments.controller;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import payments.controller.commands.Command;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import payments.controller.commands.PageNotFoundCommand;
import payments.utils.constants.PagesPath;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestFrontController {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private CommandHolder commandHolder;
    @Mock
    private Command command;

    private FrontController controller;

    @Before
    public void init() {
        controller = new FrontController();
        controller.setCommandHolder(commandHolder);
    }

    @Test
    public void testDoGetDoNothingIfCommandReturnedRedirected() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("path");
        when(request.getMethod()).thenReturn("get");
        when(command.execute(request, response)).thenReturn(PagesPath.REDIRECT);
        when(commandHolder.findCommand(any())).thenReturn(command);
        controller.doGet(request, response);
        verify(response, times(0)).sendRedirect(any());
        verify(requestDispatcher, times(0)).forward(request, response);
    }

    @Test
    public void testDoPostDoNothingIfCommandReturnedForward() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("path");
        when(request.getMethod()).thenReturn("get");
        when(command.execute(request, response)).thenReturn(PagesPath.FORWARD);
        when(commandHolder.findCommand(any())).thenReturn(command);
        controller.doPost(request, response);
        verify(response, times(0)).sendRedirect(any());
        verify(requestDispatcher, times(0)).forward(request, response);
    }

    @Test
    public void testDoGetDispatchesToPageNotFoundCommandIfUriIsNotValid() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("path");
        when(request.getMethod()).thenReturn("get");
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request, response);
        when(commandHolder.findCommand(any())).thenReturn(new PageNotFoundCommand());
        controller.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher(PagesPath.ERROR_PAGE);
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoPostDispatchesToPageNotFoundCommandIfUriIsNotValid() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("path");
        when(request.getMethod()).thenReturn("post");
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request, response);
        when(commandHolder.findCommand(any())).thenReturn(new PageNotFoundCommand());
        controller.doPost(request, response);
        verify(response, times(1)).sendRedirect(PagesPath.ERROR_PAGE);
    }

    @Test
    public void testDoPostRetrievesPostCommandsFromHolderIfRequestMethodIsPost() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("path");
        when(request.getMethod()).thenReturn("post");
        when(commandHolder.findCommand("POST:path")).thenReturn(command);
        when(command.execute(request, response)).thenReturn("pagePost");
        controller.doPost(request, response);
        verify(commandHolder, times(0)).findCommand("GET:path");
        verify(commandHolder, times(1)).findCommand("POST:path");
    }
}
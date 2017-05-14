package payments.controller.filters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import payments.model.entity.user.RoleType;
import payments.utils.constants.Attributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthFilterTest {
    @Mock
    HttpServletRequest request;
    @Mock
    ServletResponse response;
    @Mock
    FilterChain chain;
    @Mock
    HttpSession session;
    @Mock
    RequestDispatcher requestDispatcher;

    private Filter filter;

    @Before
    public void init() throws IOException, ServletException{
        filter = new AuthFilter();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        doNothing().when(chain).doFilter(request, response);
    }

    private void setUpMocksByParams(String uri, long userId, RoleType type){
        when(request.getRequestURI()).thenReturn(uri);
        when(request.getAttribute(Attributes.USER_ID)).thenReturn(userId);
        when(request.getAttribute(Attributes.USER_ROLE)).thenReturn(type);
    }

    @Test
    public void testDoFilterToHomePathByNonAuthorizedRequest()throws IOException, ServletException{
        setUpMocksByParams("/home", 0, null);
        filter.doFilter(request, response, chain);
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoFilterToStaticResource() throws IOException, ServletException {
        setUpMocksByParams("script.js", 0, null);
        filter.doFilter(request, response, chain);
        verify(requestDispatcher, never()).forward(request, response);
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterAdminPathByNonPrivilegedUser() throws IOException, ServletException {
        setUpMocksByParams("/admin",1, RoleType.USER);
        filter.doFilter(request, response, chain);
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoFilterLoginPathByGuest() throws IOException, ServletException {
        setUpMocksByParams("/login", 0, null);
        filter.doFilter(request, response, chain);
        verify(requestDispatcher, never()).forward(request, response);
    }
}

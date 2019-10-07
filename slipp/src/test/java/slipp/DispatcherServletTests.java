package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.ControllerHandlerAdapter;
import nextstep.mvc.tobe.HandlerAdapter;
import nextstep.mvc.tobe.HandlerAdapterNotFoundException;
import nextstep.mvc.tobe.HandlerExecutionAdaptor;
import nextstep.mvc.tobe.HandlerNotExistException;
import nextstep.mvc.tobe.HandlerNotFoundException;
import nextstep.web.annotation.RequestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class DispatcherServletTests {
    private DispatcherServlet dispatcherServlet;
    private MockHttpServletRequest mockHttpServletRequest;
    private MockHttpServletResponse mockHttpServletResponse;

    @BeforeEach
    void setUp() {
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletResponse = new MockHttpServletResponse();
    }


    @Test
    void service() throws ServletException, IOException {
        dispatcherServlet = new DispatcherServlet(Arrays.asList(new ControllerHandlerAdapter(), new HandlerExecutionAdaptor()),
            Arrays.asList(new ManualHandlerMapping(), new AnnotationHandlerMapping("slipp")));
        dispatcherServlet.init();

        makeRequest();
        dispatcherServlet.service(mockHttpServletRequest, mockHttpServletResponse);
    }

    @Test
    void HandlerAdapterNotFound() {
        List<HandlerAdapter> list = new ArrayList<>();
        dispatcherServlet = new DispatcherServlet(list,
            Arrays.asList(new ManualHandlerMapping(), new AnnotationHandlerMapping("slipp")));
        dispatcherServlet.init();
        makeRequest();

        assertThrows(HandlerAdapterNotFoundException.class, () -> {
            dispatcherServlet.service(mockHttpServletRequest, mockHttpServletResponse);
        });
    }

    @Test
    void HandlerNotFound() {
        List<HandlerMapping> list = new ArrayList<>();
        dispatcherServlet = new DispatcherServlet(Arrays.asList(new ControllerHandlerAdapter(), new HandlerExecutionAdaptor()), list);
        dispatcherServlet.init();
        makeRequest();

        assertThrows(HandlerNotExistException.class, () -> {
            dispatcherServlet.service(mockHttpServletRequest, mockHttpServletResponse);
        });
    }

    private void makeRequest() {
        mockHttpServletRequest.setMethod(RequestMethod.GET.toString());
        mockHttpServletRequest.setRequestURI("/");
        mockHttpServletRequest.setProtocol("HTTP/1.1");
        mockHttpServletRequest.addHeader("Host", "localhost:8080");
        mockHttpServletRequest.addHeader("Connection", "keep-alive");
        mockHttpServletRequest.addHeader("Accept", "*/*");
    }
}

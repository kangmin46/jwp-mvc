package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdaptor {
    boolean supports(Object handler);

    ModelAndView handle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception;
}
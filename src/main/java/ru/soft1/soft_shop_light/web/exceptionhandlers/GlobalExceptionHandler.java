package ru.soft1.soft_shop_light.web.exceptionhandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.View;
import ru.soft1.soft_shop_light.util.exception.ApplicationException;
import ru.soft1.soft_shop_light.util.exception.ErrorType;
import ru.soft1.soft_shop_light.util.validation.ValidationUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSourceAccessor messageSourceAccessor;

    public GlobalExceptionHandler(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView wrongRequest(HttpServletRequest req, NoHandlerFoundException e) {
        return logAndGetExceptionView(req, e, false, ErrorType.WRONG_REQUEST, null);
    }

    @ExceptionHandler(ApplicationException.class)
    public ModelAndView updateRestrictionException(HttpServletRequest req, ApplicationException appEx) {
        return logAndGetExceptionView(req, appEx, false, appEx.getType(), appEx.getMsgCode());
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.error("Exception at request " + req.getRequestURL(), e);
        return logAndGetExceptionView(req, e, true, ErrorType.APP_ERROR, null);
    }

    private ModelAndView logAndGetExceptionView(HttpServletRequest req, Exception e, boolean logException,
                                                ErrorType errorType, String code) {
        Throwable rootCause = ValidationUtil.logAndGetRootCause(log, req, e, logException, errorType);
        ModelAndView mav = new ModelAndView("exception",
                Map.of("exception", rootCause, "message",
                        code != null ? messageSourceAccessor.getMessage(code) : ValidationUtil.getMessage(rootCause),
                        "typeMessage", messageSourceAccessor.getMessage(errorType.getErrorCode()),
                        "status", errorType.getStatus()));
        mav.setStatus(errorType.getStatus());
        return mav;
    }
}

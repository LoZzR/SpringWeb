package exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//@ControllerAdvice
public class MissingRecordsHandler {

    private static Logger logger = LoggerFactory.getLogger(MissingRecordsHandler.class);
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFound(HttpServletRequest req, NotFoundException nfe) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>Calling notFound");
    }
}

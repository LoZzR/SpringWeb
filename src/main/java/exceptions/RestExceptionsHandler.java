package exceptions;

import controllers.MultiplePersonController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import util.JsonError;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackageClasses = MultiplePersonController.class)
public class RestExceptionsHandler {

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<JsonError> handleException(HttpServletRequest req, Exception e) {
        String errorURL = req.getRequestURL().toString();
        if(e instanceof PersonsException) {
            PersonsException pe = (PersonsException) e;
            return new ResponseEntity<JsonError>(new JsonError(errorURL, pe.getMessage()), pe.getStatus());
        }
        return new ResponseEntity<JsonError>(new JsonError(errorURL, "Unexpected Exception: " + e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

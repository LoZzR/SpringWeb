package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MissingRecordsHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFound(HttpServletRequest req, NotFoundException
            nfe) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("problem", "Malformed URL: " + req.getRequestURI()
                + "<br/>" + nfe.getMessage());
        mav.setViewName("error");
        return mav;
    }
}

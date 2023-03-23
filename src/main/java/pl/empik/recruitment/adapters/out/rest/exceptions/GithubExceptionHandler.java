package pl.empik.recruitment.adapters.out.rest.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.empik.recruitment.handler.GlobalExceptionHandler;

@ControllerAdvice
public class GithubExceptionHandler {
    @ExceptionHandler(ServiceNotRespondingException.class)
    @ResponseBody
    public Error handleServiceNotRespondingException(ServiceNotRespondingException ex) {
        return new Error(ex.getClass().getName(), ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public Error handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new Error(ex.getClass().getName(), ex.getMessage());
    }

    public static class Error extends GlobalExceptionHandler.Error {
        public Error() {
        }

        public Error(String className, String message) {
            super(className, message);
        }
    }
}

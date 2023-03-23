package pl.empik.recruitment.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Error handleRuntimeException(RuntimeException ex) {
        return new Error(ex.getClass().getName(), "Something goes wrong");
    }


    public static class Error implements Serializable {
        private String className;

        private String message;

        public Error() {
        }

        public Error(String className, String message) {
            this.className = className;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public String getClassName() {
            return className;
        }
    }
}

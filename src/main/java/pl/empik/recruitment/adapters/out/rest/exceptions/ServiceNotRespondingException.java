package pl.empik.recruitment.adapters.out.rest.exceptions;

public class ServiceNotRespondingException extends RuntimeException {
    public ServiceNotRespondingException(String message) {
        super(message);
    }
}

package Exceptions;

public class PrgStateException extends Exception{
    public PrgStateException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "\n PrgStateException: " + super.getMessage();
    }
}

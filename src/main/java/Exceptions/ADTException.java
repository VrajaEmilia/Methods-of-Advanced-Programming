package Exceptions;

public class ADTException extends Exception{
    public ADTException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "\n ADT EXCEPTION: " + super.getMessage();
    }
}

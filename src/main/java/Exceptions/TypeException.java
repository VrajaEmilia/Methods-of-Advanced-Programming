package Exceptions;

public class TypeException extends Exception{
    public TypeException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "\n TYPE EXCEPTION: " + super.getMessage();
    }
}

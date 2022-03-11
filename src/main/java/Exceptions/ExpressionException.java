package Exceptions;

public class ExpressionException extends Exception {
    public ExpressionException(String message) {
        super(message);
    }
    @Override
    public String getMessage() {
        return "\n EXPRESSION EXCEPTION: " + super.getMessage();
    }
}

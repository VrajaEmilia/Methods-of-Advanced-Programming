package Exceptions;

public class FileException extends Exception{
    public FileException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "\n FILE EXCEPTION: " + super.getMessage();
    }
}

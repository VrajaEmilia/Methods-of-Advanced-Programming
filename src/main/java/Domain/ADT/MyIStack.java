package Domain.ADT;

import Exceptions.ADTException;
import java.util.List;

public interface MyIStack<T> {
    T pop() throws ADTException;
    void push(T elem);
    boolean isEmpty();

    List<T> getAll();
}

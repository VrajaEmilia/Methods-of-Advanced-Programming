package Domain.ADT;

import java.util.List;

public interface MyIList<T>{
    void add(T elem);
    boolean isEmpty();
    void remove(int index);
    List<T> getAll();
    int size();
    T get(int index);
}

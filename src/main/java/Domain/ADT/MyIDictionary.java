package Domain.ADT;

import Exceptions.ADTException;

import java.util.HashMap;

public interface MyIDictionary<X,Y> {
    void add(X key, Y value);
    void update(X key,Y new_value) throws ADTException;
    boolean isKey(X key);
    boolean isPair(X key,Y value);
    void remove(X key,Y value) throws ADTException;
    Y lookup(X key) throws ADTException;
    MyIDictionary<X,Y> get_copy();
    public HashMap<X, Y> getElems();
}

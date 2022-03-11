package Domain.ADT;


import Exceptions.ADTException;

import java.util.HashMap;

public class MyDictionary<X,Y> implements MyIDictionary<X,Y> {
    private HashMap<X, Y> elems;

    public HashMap<X, Y> getElems() {
        return elems;
    }

    public MyDictionary() {
        this.elems = new HashMap<X, Y>();
    }

    @Override
    public void add(X key, Y value) {
        this.elems.put(key,value);
    }

    @Override
    public void update(X key,Y new_value) throws ADTException {
        if (!isKey(key))
            throw new ADTException("This key doesn't exist!");
        elems.replace(key,new_value);
        }

    @Override
    public boolean isKey(X key) {
        return elems.containsKey(key);
    }

    @Override
    public boolean isPair(X key, Y value) {
        return elems.get(key)==value;
    }

    @Override
    public void remove(X key, Y value) throws ADTException {
        if (!isKey(key))
            throw new ADTException("This key is not in the dict!");
        if (!isPair(key, value))
            throw new ADTException("This pair is not in the dict!");
        this.elems.remove(key,value);
    }

    @Override
    public Y lookup(X key) throws ADTException {
        if (!isKey(key))
            throw new ADTException("This key is not defined");
        return this.elems.get(key);
    }

    @Override
    public MyIDictionary<X, Y> get_copy() {
        MyDictionary<X, Y> copy = new MyDictionary<>();
        copy.getElems().putAll(this.elems);
        return copy;
    }

    @Override
    public String toString() {
        return this.elems.toString();
    }
}
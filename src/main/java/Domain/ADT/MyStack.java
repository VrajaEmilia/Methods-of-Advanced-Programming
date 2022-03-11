package Domain.ADT;

import Exceptions.ADTException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> elems;
    public MyStack() {
        this.elems = new Stack<T>();
    }

    @Override
    public T pop() throws ADTException {
        if(elems.isEmpty())
            throw new ADTException("The stack is empty!");
        T top = elems.pop();;
        return top;
    }

    @Override
    public void push(T elem) {
        this.elems.push(elem);
    }

    @Override
    public boolean isEmpty() {
        return elems.isEmpty();
    }

    @Override
    public List getAll() {
        return elems.subList(0,elems.size());
    }

    @Override
    public String toString() {
       return this.elems.toString();
    }
}

package Domain.ADT;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private ArrayList<T> elems;

    public MyList() {
        this.elems = new ArrayList<T>();
    }

    @Override
    public void add(T elem) {
        this.elems.add(elem);
    }

    @Override
    public boolean isEmpty() {
        return this.elems.isEmpty();
    }

    @Override
    public void remove(int index) {
        this.elems.remove(index);
    }

    @Override
    public List<T> getAll() {
        return this.elems;
    }

    @Override
    public int size() {
        return elems.size();
    }

    @Override
    public T get(int index) {
        return elems.get(index);
    }

    @Override
    public String toString() {
        return elems.toString();
    }
}

package Domain.ADT;

import Domain.Value.Value;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap{
    private Map<Integer,Value> heap;
    private Integer free;

    public MyHeap() {
        heap = new HashMap<>();
        free = 1;
    }

    public int getFree() {
        return free;
    }

    @Override
    public int add(Value val) {
        int addr = free;
        setHeap(addr,val);
        free+=1;
        return addr;
    }

    @Override
    public boolean isFree(int addr) {
        return !heap.containsKey(addr);
    }

    @Override
    public Value getValue(int addr) {
        return heap.get(addr);
    }

    @Override
    public Map<Integer, Value> getHeap() {
        return heap;
    }

    @Override
    public void setHeap(int addr, Value val) {
        heap.put(addr,val);
    }

    @Override
    public void setHeap(HashMap<Integer, Value> newHeap) {
       this.heap=newHeap;
    }

    @Override
    public String toString() {
        StringBuilder s= new StringBuilder("{");
        int first=0;
        for(Map.Entry<Integer, Value> e : heap.entrySet())
        {
            if(first!=0)
                s.append(",");
            Integer key    = e.getKey();
            Value value  = e.getValue();
            s.append(key).append("->").append(value);
            first=1;
        }
        s.append("}");
        return s.toString();
    }
}

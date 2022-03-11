package Domain.ADT;

import Domain.Value.Value;

import java.util.HashMap;
import java.util.Map;

public interface MyIHeap {
    int add(Value val);
    boolean isFree(int addr);
    Value getValue(int addr);
    Map<Integer,Value> getHeap();
    void setHeap(int addr, Value val);
    public void setHeap(HashMap<Integer, Value> newHeap);

}

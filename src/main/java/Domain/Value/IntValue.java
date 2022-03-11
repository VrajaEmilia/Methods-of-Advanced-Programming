package Domain.Value;

import Domain.Type.IntType;
import Domain.Type.Type;

public class IntValue implements Value{
    int val;

    public IntValue(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    public boolean equals(Object obj) {
        return obj instanceof IntValue && ((IntValue) obj).getVal() == val;
    }


    @Override
    public String toString() {
        return ""+ val;
    }
}

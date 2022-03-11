package Domain.Value;

import Domain.Type.BoolType;
import Domain.Type.IntType;
import Domain.Type.Type;

public class BoolValue implements Value{
    boolean val;

    public BoolValue(boolean val) {
        this.val = val;
    }

    public boolean isVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolValue && ((BoolValue) obj).isVal() == val;
    }

    @Override
    public String toString() {
        return ""+ val;
    }
}

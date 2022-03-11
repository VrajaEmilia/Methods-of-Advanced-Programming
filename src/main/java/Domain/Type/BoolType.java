package Domain.Type;

import Domain.Value.BoolValue;
import Domain.Value.Value;

public class BoolType implements Type{
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    public Value defaultValue() {
        return new BoolValue(false);
    }
}

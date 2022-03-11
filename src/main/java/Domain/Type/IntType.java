package Domain.Type;

import Domain.Value.IntValue;
import Domain.Value.Value;

public class IntType implements Type{

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }
    public Value defaultValue() {
        return new IntValue(0);
    }
}

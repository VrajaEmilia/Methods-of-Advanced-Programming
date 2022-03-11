package Domain.Type;

import Domain.Value.StringValue;
import Domain.Value.Value;

public class StringType implements Type{
    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public Value defaultValue() {
        return new StringValue("null string");
    }
}

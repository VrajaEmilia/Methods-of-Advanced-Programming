package Domain.Value;

import Domain.Type.IntType;
import Domain.Type.StringType;
import Domain.Type.Type;

import java.util.Objects;

public class StringValue implements Value{
    String val;

    public StringValue(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    public boolean equals(Object obj) {
        return obj instanceof StringValue && Objects.equals(((StringValue) obj).getVal(), val);
    }


    @Override
    public String toString() {
        return val;
    }
}

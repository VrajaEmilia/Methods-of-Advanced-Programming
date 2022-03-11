package Domain.Value;

import Domain.Type.Type;
import Domain.Type.RefType;

import java.util.Objects;

public class RefValue implements Value{
    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddr() {return address;}

    public Type getType() { return new RefType(locationType);
    }

    public Type getLocationType() {
        return locationType;
    }

    public String toString(){
        return "RefVal("+address + "," +locationType.toString()+")";
    }

    public boolean equals(Object obj){

        if(obj instanceof RefValue)
            return (Objects.equals(((RefValue) obj).getAddr(),address)
                    && locationType.equals(((RefValue) obj).getType()));
        return false;
    }
}

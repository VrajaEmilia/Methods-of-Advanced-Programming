package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Type.RefType;
import Domain.Type.Type;
import Domain.Value.RefValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionException;

public class rH implements IExpression{
    IExpression exp;

    public rH(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap heap) throws ExpressionException, ADTException {
        Value val = exp.eval(table,heap);
        if(!(val instanceof RefValue))
            throw new ExpressionException("Not a reference value!");
        int addr = ((RefValue) val).getAddr();
        if(heap.isFree(addr))
            throw new ExpressionException("Address not found in heap");
        return heap.getValue(addr);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type typ=exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new ExpressionException("the rH argument is not a Ref Type");
    }

    @Override
    public String toString() {
        return "readHeap(" +exp+
                ')';
    }
}

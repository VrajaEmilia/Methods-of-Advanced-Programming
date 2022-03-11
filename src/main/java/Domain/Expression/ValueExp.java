package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Type.Type;
import Domain.Value.Value;
import Exceptions.ExpressionException;

import java.beans.Expression;

public class ValueExp implements IExpression{
    private Value e;

    public ValueExp(Value e1)
    {
        e = e1;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap heap) {
        return e;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        return e.getType();
    }

    @Override
    public String toString() {
        return e.toString();
    }


}

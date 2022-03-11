package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Type.Type;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionException;

public class VarExp implements IExpression{
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap heap) throws ExpressionException, ADTException {
        if(!table.isKey(id))
            throw new ExpressionException("The variable has not been defined");
        return table.lookup(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        return typeEnv.lookup(id);
    }
}

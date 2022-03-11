package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Type.IntType;
import Domain.Type.RefType;
import Domain.Type.Type;
import Domain.Value.BoolValue;
import Domain.Value.IntValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionException;

public class RelationalExp implements IExpression{
    private IExpression expr1;
    private IExpression expr2;
    private String op;

    public RelationalExp(IExpression expr1, IExpression expr2, String op) {
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap heap) throws ExpressionException, ADTException {
        Value v1,v2;
        v1 = this.expr1.eval(table, heap);
        v2 = this.expr2.eval(table, heap);
        if(!v1.getType().equals(new IntType()))
            throw new ExpressionException("The first value is not an integer!");
        if(!v2.getType().equals(new IntType()))
            throw new ExpressionException("The second value is not an integer!");
        int n1=((IntValue)v1).getVal();
        int n2=((IntValue)v2).getVal();
        if(op.equals("<"))
            return new BoolValue(n1<n2);
        else if(op.equals(">"))
            return new BoolValue(n1>n2);
        else if(op.equals("<="))
            return new BoolValue(n1<=n2);
        else if(op.equals(">="))
            return new BoolValue(n1>=n2);
        else if(op.equals("=="))
            return new BoolValue(n1==n2);
        else if(op.equals("!="))
            return new BoolValue(n1!=n2);
        else
            throw new ExpressionException("Invalid operand!");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type typ1, typ2;
        typ1 = expr1.typecheck(typeEnv);
        typ2 = expr2.typecheck(typeEnv);
        if(typ1.equals(new IntType())) {
            if(typ2.equals(new IntType())) {
                return new IntType();
            } else
                throw new ExpressionException("second operand is not an integer");
        }else
            throw new ExpressionException("first operand is not an integer");
    }



    @Override
    public String toString() {
        return expr1 +" "+this.op+" " +expr2;

    }
}

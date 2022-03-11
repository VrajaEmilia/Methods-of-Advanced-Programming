package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Type.BoolType;
import Domain.Type.IntType;
import Domain.Type.Type;
import Domain.Value.BoolValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionException;

import java.util.Objects;

public class LogicExp implements IExpression{
    private IExpression e1;
    private IExpression e2;
    private String op;

    public LogicExp(IExpression e1, IExpression e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }
    public String toString() {
        return "(" + e1.toString() + op + e2.toString() + ")";
    }
    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap heap) throws ExpressionException, ADTException {
        Value v1 = e1.eval(table,heap );
        if(Objects.equals(v1.getType(), new BoolType()))
        {
            Value v2 = e2.eval(table,heap );
            if(Objects.equals(v2.getType(), new BoolType())) {
                boolean n1 = ((BoolValue)v1).isVal();
                boolean n2 = ((BoolValue)v2).isVal();
                if(Objects.equals(op, "and")) return new BoolValue(n1 && n2);
                else if(Objects.equals(op, "or")) return new BoolValue(n1 || n2);
                else throw new ExpressionException("Invalid operand!");
            }
            else
                throw new ExpressionException("Cannot perform logic operation on non-boolean type variables!");
        }
        else
            throw new ExpressionException("Cannot perform logic operation on non-boolean type variables!");
        }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if(typ1.equals(new BoolType())) {
            if(typ2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new ExpressionException("second operand is not of type bool");
        }else
            throw new ExpressionException("first operand is not of type bool");
    }

}


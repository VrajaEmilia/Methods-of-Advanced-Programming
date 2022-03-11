package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Type.IntType;
import Domain.Type.Type;
import Domain.Value.IntValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionException;

public class ArithExp implements IExpression {
    private IExpression e1;
    private IExpression e2;
    private Character op;

    public ArithExp(Character op, IExpression e1, IExpression e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public String toString() {
        return "(" + e1 + op + e2 + ")";
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap heap) throws ExpressionException, ADTException {
        Value v1, v2;
        v1 = e1.eval(table, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(table, heap);
            if (v2.getType().equals(new IntType())) {
                int n1 = ((IntValue) v1).getVal();
                int n2 = ((IntValue) v2).getVal();
                if (op == '+')
                    return new IntValue(n1 + n2);
                else if (op == '-')
                    return new IntValue(n1 - n2);
                else if (op == '*')
                    return new IntValue(n1 * n2);
                else if (op == '/') {
                    if (n2 == 0)
                        throw new ExpressionException("Cannot perform division by 0!");
                    return new IntValue(n1 / n2);
                } else
                    throw new ExpressionException("Invalid operand!");
            } else
                throw new ExpressionException("Cannot perform arithmetic operations on non int variables");
        } else
            throw new ExpressionException("Cannot perform arithmetic operations on non-int variables");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if(typ1.equals(new IntType())) {
            if(typ2.equals(new IntType())) {
                return new IntType();
            } else
            throw new ExpressionException("second operand is not an integer");
        }else
        throw new ExpressionException("first operand is not an integer");
    }

}


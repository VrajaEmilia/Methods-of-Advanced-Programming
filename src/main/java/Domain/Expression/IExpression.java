package Domain.Expression;
import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Type.Type;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionException;

public interface IExpression {
    String toString();
    Value eval(MyIDictionary<String, Value> table, MyIHeap heap) throws ExpressionException, ADTException;
    Type typecheck(MyIDictionary<String,Type> typeEnv) throws ExpressionException, ADTException;
}

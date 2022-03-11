package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.Expression.IExpression;
import Domain.PrgState;
import Domain.Type.Type;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionException;
import Exceptions.StmtException;

public class PrintStmt implements IStmt{
    private IExpression exp;

    public PrintStmt(IExpression exp) {
        this.exp = exp;
    }

    public IExpression getExp() {
        return exp;
    }

    @Override
    public String toString()
    {
        return "print(" + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ADTException, ExpressionException {
        Value e = exp.eval(state.getSymbolTable(), state.getHeap());
        state.getOut().add(e);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ADTException, ExpressionException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}

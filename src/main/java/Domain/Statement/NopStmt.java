package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.PrgState;
import Domain.Type.Type;
import Exceptions.ADTException;
import Exceptions.ExpressionException;
import Exceptions.StmtException;

public class NopStmt implements IStmt{
    public NopStmt() {
    }

    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ADTException, ExpressionException {
        return typeEnv;
    }

    public String toString()
    {
        return "nop()";
    }
}

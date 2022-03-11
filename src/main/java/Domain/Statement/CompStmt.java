package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIStack;
import Domain.PrgState;
import Domain.Type.Type;
import Exceptions.ADTException;
import Exceptions.ExpressionException;
import Exceptions.StmtException;

public class CompStmt implements IStmt{
    private IStmt first;
    private IStmt snd;

    public CompStmt(IStmt first, IStmt snd) {
        this.first = first;
        this.snd = snd;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(snd);
        stk.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ADTException, ExpressionException {
        return snd.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return first+" | "+snd;
    }
}

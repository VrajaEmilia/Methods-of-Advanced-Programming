package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIStack;
import Domain.ADT.MyStack;
import Domain.PrgState;
import Domain.Type.Type;
import Domain.Value.Value;
import Exceptions.*;

public class forkStmt implements IStmt{
    IStmt forkStmt;

    public forkStmt(IStmt forkStmt) {
        this.forkStmt = forkStmt;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ADTException, TypeException, ExpressionException, FileException {
        MyIDictionary<String, Value> symTableCopy = state.getSymbolTable().get_copy();
        MyIStack<IStmt> stack = new MyStack<>();
        stack.push(this.forkStmt);
        PrgState newPrgState = new PrgState(stack,symTableCopy,state.getOut(), state.getFileTable(),state.getHeap());
        newPrgState.setId();
        return newPrgState;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ADTException, ExpressionException {
        forkStmt.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString(){
        return " FORK STMT(" + forkStmt.toString() +") ";
    }
}

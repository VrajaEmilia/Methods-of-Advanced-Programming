package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.PrgState;
import Domain.Type.Type;
import Exceptions.*;

public interface IStmt {
    PrgState execute(PrgState state) throws StmtException, ADTException, TypeException, ExpressionException, FileException;
    MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws StmtException, ADTException, ExpressionException;
}

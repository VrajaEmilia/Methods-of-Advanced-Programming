package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.PrgState;
import Domain.Type.Type;
import Exceptions.ADTException;
import Exceptions.ExpressionException;
import Exceptions.StmtException;
import Exceptions.TypeException;

public class VarDeclStmt implements IStmt{
    String name;
    Type typ;

    public VarDeclStmt(String name, Type typ) {
        this.name = name;
        this.typ = typ;
    }
    @Override
    public String toString() {
        return typ.toString() + " " + name;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ADTException, TypeException {
        if(state.getSymbolTable().isKey(name))
            throw new StmtException("This variable is already defined!");
        state.getSymbolTable().add(name,typ.defaultValue());
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ADTException, ExpressionException {
        typeEnv.add(name,typ);
        return typeEnv;
    }
}

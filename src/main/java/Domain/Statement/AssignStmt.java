package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIStack;
import Domain.Expression.IExpression;
import Domain.PrgState;
import Domain.Type.Type;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionException;
import Exceptions.StmtException;
import Exceptions.TypeException;

public class AssignStmt implements IStmt{
    String id;
    IExpression exp;

    public AssignStmt(String id, IExpression exp) {
        this.id = id;
        this.exp = exp;
    }

    public String toString(){ return id+"="+ exp.toString();}

    @Override
    public PrgState execute(PrgState state) throws StmtException, ADTException, TypeException, ExpressionException {
        MyIStack<IStmt> stk=state.getExeStack();
        MyIDictionary<String, Value> symTbl= state.getSymbolTable();
        if(symTbl.isKey(id)) {
            Value val = exp.eval(symTbl,state.getHeap());
            Type typId = (symTbl.lookup(id)).getType();
            if (val.getType().equals(typId)) {
                symTbl.update(id, val);
            }
            else
                throw new TypeException("declared type of variable" + id + " and type of the assigned expression do not match");
        }
        else throw new StmtException("the used variable" +id + " was not declared before");
        return null;

    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ADTException, ExpressionException {
        Type typevar = typeEnv.lookup(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new StmtException("Assignment: right hand side and left hand side have different types ");
    }
}



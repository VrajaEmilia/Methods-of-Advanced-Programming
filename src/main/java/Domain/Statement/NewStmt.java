package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Expression.IExpression;
import Domain.PrgState;
import Domain.Type.RefType;
import Domain.Type.Type;
import Domain.Value.RefValue;
import Domain.Value.Value;
import Exceptions.*;

import java.beans.Expression;

public class NewStmt implements IStmt{
    String var_name;
    IExpression exp;

    public NewStmt(String var_name, IExpression exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ADTException, TypeException, ExpressionException, FileException {
        MyIDictionary<String, Value> symTbl= state.getSymbolTable();
        MyIHeap heap = state.getHeap();
        if(!symTbl.isKey(var_name))
            throw new StmtException("Undefined variable!");
        RefValue refval = (RefValue) symTbl.lookup(var_name);
        Value value=exp.eval(symTbl,heap);
        if(!value.getType().equals(refval.getLocationType()))
            throw new StmtException("Type is not matching!");
        int addr = heap.add(value);
        symTbl.update(var_name,new RefValue(addr, refval.getLocationType()));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ADTException, ExpressionException {
        Type typevar = typeEnv.lookup(var_name);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new StmtException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return "NewStmt(" + var_name + ',' + exp + ')';
    }
}

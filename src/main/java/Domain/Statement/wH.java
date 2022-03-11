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

public class wH implements IStmt{
    String var_name;
    IExpression exp;

    public wH(String var_name, IExpression exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ADTException, TypeException, ExpressionException, FileException {
        MyIDictionary<String, Value> symTbl= state.getSymbolTable();
        MyIHeap heap = state.getHeap();
        if(!symTbl.isKey(var_name))
            throw new StmtException("Variable name not found in symbol table!");
        Value val = symTbl.lookup(var_name);
        if(!(val instanceof RefValue))
            throw new StmtException("Value is not of reference type!");
        RefValue ref = (RefValue) val;
        if(heap.isFree(ref.getAddr()))
            throw new StmtException("Variable not defined!");
        Value val2 = exp.eval(symTbl,heap);
        if(!val2.getType().equals(ref.getLocationType()))
            throw new StmtException("The type does not match!");
        heap.setHeap(ref.getAddr(),val2);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ADTException, ExpressionException {
        Type typeVar= typeEnv.lookup(var_name);
        Type typeExpr = exp.typecheck(typeEnv);

        if (!(typeVar.equals(new RefType(typeExpr)))){
            throw new StmtException("Var type should be a ref");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "writeHeap(" + var_name +
                "," + exp +
                ')';
    }
}

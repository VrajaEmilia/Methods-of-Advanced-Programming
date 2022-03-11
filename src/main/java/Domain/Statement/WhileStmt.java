package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIStack;
import Domain.Expression.IExpression;
import Domain.PrgState;
import Domain.Type.BoolType;
import Domain.Type.Type;
import Domain.Value.BoolValue;
import Domain.Value.Value;
import Exceptions.*;

public class WhileStmt implements IStmt {
    IExpression exp;
    IStmt stmt;

    public WhileStmt(IExpression exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ADTException, TypeException, ExpressionException, FileException {
        MyIStack<IStmt> stk = state.getExeStack();
        Value val = this.exp.eval(state.getSymbolTable(),state.getHeap());
        if(!val.getType().equals(new BoolType()))
            throw new StmtException("Value needs to be of bool type!");

        boolean cond = ((BoolValue) val).isVal();
        if(cond) {
            stk.push(this);
            stk.push(stmt);
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ADTException, ExpressionException {
        stmt.typecheck(typeEnv);
        Type typeexp=exp.typecheck(typeEnv);
        if(!(typeexp.equals(new BoolType())))
        {
            throw new StmtException("Expression should be a bool");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "While(" + exp + ")"+"{"+
                stmt +
                '}';
    }
}

package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.Expression.IExpression;
import Domain.PrgState;
import Domain.Type.BoolType;
import Domain.Type.Type;
import Domain.Value.BoolValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionException;
import Exceptions.StmtException;
import Exceptions.TypeException;

public class IfStmt implements IStmt {
    IExpression exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(IExpression exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public String toString() {
        return "IF(" + exp.toString() + ") THEN (" + thenS.toString() + ") ELSE (" + elseS.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ADTException, TypeException, ExpressionException {
        Value val = exp.eval(state.getSymbolTable(),state.getHeap());
        if (val.getType().equals(new BoolType())) {
            BoolValue result = (BoolValue) val;
            if (result.isVal())
                state.getExeStack().push(thenS);
            else state.getExeStack().push(elseS);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ADTException, ExpressionException {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.get_copy());
            elseS.typecheck(typeEnv.get_copy());
            return typeEnv;
        }
        else
            throw new StmtException("The condition of IF has not the type bool");
    }
}


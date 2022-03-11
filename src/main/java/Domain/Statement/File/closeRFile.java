package Domain.Statement.File;

import Domain.ADT.MyIDictionary;
import Domain.Expression.IExpression;
import Domain.PrgState;
import Domain.Statement.IStmt;
import Domain.Type.StringType;
import Domain.Type.Type;
import Domain.Value.StringValue;
import Domain.Value.Value;
import Exceptions.*;

import java.io.BufferedReader;
import java.io.IOException;

public class closeRFile implements IStmt {
    IExpression exp;

    public closeRFile(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ADTException, TypeException, ExpressionException, FileException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        Value val = exp.eval(symbolTable,state.getHeap());
        if (!val.getType().equals(new StringType()))
            throw new FileException("The file path should be string!");
        MyIDictionary<StringValue, BufferedReader> FileTable = state.getFileTable();
        StringValue value = (StringValue) val;
        if(!FileTable.isKey(value))
            throw new FileException("The file does not exist in the file table!");
        BufferedReader fileDescriptor = FileTable.lookup(value);

        try {
            fileDescriptor.close();

        } catch (IOException ioerror) {
            throw new FileException("An error has occured while closing the file!");
        }
        return state;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ADTException, ExpressionException {
        Type typeExp = exp.typecheck(typeEnv);
        if (!(typeExp.equals(new StringType())))
            throw new StmtException("Expression should be a string");
        else
            return typeEnv;
    }

    public String toString() {
        return "close("+this.exp+")";
    }
}

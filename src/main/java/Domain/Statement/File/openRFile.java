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
import java.io.FileReader;
import java.io.IOException;

public class openRFile implements IStmt {
    IExpression exp;

    public openRFile(IExpression exp) {
        this.exp = exp;
    }


    @Override
    public PrgState execute(PrgState state) throws StmtException, ADTException, TypeException, ExpressionException, FileException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        Value val = exp.eval(symbolTable, state.getHeap());
        if (!val.getType().equals(new StringType()))
            throw new FileException("The file path should be a string!");
        MyIDictionary<StringValue, BufferedReader> FileTable = state.getFileTable();
        StringValue fileName = (StringValue) val;
        if(FileTable.isKey(fileName))
            throw new FileException("This file already exists in the table!");
        try {
            BufferedReader fileDescriptor = new BufferedReader(new FileReader(fileName.getVal()));
            FileTable.add(fileName, fileDescriptor);
        } catch (IOException ioerror) {
            throw new FileException("File does not exist!");
        }
        return state;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ADTException, ExpressionException {
        Type typeExp = exp.typecheck(typeEnv);
        if (!(typeExp.equals(new StringType())))
            throw new StmtException("Expression should be a string");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "open("+this.exp+")";
    }
}

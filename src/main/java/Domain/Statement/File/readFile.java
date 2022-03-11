package Domain.Statement.File;

import Domain.ADT.MyIDictionary;
import Domain.Expression.IExpression;
import Domain.PrgState;
import Domain.Statement.IStmt;
import Domain.Type.IntType;
import Domain.Type.StringType;
import Domain.Type.Type;
import Domain.Value.IntValue;
import Domain.Value.StringValue;
import Domain.Value.Value;
import Exceptions.*;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements IStmt {
    IExpression exp;
    String var_name;

    public readFile(IExpression exp, String var_name) {
        this.exp = exp;
        this.var_name = var_name;
    }


    @Override
    public PrgState execute(PrgState state) throws StmtException, ADTException, TypeException, ExpressionException, FileException {
       MyIDictionary<String, Value> symbolTable=state.getSymbolTable();
       if(!symbolTable.isKey(var_name))
           throw new StmtException("The variable is not defined!");
        if (!symbolTable.lookup(var_name).getType().equals(new IntType()))
        { throw new StmtException("The variable should be int!");}
        Value value=exp.eval(symbolTable,state.getHeap());
        if (!value.getType().equals(new StringType()))
            throw new FileException("Expected a string for the file path!");
        MyIDictionary<StringValue, BufferedReader> FileTable=state.getFileTable();
        StringValue file_name = (StringValue) value;
        if (!FileTable.isKey(file_name))
            throw new FileException("The file does not exist in the file table!");
        BufferedReader fileDescriptor=FileTable.lookup(file_name);
        int read;
        try{
            String line=fileDescriptor.readLine();
            if (line==null)
                read =0;
            else
                read =Integer.parseInt(line);
        }
        catch (IOException ioerror)
        {
            throw new FileException("File couldn't be read!");
        }
        symbolTable.update(var_name,new IntValue(read));
        return state;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ADTException, ExpressionException {
        Type typeVar = (Type) typeEnv.lookup(var_name);
        if (!(typeVar instanceof IntType))
            throw new StmtException("Var type should be an int");
        Type typeExp = exp.typecheck(typeEnv);
        if (!(typeExp instanceof  StringType))
            throw new StmtException("Expression should be a string");
        return typeEnv;
    }

    public String toString() {
        return "read("+this.exp+")";
    }
}

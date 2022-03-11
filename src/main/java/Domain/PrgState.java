package Domain;

import Domain.ADT.*;
import Domain.Statement.IStmt;
import Domain.Value.StringValue;
import Domain.Value.Value;
import Exceptions.*;

import java.io.BufferedReader;

public class PrgState {
    private final MyIStack<IStmt> exeStack;
    private final MyIDictionary<String, Value> symbolTable;
    private final MyIList<Value> out;
    private MyIDictionary<StringValue, BufferedReader> FileTable;
    private MyIHeap heap;
    IStmt ogProgram;
    private static int prevID =0;
    private int id;

    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String, Value> symTableCopy, MyIList<Value> out, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap heap) {
        this.exeStack = stack;
        this.symbolTable = symTableCopy;
        this.out = out;
        this.FileTable=fileTable;
        this.heap = heap;
    }

    public int getId() {
        return id;
    }

    public synchronized  void setId(){
        prevID++;
        id= prevID;
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symbolTable, MyIList<Value> out, IStmt ogProgram) {
        this.exeStack = exeStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.ogProgram = ogProgram;
        this.id=0;
        this.exeStack.push(ogProgram);
    }

    public PrgState(IStmt prg)
    {
        exeStack = new MyStack<>();
        symbolTable = new MyDictionary<>();
        out = new MyList<>();
        ogProgram = prg; //recreate the entire original prg
        FileTable = new MyDictionary<>();
        heap = new MyHeap();
        this.id=0;
        exeStack.push(prg);
    }

    public MyIHeap getHeap() {return heap;}

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return FileTable;
    }

    public String toString()
    {
        return " PROGRAM STATE ID:"+this.id+ "\n" +
                "execution stack = " + exeStack.toString() +
                "\nsymbol table = " + symbolTable.toString() +
                "\noutput = " + out.toString() +
                "\nfiletable = " + FileTable.toString()+
                "\nheap = " + heap.toString()+"\n";

    }
    public String fileToString()
    {
        String out = "PRGSTATE ID:"+this.id;
        out+= "\nEXE STACK:\n" + this.exeStack.toString() +
                "\nSYM TABLE:\n" + this.symbolTable.toString()+
                "\nOUT:\n" + this.out.toString()+
                "\nFILE TABLE:\n" + this.FileTable.toString()+
                "\nHEAP:\n" + this.heap.toString()+
        "\n**********************************************************\n";
        return out;
    }
    public Boolean isNotCompleted()
    {
        return !this.exeStack.isEmpty();
    }

    public PrgState oneStep() throws PrgStateException, StmtException, FileException, TypeException, ADTException, ExpressionException {
        if(exeStack.isEmpty()) throw new PrgStateException("prgstate stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }
    }


package Repository;

import Domain.ADT.MyList;
import Domain.PrgState;
import Domain.Value.Value;
import Exceptions.PrgStateException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo{
    private List<PrgState> list;
    private String logFilePath;
    private PrgState crtPrg;


    public Repo() {
        list =new ArrayList<>();
    }

    public Repo(ArrayList<PrgState> repo) {
        this.list = repo;
    }

    public Repo(String logFilePath) {
        this.logFilePath = logFilePath;
        this.list = new ArrayList<PrgState>();
    }


    public Repo(ArrayList<PrgState> list, String logFilePath) {
        this.list = list;
        this.logFilePath = logFilePath;
    }

    public Repo(String logFilePath, PrgState crtPrg) {
        this.logFilePath = logFilePath;
        this.crtPrg = crtPrg;
    }

    @Override
    public void add(PrgState state) {
        list.add(state);
    }

    @Override
    public void logPrgStateExec(PrgState state) throws IOException {
        PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.append(state.fileToString());
        logFile.close();

    }

    @Override
    public List<PrgState> getPrgList() {
        return this.list;
    }

    @Override
    public void setPrgList(List<PrgState> new_list) {
        this.list = new_list;
    }

    @Override
    public PrgState getCrtPrg() {
        return this.list.get(0);
    }

}

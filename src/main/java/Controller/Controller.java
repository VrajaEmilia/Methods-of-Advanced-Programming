package Controller;

import Domain.ADT.MyIStack;
import Domain.PrgState;
import Domain.Statement.IStmt;
import Exceptions.*;
import Repository.IRepo;
import Repository.Repo;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    IRepo repo;
    ExecutorService executor;

    public Controller(IRepo repo) {
        this.repo = repo;
    }

    public Controller() {
        this.repo = new Repo();
    }

    public Object oneStep(PrgState state) throws ADTException, StmtException, TypeException, ExpressionException, FileException {
        MyIStack<IStmt> stk = state.getExeStack();
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }

    public IRepo getRepo() {
        return repo;
    }

    /*
        public void allSteps() throws StmtException, TypeException, ADTException, ExpressionException, IOException, FileException {
            PrgState state = repo.getCrtPrg();
            System.out.println(state.toString());
            repo.logPrgStateExec(state);
            while(!state.getExeStack().isEmpty())
            {
                oneStep(state);
                System.out.println(state.toString());
                repo.logPrgStateExec(state);
            }
        }

     */
    void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        //before the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //RUN concurrently one step for each of the existing PrgStates
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());
        //returns the list of new created PrgStates
        List<PrgState> newPrgList = executor.invokeAll(callList). stream()
                . map(future -> {
                    try {
                        return future.get();
                        }
                        catch(Exception e) {
                            throw new RuntimeException();
                        }
                        })
                        .filter(Objects::nonNull)
                                    .collect(Collectors.toList());
        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        repo.setPrgList(prgList);
    }

    public void allSteps() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        while(prgList.size() > 0){
            GarbageCollector.conservativeGarbageCollector(prgList);
            oneStepForAllPrg(prgList);
            for(PrgState prg: prgList)
                System.out.println(prg);
            //remove the completed programs
            prgList=removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList)
    {
        return inPrgList.stream().filter(PrgState::isNotCompleted).collect(Collectors.toList());
    }
}

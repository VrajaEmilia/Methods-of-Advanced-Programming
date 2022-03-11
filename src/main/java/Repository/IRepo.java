package Repository;

import Controller.Controller;
import Domain.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepo {
    void add(PrgState state);
    void logPrgStateExec(PrgState state) throws IOException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> new_list);

    PrgState getCrtPrg();
}

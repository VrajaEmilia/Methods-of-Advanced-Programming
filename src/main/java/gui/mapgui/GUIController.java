package gui.mapgui;

import Controller.Controller;
import Domain.ADT.MyDictionary;
import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Expression.*;
import Domain.PrgState;
import Domain.Statement.*;
import Domain.Statement.File.closeRFile;
import Domain.Statement.File.openRFile;
import Domain.Statement.File.readFile;
import Domain.Type.BoolType;
import Domain.Type.IntType;
import Domain.Type.RefType;
import Domain.Type.StringType;
import Domain.Value.BoolValue;
import Domain.Value.IntValue;
import Domain.Value.StringValue;
import Domain.Value.Value;
import Exceptions.*;
import Repository.IRepo;
import Repository.Repo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GUIController implements Initializable {

    private Controller ProgramController;

    private ArrayList<IStmt> examples;

    @FXML
    private ListView<String> ExListView;

    @FXML
    private ListView<String> idsList;

    @FXML
    private TextField NoPrograms;

    @FXML
    private TableView<Map.Entry<String,String>> SymTable;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> SVariable;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> Svalue;


    @FXML
    private TableView<Map.Entry<String,String>>HeapTable;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> HAdress;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> HValue;

    @FXML
    private ListView<String> StackList;

    @FXML
    private ListView<String> OutList;

    @FXML
    private ListView<String> FileTableList;

    @FXML
    void OneStepButtonClicked(ActionEvent event) throws StmtException, FileException, TypeException, ADTException, ExpressionException, IOException {
        try {
            ProgramController.oneStep(ProgramController.getRepo().getCrtPrg());
            this.updateTables();
        }
        catch (Exception exc)
        {

            //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Warning.fxml")));
            Label label = new Label(exc.getMessage());
            Scene scene = new Scene(label,300,50);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("EXCEPTION");
            primaryStage.setScene(scene);
            primaryStage.initModality(Modality.WINDOW_MODAL);
            primaryStage.show();

        }
    }

    private void updateTables() {
        updateStack();
        updateOut();
        updateFileTable();
        updateSymTable();
        updateHeap();
        updateIds();
    }

    private void updateIds() {
        ObservableList<String> ids = this.idsList.getItems();
        String id = String.valueOf(this.ProgramController.getRepo().getCrtPrg().getId());
        if(!ids.contains(id))
            ids.add(id);
        this.idsList.setItems(ids);
    }

    private void updateHeap() {
        MyIHeap heap = this.ProgramController.getRepo().getCrtPrg().getHeap();
        List<Map.Entry<String, String>> heapList= new ArrayList<>();
        for(Map.Entry<Integer, Value> element: heap.getHeap().entrySet()){
            Map.Entry<String, String> entry =
                    new AbstractMap.SimpleEntry<String, String>(element.getKey().toString(), element.getValue().toString());
            heapList.add(entry);
        }
        this.HeapTable.setItems(FXCollections.observableList(heapList));
    }

    private void updateSymTable() {
        MyIDictionary<String, Value> symTable = this.ProgramController.getRepo().getCrtPrg().getSymbolTable();
        List<Map.Entry<String, String>> symTableList = new ArrayList<>();
        for(Map.Entry<String, Value> element: symTable.getElems().entrySet()){
            Map.Entry<String, String> entry =
                    new AbstractMap.SimpleEntry<String, String>(element.getKey(), element.getValue().toString());
            symTableList.add(entry);
        }
        this.SymTable.setItems(FXCollections.observableList(symTableList));
    }

    private void updateFileTable() {
        Set<StringValue> fileItems = ProgramController.getRepo().getCrtPrg().getFileTable().getElems().keySet();
        ObservableList<String> files = FXCollections.observableArrayList();
        for(StringValue val: fileItems)
            files.add(val.toString());
        this.FileTableList.setItems(files);
    }

    private void updateOut() {
        List<Value> outItems = ProgramController.getRepo().getCrtPrg().getOut().getAll();
        ObservableList<String> out = FXCollections.observableArrayList();
        for(Value val: outItems)
            out.add(val.toString());
        this.OutList.setItems(out);
    }

    private void updateStack() {
        List<IStmt> stackItems = ProgramController.getRepo().getCrtPrg().getExeStack().getAll();
        ObservableList<String> stack = FXCollections.observableArrayList();
        for(IStmt val: stackItems)
            stack.add(val.toString());
        this.StackList.setItems(stack);
    }

    @FXML
    void SelectButtonClicked(ActionEvent event) throws IOException, StmtException, ADTException, ExpressionException {
        try {
            clearTables();
            int index = ExListView.getSelectionModel().getSelectedIndex();
            PrgState prgState = new PrgState(this.examples.get(index));
            this.ProgramController = createController(prgState, index);
            this.ProgramController.getRepo().getPrgList().get(0).getExeStack().getAll().get(0).typecheck(new MyDictionary<>());
            updateTables();
        }
        catch (Exception exc)
        {

            //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Warning.fxml")));
            Label label = new Label(exc.getMessage());
            Scene scene = new Scene(label,300,50);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("EXCEPTION");
            primaryStage.setScene(scene);
            primaryStage.initModality(Modality.WINDOW_MODAL);
            primaryStage.show();
        }


    }
    void clearTables()
    {
        this.StackList.setItems(FXCollections.observableArrayList());
        this.OutList.setItems(FXCollections.observableArrayList());
        this.FileTableList.setItems(FXCollections.observableArrayList());
        this.idsList.setItems(FXCollections.observableArrayList());
        this.NoPrograms.clear();
        this.SymTable.setItems(FXCollections.observableArrayList());
        this.HeapTable.setItems(FXCollections.observableArrayList());
    }

    Controller createController(PrgState prgState,int index)
    {
        IRepo repository = new Repo("C:\\Users\\Emi\\IdeaProjects\\MapGUI\\log" + index+1 + ".txt");
        repository.add(prgState);
        return new Controller(repository);
    }

    public ArrayList<IStmt> loadList() {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new BoolValue(true))), new PrintStmt(new
                        VarExp("v"))));
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)),
                                new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+',
                                        new VarExp("a"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))))));
        IStmt ex3 =  new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
        IStmt ex4 =  new CompStmt(new VarDeclStmt("varf",new StringType()),
                new CompStmt(new AssignStmt("varf",new ValueExp(new StringValue("C:\\Users\\Emi\\IdeaProjects\\untitled2\\test.in"))),
                        new CompStmt(new VarDeclStmt("varc",new IntType()),
                                new CompStmt(new openRFile(new VarExp("varf")),
                                        new CompStmt(new readFile(new VarExp("varf"),"varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new readFile(new VarExp("varf"),"varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new closeRFile(new VarExp("varf"))))))))));
        IStmt ex5 =new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+', new rH(new rH(new VarExp("a"))),
                                                        new ValueExp(new IntValue(5)))))))));
        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                new CompStmt(new wH("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new rH(new VarExp("v")),
                                                new ValueExp(new IntValue(5))))))));
        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"),new ValueExp(new StringValue("bcd")),">"),
                                new CompStmt(new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('-',new VarExp("v"),new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));
        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new forkStmt(
                                                new CompStmt(new wH("a", new ValueExp(new IntValue(30))),
                                                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                                        new PrintStmt(new rH(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new rH(new VarExp("a")))))))));
        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new forkStmt(
                                                new CompStmt(new wH("a", new ValueExp(new IntValue(30))),
                                                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                                        new PrintStmt(new rH(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new rH(new VarExp("a")))))))));
        ArrayList<IStmt> ex = new ArrayList<>();
        ex.add(ex1);
        ex.add(ex2);
        ex.add(ex3);
        ex.add(ex4);
        ex.add(ex5);
        ex.add(ex6);
        ex.add(ex7);
        ex.add(ex8);
        ex.add(ex9);

        this.examples = ex;
        return ex;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //add the examples in the ExListView
        ObservableList<String> List = FXCollections.observableArrayList();
        ArrayList<IStmt> ex = loadList();
        for(int i =1; i<=ex.size();i++)
        {
            List.add(i +":"+ ex.get(i-1).toString());
        }
        this.ExListView.setItems(List);

        this.SVariable.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey()));
        this.Svalue.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue()));
        this.HAdress.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey()));
        this.HValue.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue()));
    }
}

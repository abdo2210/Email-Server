package web.lab.email.database;

import java.util.*;

public class TableBuilder {

    private String name;
    private HashMap<String, Column<Integer, String>> columns;


    public void reset(){
        this.name = "";
        this.columns = null;
    }

    public void setName(String name){
        this.name = name;
    }

//    public void setColumns(HashMap<String>)

    public void addColumn(String name){
        if(this.columns == null){
            this.columns = new HashMap<>();
        }
        if(this.columns.containsKey(name)){
            //column names must be unique
            throw new RuntimeException("A column with the name \""+name+"\" already exists in the table");
        }
        this.columns.put(name, new Column<>(name));
    }

    public Table getResult(){
        return new Table(this.name, this.columns);
    }
}

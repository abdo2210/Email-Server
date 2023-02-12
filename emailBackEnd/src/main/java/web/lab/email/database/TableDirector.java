package web.lab.email.database;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class TableDirector {

    private TableBuilder tableBuilder;

    public TableDirector(TableBuilder builder){
        this.tableBuilder = builder;
    }

    public Table createTable(String tableName, List<String> columnNames){
        //for creating a table for the first time
        this.tableBuilder.reset();
        this.tableBuilder.setName(tableName);
        columnNames.forEach((columnName)->{
            this.tableBuilder.addColumn(columnName);
        });
        Table table = this.tableBuilder.getResult();
        JSONHandler.writeTableFile(table);
        return table;
    }

    public Table loadTable(String tableName){
        //instantiates a Table object at runtime from json, if its
        //json file exists
        JSONObject jsonTable = JSONHandler.readTableFile(tableName);
        if(jsonTable == null){
            //in case that the json file does not exist
            throw new RuntimeException("Json file for table \""+tableName+"\" does not exist");
        }
        this.tableBuilder.reset();
        this.tableBuilder.setName(jsonTable.getString("name"));
        JSONArray arr = jsonTable.getJSONArray("columnNames");
        arr.forEach((name)->this.tableBuilder.addColumn((String)name));
        //looping through keys of the first record to get column names
        if(jsonTable.isNull("records")){
            //for loading empty tables
            return this.tableBuilder.getResult();
        }
        //nonempty records
        JSONObject records = jsonTable.getJSONObject("records");

        Table table = this.tableBuilder.getResult();
        //filling up columns
        for(int i = 0; i < jsonTable.getInt("nextId"); i++){
            if(records.keySet().contains(Integer.toString(i))) {
                table.addRecord(records.getJSONObject(Integer.toString(i)));
            }
        }
        return table;
    }
}

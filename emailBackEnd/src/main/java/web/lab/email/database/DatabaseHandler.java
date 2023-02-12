package web.lab.email.database;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHandler {
    // facade pattern for all database classes
    private TableBuilder builder;
    private TableDirector director;
    private HashMap<String, Table> tables;

    public DatabaseHandler(){
        this.builder = new TableBuilder();
        this.director = new TableDirector(this.builder);
        this.tables = new HashMap<>();
    }

    public void createTable(String tableName, List<String> columnNames){
        if(this.tables.containsKey(tableName)){
            throw new RuntimeException("Table \""+tableName+"\" already exists");
        }
        Table table = this.director.createTable(tableName, columnNames);
        this.tables.put(tableName, table);
    }

    public void loadTable(String tableName){
        if(!this.tables.containsKey(tableName)){
            //throws error if json file does not exist
            Table table =  this.director.loadTable(tableName);
            this.tables.put(tableName, table);
        }
    }

    private void updateTable(String tableName){
        if(!this.tables.containsKey(tableName)){
            throw new RuntimeException("Table \""+tableName+"\" does not exist");
        }
        JSONHandler.writeTableFile(this.tables.get(tableName));
    }

    public void addRow(String tableName, Object saveObject) throws NoSuchFieldException, IllegalAccessException {
        if(!this.tables.containsKey(tableName)){
            throw new RuntimeException("Table \""+tableName+"\" does not exist");
        }
        Class cls = saveObject.getClass();
        Table table = this.tables.get(tableName);
        JSONObject rowData = new JSONObject();

        //Here the value of each column in the table
        //is fetched from the object's fields using reflection.
        //The object's fields must have the same name as
        //the column's names.
        for(String columnName: table.getColumns().keySet()){
            Field field = cls.getDeclaredField(columnName);
            field.setAccessible(true);
            rowData.put(columnName, field.get(saveObject));
        }
        //updating runtime data
        table.addRecord(rowData);
        //updating data in json files
        updateTable(tableName);
    }

    public List<HashMap<String,String>> getRows(String tableName, TableAndCriterea conditions){
        //gets a list of all rows that satisfy a set of conditions
        if(!this.tables.containsKey(tableName)){
            throw new RuntimeException("Table \""+tableName+"\" does not exist");
        }
        Table table = this.tables.get(tableName);
        List<Integer> idList = conditions.meetCriteria();
        List<HashMap<String, String>> rows = new ArrayList<>();
        for(Integer id: idList){
            rows.add(table.getRecordById(id));
        }
        return rows;
    }
    public void deleteRows(String tableName, HashMap<String,String> conditions){
        //deletes row which holds the data of "deleteObject"
        if(!this.tables.containsKey(tableName)){
            throw new RuntimeException("Table \""+tableName+"\" does not exist");
        }
        Table table = this.tables.get(tableName);
        List<Criteria> conditionList = new ArrayList<>();
        conditions.forEach((field, value)->{
            Criteria c = new TableCriterea(table, field, value);
            conditionList.add(c);
        });
        TableAndCriterea filter = new TableAndCriterea(conditionList, table);
        List<Integer> rows = filter.meetCriteria();
        for(Integer id: rows){
            table.deleteRecord(id);
        }
        updateTable(tableName);
    }

    public void updateRow(String tableName, String columnName, HashMap<String,String> conditions, String newValue){
        //updates a certain field in a row
        if(!this.tables.containsKey(tableName)){
            throw new RuntimeException("Table \""+tableName+"\" does not exist");
        }
        Table table = this.tables.get(tableName);
        List<Criteria> conditionList = new ArrayList<>();
        conditions.forEach((field, value)->{
            Criteria c = new TableCriterea(table, field, value);
            conditionList.add(c);
        });
        TableAndCriterea filter = new TableAndCriterea(conditionList, table);
        List<Integer> rows = filter.meetCriteria();
        for(Integer id: rows){
            table.updateRecord(id, columnName, newValue);
        }
        updateTable(tableName);
    }

    public List<HashMap<String, String>> filterTable(String tableName, HashMap<String, String> conditions){
        Table table = this.tables.get(tableName);
        List<Criteria> conditionList = new ArrayList<>();
        conditions.forEach((field, value)->{
            Criteria c = new TableCriterea(table, field, value);
            conditionList.add(c);
        });
        TableAndCriterea filter = new TableAndCriterea(conditionList, table);
        return this.getRows(tableName, filter);
    }
    public List<HashMap<String, String>> getAllRows(String tableName){
        //gets a list of all rows in a table
        if(!this.tables.containsKey(tableName)){
            throw new RuntimeException("Table \""+tableName+"\" does not exist");
        }
        Table table = this.tables.get(tableName);
        return new ArrayList<>(table.getAllRecords().values());}
}

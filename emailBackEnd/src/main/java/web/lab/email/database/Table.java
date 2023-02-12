package web.lab.email.database;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table {
    //this class deals with columns and fills them with provided json data

    private String name;
    private HashMap<String, Column<Integer, String>> columns;
    private int nextId;


    public Table(String name, HashMap<String, Column<Integer, String>> columns){
        this.name = name;
        this.columns = columns;
        this.nextId = 0;
    }

    public String toString(){
        return this.columns.toString();
    }

    public String getName(){
        return this.name;
    }

    public HashMap<String, Column<Integer, String>> getColumns(){
        return this.columns;
    }

    public void addRecord(JSONObject obj){
        this.columns.keySet().forEach((key)->{
            this.columns.get(key).put(nextId, (String)obj.get(key));
        });
        nextId++;
    }

    public int getNextId(){
        return this.nextId;
    }

//    public void setNextId(int id){
//        this.nextId = id;
//    }

    public HashMap<Integer, HashMap<String, String>> getAllRecords(){
        HashMap<Integer, HashMap<String, String>> records = new HashMap<>();
        for(int i = 0; i < this.nextId; i++){
            HashMap<String, String> attributes = new HashMap<>();
            //in case that the record was deleted
            if(!this.recordExists(i)){
                continue;
            }
            for(Column column: this.columns.values()){
                attributes.put((String)column.getName(), (String)column.get(i));
            }
            records.put(i, attributes);
        }
        return records;
    }

    public HashMap<String, String> getRecordById(int id){
        if(!this.recordExists(id)){
            throw new RuntimeException("Record "+id+" does not exist");
        }
        HashMap<String, String> record = new HashMap<>();
        for(Column column: this.columns.values()){
            record.put((String)column.getName(), (String)column.get(id));
        }
        return record;
    }

    public String getFieldById(String fieldName, int id){
        if(this.recordExists(id) && this.columnExists(fieldName)) {
            return this.columns.get(fieldName).get(id);
        }else{
            throw new RuntimeException("Record or column do not exist");
        }
    }

    public List<Integer> searchWithField(String fieldName, String value){
        if(this.columnExists(fieldName)) {
            return this.columns.get(fieldName).getIDsByValue(value).stream().toList();
        }else{
            throw new RuntimeException("Column does not exist");
        }
    }


    public void updateRecord(int id, String fieldName, String newValue){
        if(this.recordExists(id) && this.columnExists(fieldName)) {
            this.columns.get(fieldName).put(id, newValue);
        }else{
            throw new RuntimeException("Record or column do not exist");
        }
    }

    public void deleteRecord(int id){
        if(this.recordExists(id)) {
            this.columns.forEach((key, column) -> {
                column.remove(id);
            });
        }else{
            throw new RuntimeException("Record with id = "+id+" does not exist");
        }
    }

    public boolean recordExists(int id){
        boolean exists = false;
        for(Column column: this.columns.values()){
            if(column.get(id)!=null){
                exists = true;
            }
        }
        return exists;
    }

    public boolean columnExists(String columnName){
        return this.columns.containsKey(columnName);
    }


    public List<Integer> getIdList(){
        return new ArrayList<>(this.getAllRecords().keySet());
    }

}
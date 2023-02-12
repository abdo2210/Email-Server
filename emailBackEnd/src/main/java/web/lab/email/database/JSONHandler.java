package web.lab.email.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.*;

import java.io.*;
import java.util.Scanner;

public class JSONHandler {

    //Performs CRUD operations with json

    //create,update
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void writeTableFile(Table table){
        try {
            JSONObject jsonTable = new JSONObject();
            jsonTable.put("name", table.getName());
            jsonTable.put("records", table.getAllRecords());
            jsonTable.put("nextId", table.getNextId());
            jsonTable.put("columnNames", table.getColumns().keySet());
            File file = new File(table.getName()+".json");
            FileWriter writer = new FileWriter(file);
            writer.write(jsonTable.toString());
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static JSONObject readTableFile(String tableName){
        try {
            File file = new File(tableName+".json");
            Scanner scanner = new Scanner(file);
            String s = "";
            while (scanner.hasNextLine()) {
                s += scanner.nextLine();
            }
            return new JSONObject(s);
        }catch(Exception e){
            //e.printStackTrace();
            return null;
        }
    }
}

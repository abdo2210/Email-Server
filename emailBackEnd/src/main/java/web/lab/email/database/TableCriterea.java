package web.lab.email.database;

import java.util.ArrayList;
import java.util.List;

public class TableCriterea implements Criteria {
    private Table table;
    private String columnToLookFor;
    private String columnValue;

    public TableCriterea(Table table, String columnToLookFor, String columnValue){
        this.table = table;
        this.columnValue = columnValue;
        if(this.table.columnExists(columnToLookFor)) {
            this.columnToLookFor = columnToLookFor;
        }else{
            throw new RuntimeException("Column \""+this.columnToLookFor
                    +"\" does not exist in table \""+this.table.getName()+"\"");
        }
    }

    @Override
    public List<Integer> meetCriteria(List<Integer> list) {
        List<Integer> filteredList = new ArrayList<>();
        for(Integer id: list){
            if(this.table.getFieldById(this.columnToLookFor, id).equals(this.columnValue)){
                filteredList.add(id);
            }
        }
        return filteredList;
    }
}

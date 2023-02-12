package web.lab.email.database;

import java.util.*;

public class Column<Integer, String> extends HashMap<Integer, String> {
    //Decorator dp
    private String name;
    private HashMap<String, Set<Integer>> reverseMap;

    public Column(String name){
        this.name = name;
        this.reverseMap = new HashMap<>();
    }

    @Override
    public String put(Integer id, String value) {
        //method for adding and updating
        if(this.reverseMap.get(value) == null){
            //adding (value, id) pair for the first time in reverseMap
            this.reverseMap.put(value, new HashSet<>());
        }
        //updating reverseMap
        //removing the id from the old value list
        if(this.containsKey(id)){
            this.reverseMap.get(this.get(id)).remove(id);
        }
        this.reverseMap.get(value).add(id);
        return super.put(id, value);
    }

    @Override
    public String get(Object id) {
        return super.get(id);
    }

    public Set<Integer> getIDsByValue(String value){
        return this.reverseMap.get(value);
    }

    @Override
    public String remove(Object id) {
        if(this.containsKey(id)){
            //remove the id from value list
            this.reverseMap.get(this.get(id)).remove(id);
        }
        return super.remove(id);
    }

    public String getName(){
        return this.name;
    }
}


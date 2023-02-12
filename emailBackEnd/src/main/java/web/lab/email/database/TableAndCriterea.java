package web.lab.email.database;


import java.util.ArrayList;
import java.util.List;

public class TableAndCriterea extends AndCriteria{

    private List<Criteria> criteriaList;
    private Table table;

    public TableAndCriterea(List<Criteria> criteriaList, Table table) {
        super(criteriaList);
        this.criteriaList = criteriaList;
        this.table = table;
    }

    public List<Integer> meetCriteria(){
        List<Integer> lst = this.table.getIdList();
        for(Criteria c: criteriaList){
            lst = c.meetCriteria(lst);
        }
        return lst;
    }
}

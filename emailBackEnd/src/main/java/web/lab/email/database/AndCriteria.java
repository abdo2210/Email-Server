package web.lab.email.database;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AndCriteria implements Criteria{

    private List<Criteria> criteriaList;

    public AndCriteria(List<Criteria> criteriaList){
        this.criteriaList = criteriaList;
    }

    public List<Integer> meetCriteria(List<Integer> list) {
        List<Integer> passedList = list;
        for(Criteria c: criteriaList){
            passedList = c.meetCriteria(passedList);
        }
        return passedList;
    }
}


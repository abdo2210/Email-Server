package web.lab.email.database;


import java.util.List;

public interface Criteria {
    public List<Integer> meetCriteria(List<Integer> list);
}
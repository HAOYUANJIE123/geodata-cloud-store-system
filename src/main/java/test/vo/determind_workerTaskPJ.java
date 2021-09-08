package test.vo;

import test.dao.Task;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hao123 on 2019/9/25.
 */
public class determind_workerTaskPJ {
    public String getDetermind_workerTaskid() {
        return determind_workerTaskid;
    }

    public void setDetermind_workerTaskid(String determind_workerTaskid) {
        this.determind_workerTaskid = determind_workerTaskid;
    }

    public List<String> getAllTaskApplicant() {
        return allTaskApplicant;
    }

    public void setAllTaskApplicant(List<String> allTaskApplicant) {
        this.allTaskApplicant = allTaskApplicant;
    }

    String determind_workerTaskid;
    List<String> allTaskApplicant=new LinkedList<>();
}

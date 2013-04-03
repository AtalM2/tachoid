package fr.univnantes.atal.android.tachoid.entity;

import java.util.ArrayList;
import java.util.List;

public class Domain {

    private List<Task> tasks;
    private String name;

    public Domain(String name) {
        this.name = name;
        this.tasks = new ArrayList<Task>();
    }

    /**
     * @return the tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

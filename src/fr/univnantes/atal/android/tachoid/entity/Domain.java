package fr.univnantes.atal.android.tachoid.entity;

import java.util.ArrayList;

public class Domain extends ArrayList<Task> {

    private String name;

    public Domain(String name) {
        this.name = name;
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
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Domain : " + name + " [\n");
        for (Task task : this) {
            builder.append("\t").append(task.toString()).append("\n");
        }
        builder.append("]\n");
        return builder.toString();
    }
}

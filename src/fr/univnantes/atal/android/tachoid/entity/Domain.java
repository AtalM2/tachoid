package fr.univnantes.atal.android.tachoid.entity;

import java.util.ArrayList;

public class Domain extends ArrayList<Task> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

    public Domain(String name) {
        this.name = name;
    }

    public int getProgress() {
        int count = 0;
        for (Task task : this) {
            if (task.isChecked()) {
                count++;
            }
        }
        return count;
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

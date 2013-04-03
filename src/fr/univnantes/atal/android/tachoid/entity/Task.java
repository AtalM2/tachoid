package fr.univnantes.atal.android.tachoid.entity;

public class Task {
    private boolean checked;
    private String name;
    
    public Task(String name) {
        this(name, false);
    }
    
    public Task(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

    /**
     * @return the checked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * @param checked the checked to set
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
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

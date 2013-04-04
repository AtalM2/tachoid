package fr.univnantes.atal.android.tachoid.persistence;

import android.app.Activity;
import android.content.Context;
import fr.univnantes.atal.android.tachoid.entity.Data;
import fr.univnantes.atal.android.tachoid.entity.Domain;
import fr.univnantes.atal.android.tachoid.entity.Task;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextPersistenceManager implements PersistenceManager {

    private final String FILE_NAME = "data";
    private final String DOMAIN_TAG = "DOMAIN";
    private final String TASK_TAG = "TASK";
    private final String SPLITTER = "-";
    private Activity activity;

    public TextPersistenceManager(Activity activity) {
        this.activity = activity;
        File file = this.activity.getBaseContext().getFileStreamPath(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(TextPersistenceManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Data load() {
        Data data = new Data();
        try {
            InputStream inputStream = activity.openFileInput(FILE_NAME);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;

                while ((receiveString = bufferedReader.readLine()) != null) {
                    System.out.println("=========== RECEIVE : " + receiveString + " =====================");
                    int splitter = receiveString.indexOf(SPLITTER);
                    if (splitter != -1) {
                        String tag = receiveString.substring(0, splitter);
                        String value = receiveString.substring(splitter + 1);
                        Domain currentDomain = null;
                        if (tag.equals(DOMAIN_TAG)) {
                            currentDomain = handleDomain(value);
                            data.add(currentDomain);
                        } else if (tag.equals(TASK_TAG)) {
                            if (currentDomain != null) {
                                Task currentTask = handleTask(value);
                                currentDomain.getTasks().add(currentTask);
                            }
                        }
                    }
                }
                inputStream.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextPersistenceManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TextPersistenceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    @Override
    public void save(Data data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(activity.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            for (Domain domain : data) {
                System.out.println("=========== DOMAINE : " + writeDomain(domain) + " =====================");
                String output = writeDomain(domain) + System.getProperty("line.separator");
                outputStreamWriter.write(output);
                for (Task task : domain.getTasks()) {
                    System.out.println("=========== TASK : " + writeTask(task) + " =====================");
                    output = writeTask(task) + System.getProperty("line.separator");
                    outputStreamWriter.write(output);
                }
            }
            outputStreamWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(TextPersistenceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Domain handleDomain(String domainString) {
        return new Domain(domainString);
    }

    private String writeDomain(Domain domain) {
        return DOMAIN_TAG + SPLITTER + domain.getName();
    }

    private Task handleTask(String taskString) {
        int splitter = taskString.indexOf(SPLITTER);
        String checked = taskString.substring(0, splitter);
        String name = taskString.substring(splitter + 1);
        Task task = new Task(name, checked.equals("1"));
        return task;
    }

    private String writeTask(Task task) {
        return TASK_TAG + SPLITTER + (task.isChecked() ? "1" : "0") + SPLITTER + task.getName();
    }
}

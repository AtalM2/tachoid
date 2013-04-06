package fr.univnantes.atal.android.tachoid.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import fr.univnantes.atal.android.tachoid.R;
import fr.univnantes.atal.android.tachoid.adapter.TasksAdapter;
import fr.univnantes.atal.android.tachoid.entity.Data;
import fr.univnantes.atal.android.tachoid.entity.Task;
import fr.univnantes.atal.android.tachoid.persistence.PersistenceManager;
import fr.univnantes.atal.android.tachoid.persistence.TextPersistenceManager;

public class TasksActivity extends Activity {

    public PersistenceManager manager;
    public Data data;
    public int domainId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("TASKS : ON CREATE");
        setContentView(R.layout.tasks_main);

        // Chargement des données
        manager = new TextPersistenceManager(this);
        data = manager.load();

        // Chargement de la liste
        Intent intent = getIntent();
        domainId = intent.getIntExtra("domain", -1);
        if (domainId == -1) {
            finish();
        }

        // Initialisation du titre
        TextView textView = (TextView) findViewById(R.id.tasks_title);
        textView.setText(data.get(domainId).getName());

        // Initialisation de la liste des tâches
        final ListView listView = (ListView) findViewById(R.id.tasks_listView);
        TasksAdapter adapter = new TasksAdapter(this);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        System.out.println("TASKS : ON RESTART");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("TASKS : ON START");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("TASKS : ON RESUME");
        load();
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("TASKS : ON PAUSE");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("TASKS : ON STOP");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("TASKS : ON DESTROY");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tasks_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tasks_options_addTask:
                showAddTaskDialog(null);
                return true;
            case R.id.tasks_options_deleteAll:
                data.get(domainId).clear();
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tasks_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.tasks_context_rename:
                renameTask((int) info.id);
                return true;
            case R.id.tasks_context_delete:
                deleteTask((int) info.id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void showAddTaskDialog(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Ajouter une tâche");
        final EditText input = new EditText(this);
        input.setSingleLine();
        alertDialogBuilder
                .setView(input)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String name = input.getText().toString();
                if (!name.equals("")) {
                    Task task = new Task(name);
                    data.get(domainId).add(task);
                    save();
                } else {
                    dialog.cancel();
                }
            }
        })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void renameTask(int taskId) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final Task task = data.get(domainId).get(taskId);
        alertDialogBuilder.setTitle("Renommer la tâche");
        final EditText input = new EditText(this);
        input.setText(task.getName());
        int position = input.length();
        Editable text = input.getText();
        Selection.setSelection(text, position);
        alertDialogBuilder
                .setView(input)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String name = input.getText().toString();
                if (!name.equals("")) {
                    task.setName(name);
                    save();
                } else {
                    dialog.cancel();
                }
            }
        })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteTask(int taskId) {
        data.get(domainId).remove(taskId);
        save();
    }

    private void save() {
        manager.save(data);
        ListView listView = (ListView) findViewById(R.id.tasks_listView);
        TasksAdapter adapter = (TasksAdapter) listView.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void load() {
        data = manager.load();
        ListView listView = (ListView) findViewById(R.id.tasks_listView);
        TasksAdapter adapter = (TasksAdapter) listView.getAdapter();
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    public void handleReturnClick(View view) {
        finish();
    }
}

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
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import fr.univnantes.atal.android.tachoid.R;
import fr.univnantes.atal.android.tachoid.adapter.DomainsAdapter;
import fr.univnantes.atal.android.tachoid.entity.Data;
import fr.univnantes.atal.android.tachoid.entity.Domain;
import fr.univnantes.atal.android.tachoid.persistence.PersistenceManager;
import fr.univnantes.atal.android.tachoid.persistence.TextPersistenceManager;

public class DomainsActivity extends Activity {

    private PersistenceManager manager;
    private Data data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("DOMAINS : ON CREATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.domains_main);

        // Chargement des données
        manager = new TextPersistenceManager(this);
        data = manager.load();

        // Initialisation de la liste des listes
        ListView listView = (ListView) findViewById(R.id.domains_listView);
        System.out.println(listView.toString());
        DomainsAdapter adapter = new DomainsAdapter(this, data);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDomain(position);
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        System.out.println("DOMAINS : ON RESTART");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("DOMAINS : ON START");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("DOMAINS : ON RESUME");
        load();
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("DOMAINS : ON PAUSE");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("DOMAINS : ON STOP");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("DOMAINS : ON DESTROY");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.domains_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.domains_options_addDomain:
                showAddDomainDialog(null);
                return true;
            case R.id.domains_options_deleteAll:
                data.clear();
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
        inflater.inflate(R.menu.domains_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.domains_context_rename:
                renameDomain((int) info.id);
                return true;
            case R.id.domains_context_delete:
                deleteDomain((int) info.id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void openDomain(int domainId) {
        Intent intent = new Intent(this, TasksActivity.class);
        intent.putExtra("domain", domainId);
        startActivity(intent);
    }

    public void showAddDomainDialog(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(getString(R.string.add_domain));
        final EditText input = new EditText(this);
        input.setSingleLine();
        alertDialogBuilder
                .setView(input)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String name = input.getText().toString();
                if (!name.equals("")) {
                    Domain domain = new Domain(name);
                    data.add(domain);
                    save();
                } else {
                    dialog.cancel();
                }
            }
        })
                .setNegativeButton(getString(R.string.cancel),new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void renameDomain(int domainId) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final Domain domain = data.get(domainId);
        alertDialogBuilder.setTitle(getString(R.string.rename_domain));
        final EditText input = new EditText(this);
        input.setText(domain.getName());
        int position = input.length();
        Editable text = input.getText();
        Selection.setSelection(text, position);
        alertDialogBuilder
                .setView(input)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String name = input.getText().toString();
                if (!name.equals("")) {
                    domain.setName(name);
                    save();
                } else {
                    dialog.cancel();
                }
            }
        })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteDomain(int domainId) {
        data.remove(domainId);
        save();
    }

    private void save() {
        manager.save(data);
        ListView listView = (ListView) findViewById(R.id.domains_listView);
        DomainsAdapter adapter = (DomainsAdapter) listView.getAdapter();
        adapter.notifyDataSetChanged();        
    }

    private void load() {        
        data = manager.load();
        ListView listView = (ListView) findViewById(R.id.domains_listView);
        DomainsAdapter adapter = (DomainsAdapter) listView.getAdapter();
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }
}

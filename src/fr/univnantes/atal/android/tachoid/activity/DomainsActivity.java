package fr.univnantes.atal.android.tachoid.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Chargement des données
        manager = new TextPersistenceManager(this);
        data = manager.load();

        // Initialisation de la liste des listes
        ListView listView = (ListView) findViewById(R.id.listView);
        DomainsAdapter adapter = new DomainsAdapter(this, data);
        listView.setAdapter(adapter);
    }

    public void showAddDomainDialog(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Ajouter une liste");
        final EditText input = new EditText(this);
        alertDialogBuilder
                .setView(input)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String name = input.getText().toString();
                if (!name.equals("")) {
                    Domain domain = new Domain(name);
                    data.add(domain);
                    manager.save(data);
                    
                    ListView listView = (ListView) findViewById(R.id.listView);
                    DomainsAdapter adapter = (DomainsAdapter) listView.getAdapter();
                    adapter.notifyDataSetChanged();
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
}

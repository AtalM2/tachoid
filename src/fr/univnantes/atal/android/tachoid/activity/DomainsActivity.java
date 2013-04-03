package fr.univnantes.atal.android.tachoid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import fr.univnantes.atal.android.tachoid.R;
import fr.univnantes.atal.android.tachoid.adapter.DomainsAdapter;
import fr.univnantes.atal.android.tachoid.entity.Data;
import fr.univnantes.atal.android.tachoid.persistence.PersistenceManager;
import fr.univnantes.atal.android.tachoid.persistence.TextPersistenceManager;

public class DomainsActivity extends Activity
{
    
    public ListView listView;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        PersistenceManager manager = new TextPersistenceManager(this);
        Data data = manager.load();
        listView = (ListView) findViewById(R.id.listView);
        DomainsAdapter adapter = new DomainsAdapter(this, data);
        listView.setAdapter(adapter);
    }
}

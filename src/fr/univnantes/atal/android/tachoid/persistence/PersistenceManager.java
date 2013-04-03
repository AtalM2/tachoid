package fr.univnantes.atal.android.tachoid.persistence;

import fr.univnantes.atal.android.tachoid.entity.Data;

public interface PersistenceManager {
        
    public Data load();
    
    public void Save(Data data);
    
}

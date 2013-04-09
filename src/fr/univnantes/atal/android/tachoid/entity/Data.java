package fr.univnantes.atal.android.tachoid.entity;

import java.util.ArrayList;

public class Data extends ArrayList<Domain> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (Domain domain : this) {
            builder.append(domain.toString()).append("\n");
        }
        builder.append("]\n");
        return builder.toString();
    }
}

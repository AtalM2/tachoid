package fr.univnantes.atal.android.tachoid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import fr.univnantes.atal.android.tachoid.R;
import fr.univnantes.atal.android.tachoid.entity.Data;
import fr.univnantes.atal.android.tachoid.entity.Domain;

public class DomainsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Data data;

    public DomainsAdapter(Context context, Data data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(Data data) {
        this.data = data;
    }

    private class ViewHolder {

        TextView tvName;
        ProgressBar pgProgress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position < getCount() - 1) {
            ViewHolder holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.domain, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.domain_name);
            holder.pgProgress = (ProgressBar) convertView.findViewById(R.id.domain_progress);
            convertView.setTag(holder);
            System.out.println("1");
            Domain domain = data.get(position);
            System.out.println("2");
            holder.tvName.setText(domain.getName());
            System.out.println("3");
            holder.pgProgress.setMax(domain.size());
            System.out.println("4");
            holder.pgProgress.setProgress(domain.getProgress());
            System.out.println("5");
            return convertView;
        } else {
            convertView = inflater.inflate(R.layout.domains_add, null);
            return convertView;
        }
    }
}

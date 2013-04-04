package fr.univnantes.atal.android.tachoid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import fr.univnantes.atal.android.tachoid.R;
import fr.univnantes.atal.android.tachoid.activity.TasksActivity;
import fr.univnantes.atal.android.tachoid.entity.Data;
import fr.univnantes.atal.android.tachoid.entity.Task;
import fr.univnantes.atal.android.tachoid.persistence.PersistenceManager;

public class TasksAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Data data;
    private PersistenceManager manager;
    private int domainId;

    public TasksAdapter(Context context) {
        System.out.println("CONTEXT CLASS : " + context.getClass());
        inflater = LayoutInflater.from(context);
        TasksActivity activity = (TasksActivity) context;
        manager = activity.manager;
        data = activity.data;
        domainId = activity.domainId;
    }

    @Override
    public int getCount() {
        return data.get(domainId).size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(domainId).get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(Data data) {
        this.data = data;
    }

    private class ViewHolder {

        CheckBox cbName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final Task task = data.get(domainId).get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.task, null);
            holder.cbName = (CheckBox) convertView.findViewById(R.id.task_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cbName.setText(task.getName());
        holder.cbName.setChecked(task.isChecked());
        holder.cbName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.setChecked(isChecked);
                if (manager == null) {
                    System.out.println("MANAGER NULL");
                }
                if (data == null) {
                    System.out.println("DATA NULL");
                }
                manager.save(data);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}

package ca.concordia.gilgamesh;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ca.concordia.gilgamesh.models.Machine;

public class MachineListViewCustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Machine> machines;

    public MachineListViewCustomAdapter(Context context, ArrayList<Machine> machines) {
        this.context = context;
        this.machines = machines;
    }

    @Override
    public int getCount() {
        return machines.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.rowlist, parent, false);
        TextView MachineName = convertView.findViewById(R.id.MachineName_TextView);
        TextView MachineLocation = convertView.findViewById(R.id.MachineLocation_TextView);
        TextView MachineStatus = convertView.findViewById(R.id.MachineStatus_TextView);

        String status = machines.get(position).getStatus();

        if (status.equals("ON")) {
            MachineStatus.setTextColor(Color.GREEN);
            MachineStatus.setText("Status: " + status);
        } else if (status.equals("OFF")) {
            MachineStatus.setTextColor(Color.RED);
            MachineStatus.setText("Status: " + status);
        }


        MachineName.setText(machines.get(position).getName());
        MachineLocation.setText("Location: " + machines.get(position).getLocation());


        return convertView;
    }
}

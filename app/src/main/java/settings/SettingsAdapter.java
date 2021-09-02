package settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinic.R;

import java.util.ArrayList;
import java.util.List;

import doctor.RecyclerViewClickListener;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.MyViewHolder> {

    List<Setting> settings = new ArrayList<Setting>();
    Context context;
    private static RecyclerViewClickListener itemListener;


    public SettingsAdapter(Context ct , List<Setting> settings, RecyclerViewClickListener ItemListener){

        this.settings = settings;
        itemListener = ItemListener;
        context = ct;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_setting, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setting.setText(settings.get(position).getSetting());
        holder.settingDescription.setText(settings.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }

    public void updateSettings(List<Setting> settings){
        this.settings = settings;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView setting;
        TextView settingDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            setting = itemView.findViewById(R.id.setting);
            settingDescription = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }
}

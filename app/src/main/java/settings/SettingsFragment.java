package settings;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinic.MainActivity;
import com.example.clinic.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import doctor.RecyclerViewClickListener;

public class SettingsFragment extends Fragment implements RecyclerViewClickListener {

    RecyclerView recyclerView;
    SettingsAdapter adapter;

    List<Setting> settingList = new ArrayList<Setting>(Arrays.asList(new Setting("Profile Contact Info","change your phone number and/or your email address."),
            new Setting("Font","change font.")
    ));

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.settingsRecyclerView);
        adapter = new SettingsAdapter(getActivity().getBaseContext(), settingList,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        MainActivity.toolbar.setTitle("Settings ");
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

        switch(position){
            case 0:
                ChangeContactInfo dialog = new ChangeContactInfo();
                dialog.show(((MainActivity) getActivity()).getSupportFragmentManager(), "123");
                break;
        }
    }
}

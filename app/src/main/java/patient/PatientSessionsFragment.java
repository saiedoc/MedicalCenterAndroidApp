package patient;
import ViewModels.*;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clinic.MainActivity;
import com.example.clinic.R;
import pojo.Session;

import java.util.ArrayList;
import java.util.List;

import doctor.RecyclerViewClickListener;


public class PatientSessionsFragment extends Fragment implements RecyclerViewClickListener{

    RecyclerView sessionsRecyclerView;
    SessionsViewModel sessionsViewModel;

    List<Session> sessions = new ArrayList<Session>();



    private void initViewModels(){
        int netStatus = ((MainActivity)getActivity()).getNetStatus();
        sessionsViewModel = SessionsViewModel.getINSTANCE();
        sessionsViewModel.setNetStatus(netStatus);
    }

    private void findViews(){
        sessionsRecyclerView = getView().findViewById(R.id.sessionsRecyclerView);
        MainActivity.toolbar.setTitle("Patient Sessions Data");
    }

    private void BuildRecyclerView(){
        PatientSessionsAdapter adapter = new PatientSessionsAdapter(getContext(), sessions, this);
        sessionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        sessionsRecyclerView.setAdapter(adapter);
    }

    private void getData(){
        int role = ((MainActivity)getActivity()).getRoleID();
        sessionsViewModel.getSessions(((MainActivity)getActivity()).getCurrentPatientProfileID(),role,getContext());
    }

    private void observeData(){
        sessionsViewModel.getSessionListMutableLiveData().observe(getActivity(), new Observer<List<Session>>() {
            @Override
            public void onChanged(List<Session> sessions1) {

                sessions = sessions1;
                BuildRecyclerView();
            }
        });
    }

    public PatientSessionsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sessions, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        findViews();
        initViewModels();
        getData();
        observeData();

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        ((MainActivity) getActivity()).setCurrentSessionID(sessions.get(position).getSession_id());
        int role = ((MainActivity) getActivity()).getRoleID();
        switch (role){
            case 2:
                NavHostFragment.findNavController(PatientSessionsFragment.this).navigate(R.id.action_patientSessionsFragment2_to_patientAttachmentsFragment);
                break;
            case 3:
                NavHostFragment.findNavController(PatientSessionsFragment.this).navigate(R.id.action_patientSessionsFragment_to_patientAttachmentsFragment2);
                break;

        }


    }
}
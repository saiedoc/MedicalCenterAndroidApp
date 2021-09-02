package admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.clinic.MainActivity;
import com.example.clinic.R;

import java.util.ArrayList;
import java.util.List;

import ViewModels.DoctorsViewModel;
import doctor.RecyclerViewClickListener;

import pojo.Doctor;


public class DoctorListFragment extends Fragment implements RecyclerViewClickListener {

    private DoctorsViewModel doctorsViewModel;
    RecyclerView recyclerView;
    EditText searchField;
    List<Doctor> doctors = new ArrayList<>();
    List<Doctor> filteredDoctors = new ArrayList<>();
    DoctorListRecyclerAdapter adapter;


    public void filter(String s){

        filteredDoctors.clear();

        if(s.isEmpty()){
            adapter.updateList(doctors);
            return;
        }


        for(Doctor doctor:doctors){
            if(doctor.getName().toLowerCase().contains(s)){
                filteredDoctors.add(doctor);
            }
        }

        adapter.updateList(filteredDoctors);

    }
    public void observeSearchField(){



        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString().toLowerCase());
            }
        });

    }

    private void initViewModels(){

        int netStatus = ((MainActivity)getActivity()).getNetStatus();
        doctorsViewModel = DoctorsViewModel.getINSTANCE();
        doctorsViewModel.setNetStatus(netStatus);
    }

    private void findViews(){
        recyclerView = getView().findViewById(R.id.admin_recyclerView);
        searchField = getView().findViewById(R.id.admin_doctor_search_field);
    }

    private void buildRecyclerView(){
        adapter = new DoctorListRecyclerAdapter(getActivity().getBaseContext(),doctors,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
    }

    private void getData(){
        doctorsViewModel.getDoctors(getContext());
    }

    private void observeData(){
        doctorsViewModel.getDoctorsListViewModel().observe(getActivity(), new Observer<List<Doctor>>() {
            @Override
            public void onChanged(List<Doctor> doctors1) {
                doctors = doctors1;
                if(doctors != null && adapter != null)
                adapter.updateList(doctors);
            }
        });
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.admin_doctor_list, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        initViewModels();
        getData();
        observeData();
        buildRecyclerView();
        observeSearchField();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

       if(filteredDoctors.isEmpty()) {
           System.out.println(doctors.get(position).getDoctor_id()+"  eyad");
           ((MainActivity) getActivity()).setCurrentDoctorProfileId(doctors.get(position).getDoctor_id());
       }else
            ((MainActivity)getActivity()).setCurrentPatientProfileID(filteredDoctors.get(position).getDoctor_id());

        NavHostFragment.findNavController(DoctorListFragment.this).navigate(R.id.action_doctorListFragment_to_doctorProfile);
    }
}
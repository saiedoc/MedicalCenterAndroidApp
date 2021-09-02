package com.example.clinic;
import ViewModels.*;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import data.ClientByteApi;
import okhttp3.ResponseBody;
import pojo.Attachments;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


import doctor.RecyclerViewClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PatientAttachmentsFragment extends Fragment implements RecyclerViewClickListener {

    RecyclerView attachmentsRecyclerView;
    AttachmentsViewModel attachmentsViewModel;
    List<Attachments> attachments = new ArrayList<>();
    DownloadViewModel downloadViewModel;


    private void initViewModels(){

        int netStatus = ((MainActivity)getActivity()).getNetStatus();
        attachmentsViewModel = AttachmentsViewModel.getINSTANCE();
        downloadViewModel = DownloadViewModel.getINSTANCE();
        attachmentsViewModel.setNetStatus(netStatus);

    }

    private void findViews() {
        attachmentsRecyclerView = getView().findViewById(R.id.attachments_recyclerView);
        MainActivity.toolbar.setTitle("Patient Attachments Data");
    }

    private void BuildRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        attachmentsRecyclerView.setLayoutManager(gridLayoutManager);
        PatientAttachmentsAdapter adapter = new PatientAttachmentsAdapter(getContext(), attachments, this);
        attachmentsRecyclerView.setAdapter(adapter);

    }

    private void getData() {
        int role = ((MainActivity)getActivity()).getRoleID();
        attachmentsViewModel.getAttachments(((MainActivity) getActivity()).getCurrentSessionID(),role,getContext());
    }

    private void observeData() {
        attachmentsViewModel.getAttachmentsMutableLiveData().observe(getActivity(), new Observer<List<Attachments>>() {
            @Override
            public void onChanged(List<Attachments> attachments1) {
                attachments = attachments1;
                BuildRecyclerView();
            }
        });
    }


    public PatientAttachmentsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.attachments_list, container, false);
    }

    @Override
    public void onActivityCreated(@NonNull Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModels();
        findViews();
        getData();
        observeData();

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {


        ((MainActivity) getActivity()).setCurrentAttachmentID(attachments.get(position).getAttachment_id());
        ((MainActivity) getActivity()).setCurrentAttachmentName(attachments.get(position).getName());
        int role = ((MainActivity) getActivity()).getRoleID();
        //String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/clinicApp" + "/" + attachments.get(position).getName() +".pdf" ;
        switch(role){
            case 2:
                NavHostFragment.findNavController(PatientAttachmentsFragment.this).navigate(R.id.action_patientAttachmentsFragment_to_pdfViewFragment);
                break;
            case 3:
                NavHostFragment.findNavController(PatientAttachmentsFragment.this).navigate(R.id.action_patientAttachmentsFragment2_to_pdfViewFragment2);
                break;
        }
    }
}
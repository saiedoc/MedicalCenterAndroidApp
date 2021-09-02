package com.example.clinic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import doctor.RecyclerViewClickListener;
import pojo.Attachments;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PatientAttachmentsAdapter extends RecyclerView.Adapter<PatientAttachmentsAdapter.MyViewHolder> {
    List<Attachments> attachments;
    private static RecyclerViewClickListener itemListener;
    Context context;

    public PatientAttachmentsAdapter(Context context, List<Attachments> attachments,RecyclerViewClickListener itemListener) {
        this.context = context;
        PatientAttachmentsAdapter.itemListener = itemListener;
        this.attachments = attachments;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_attachment_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.topic.setText(attachments.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return attachments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView topic;
        TextView des;

        public MyViewHolder(View itemView) {
            super(itemView);
            topic = (TextView) itemView.findViewById(R.id.topic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v,this.getLayoutPosition());
        }
    }
}

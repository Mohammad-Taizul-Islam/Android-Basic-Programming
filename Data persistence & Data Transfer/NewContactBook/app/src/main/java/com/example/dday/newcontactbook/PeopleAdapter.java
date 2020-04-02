package com.example.dday.newcontactbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.MyViewHolder>{

    private Context context;
    private List<People> peopleList;

    public PeopleAdapter(Context context,List<People> peopleList)
    {
        this.context=context;
        this.peopleList=peopleList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        People people=peopleList.get(position);
        holder.textViewId.setText(""+people.getId());
        holder.textViewName.setText(people.getName());
        holder.textViewPhone.setText(people.getPhone());
        holder.textViewEmail.setText(people.getEmail());
    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewName;
        private TextView textViewPhone;
        private TextView textViewEmail;
        private TextView textViewId;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewId=itemView.findViewById(R.id.text_view_id);
            textViewName=itemView.findViewById(R.id.text_view_name);
            textViewEmail=itemView.findViewById(R.id.text_view_email);
            textViewPhone=itemView.findViewById(R.id.text_view_phone);
        }
    }
}

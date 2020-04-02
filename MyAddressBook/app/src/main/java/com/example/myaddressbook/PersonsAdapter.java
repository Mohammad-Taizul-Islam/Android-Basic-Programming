package com.example.myaddressbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonsAdapter extends  RecyclerView.Adapter<PersonsAdapter.MyViewHolder> {


    private Context context;
    private List<Person> personList;
    private OnItemClickListener listener;


    public PersonsAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_person_info, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Person person=personList.get(position);
        holder.name.setText(person.getPerson_name());
        holder.phone_number.setText(person.getPhone_number());
        holder.email.setText(person.getEmail());
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public Person getPersonAt(int position)
    {
        return get
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView phone_number;
        public TextView email;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.text_view_name);
            email = view.findViewById(R.id.text_view_email);
            phone_number = view.findViewById(R.id.text_view_phone_number);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(position));
                    }

                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Person person);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener=listener;
    }
}

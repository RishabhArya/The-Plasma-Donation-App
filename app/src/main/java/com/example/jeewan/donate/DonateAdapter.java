package com.example.jeewan.donate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeewan.R;
import com.example.jeewan.covidUpdates.CovidStateWiseStatsModel;
import com.example.jeewan.covidUpdates.CovidStatewiseAdapter;
import com.example.jeewan.request.RequestModel;

import java.util.List;

public class DonateAdapter extends RecyclerView.Adapter<DonateAdapter.DonViewHolder> {
    private List<RequestModel> requestlist;
    private Context context;

    public DonateAdapter(Context context, List<RequestModel> requestlist) {
        this.context = context;
        this.requestlist = requestlist;
    }

    //to provide layout for each view in recyclerview
    @NonNull
    @Override
    public DonateAdapter.DonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.donate_recyclerview_data, parent, false);
        return new DonateAdapter.DonViewHolder(view);
    }

    //to set all the values for each view in recyclerview
    @Override
    public void onBindViewHolder(@NonNull DonateAdapter.DonViewHolder holder, int position) {
        final RequestModel reqdata = requestlist.get(position);

        holder.req_type.setText(reqdata.getReq_type());
        holder.name.setText(reqdata.getName());
        holder.bloodgroup.setText(reqdata.getBlood_group());
        holder.quantity.setText(reqdata.getAmount());
        holder.hospitalname.setText(reqdata.getHospital_name() + "," + reqdata.getCity());
        holder.description.setText(reqdata.getDescription());
        holder.date.setText(reqdata.getDate());

        //set listener on contact button
        holder.contact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return requestlist.size();
    }

    //viewholder class to provide a holder for views in recyclerview
    public class DonViewHolder extends RecyclerView.ViewHolder {
        private TextView req_type;
        private TextView name;
        private TextView bloodgroup;
        private TextView quantity;
        private TextView hospitalname;
        private TextView description;
        private TextView date;
        private Button contact_button;

        public DonViewHolder(View view) {
            super(view);
            req_type = view.findViewById(R.id.requesttype_textView);
            name = view.findViewById(R.id.patient_name_tvstats);
            bloodgroup = view.findViewById(R.id.blood_group_tvstats);
            quantity = view.findViewById(R.id.blood_quantity_tvstats);
            hospitalname = view.findViewById(R.id.hospitalname_tv);
            description = view.findViewById(R.id.description_tvstats);
            contact_button = view.findViewById(R.id.patient_contact_button);
            date = view.findViewById(R.id.request_date_tv);
        }
    }
}

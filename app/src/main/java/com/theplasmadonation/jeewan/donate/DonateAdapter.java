package com.theplasmadonation.jeewan.donate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theplasmadonation.jeewan.R;
import com.theplasmadonation.jeewan.request.RequestModel;

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
        holder.hospitalname.setText(((reqdata.getHospital_name().substring(0,1).toUpperCase())+reqdata.getHospital_name().substring(1)) + "," +
                (reqdata.getCity().substring(0,1)+(reqdata.getCity().substring(1)).toLowerCase()));
        holder.description.setText(reqdata.getDescription());
        holder.date.setText(reqdata.getDate());

        //set listener on contact button
        holder.contact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+reqdata.getPhone_number()));
                context.startActivity(callIntent);
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
            req_type = view.findViewById(R.id.requestdis_type_tv);
            name = view.findViewById(R.id.requestdis_name_tvstats);
            bloodgroup = view.findViewById(R.id.requestdis_bgroup_tvstats);
            quantity = view.findViewById(R.id.requestdis_bquant_tvstats);
            hospitalname = view.findViewById(R.id.requestdis_hospitalname_tv);
            description = view.findViewById(R.id.requestdis_desc_tvstats);
            contact_button = view.findViewById(R.id.requestdis_close_button);
            date = view.findViewById(R.id.requestdis_date_tv);
        }
    }
}

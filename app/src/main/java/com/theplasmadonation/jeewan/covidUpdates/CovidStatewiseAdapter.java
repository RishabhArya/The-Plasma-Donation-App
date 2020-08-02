package com.theplasmadonation.jeewan.covidUpdates;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theplasmadonation.jeewan.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CovidStatewiseAdapter extends RecyclerView.Adapter<CovidStatewiseAdapter.CovidViewHolder> {
    private List<CovidStateWiseStatsModel> covidstatewiselist;
    private Context context;

    public CovidStatewiseAdapter(Context context,List<CovidStateWiseStatsModel> covidstatewiselist)
    {
        this.context=context;
        this.covidstatewiselist=covidstatewiselist;
    }

    //to provide layout for each view in recyclerview
    @NonNull
    @Override
    public CovidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.covidupdates_recyclerview,parent,false);
        return new CovidStatewiseAdapter.CovidViewHolder(view);
    }

    //to set all the values for each view in recyclerview
    @Override
    public void onBindViewHolder(@NonNull CovidViewHolder holder, int position) {
        final CovidStateWiseStatsModel statewisedata=covidstatewiselist.get(position);

        holder.statename_tv.setText(statewisedata.getState());
        holder.statewise_confirmed_tvstats.setText(statewisedata.getConfirmed());
        holder.statewise_active_tvstats.setText(statewisedata.getActive());
        holder.statewise_recovered_tvstats.setText(statewisedata.getRecovered());
        holder.statewise_deceased_tvstats.setText(statewisedata.getDeaths());
        //Convert lastupdatedtime in suitable format
        Date currentDate = new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date updatedate= null;
        try {
            updatedate = dateFormat.parse(statewisedata.getLastupdatedtime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long sec=TimeUnit.MILLISECONDS.toSeconds(currentDate.getTime()-updatedate.getTime());
        Long min=TimeUnit.MILLISECONDS.toMinutes(currentDate.getTime()-updatedate.getTime());
        Long hr=TimeUnit.MILLISECONDS.toHours(currentDate.getTime()-updatedate.getTime());

        if(sec<60){
            holder.statewise_lastupdated_tvstats.setText("Few seconds ago");
        }
        else if(min<60)
        {
            holder.statewise_lastupdated_tvstats.setText(min+" minutes ago");
        }
        else if (hr<24)
        {
            holder.statewise_lastupdated_tvstats.setText((min/60)+" hours ago");
        }
        else
        {
            holder.statewise_lastupdated_tvstats.setText((DateFormat.format("dd/MM/yyyy",updatedate).toString()));
        }
    }

    @Override
    public int getItemCount() {
        return covidstatewiselist.size();
    }

    //viewholder class to provide a holder for views in recyclerview
    public class CovidViewHolder extends RecyclerView.ViewHolder
    {
        private TextView statename_tv;
        private TextView statewise_active_tvstats;
        private TextView statewise_confirmed_tvstats;
        private TextView statewise_recovered_tvstats;
        private TextView statewise_deceased_tvstats;
        private TextView statewise_lastupdated_tvstats;

        public CovidViewHolder(View view)
        {
            super(view);
            statename_tv=view.findViewById(R.id.statename_tv);
            statewise_confirmed_tvstats=view.findViewById(R.id.statewise_confirmed_tvstats);
            statewise_active_tvstats=view.findViewById(R.id.statewise_active_tvstats);
            statewise_recovered_tvstats=view.findViewById(R.id.statewise_recovered_tvstats);
            statewise_deceased_tvstats=view.findViewById(R.id.statewise_deceased_tvstats);
            statewise_lastupdated_tvstats=view.findViewById(R.id.statewise_lastupdated_tvstats);
        }
    }

}

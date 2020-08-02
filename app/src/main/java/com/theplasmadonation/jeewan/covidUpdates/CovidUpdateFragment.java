package com.theplasmadonation.jeewan.covidUpdates;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.theplasmadonation.jeewan.api.GetCovidStatewiseData;
import com.theplasmadonation.jeewan.api.RetrofitClientInstance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CovidUpdateFragment extends Fragment {
    com.theplasmadonation.jeewan.databinding.FragmentCovidUpdateBinding covidupdateBinding;
    //reference to covidindia recycler view adapter and progress dialog
    private CovidStatewiseAdapter covid_statewise_adapter;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        covidupdateBinding= com.theplasmadonation.jeewan.databinding.FragmentCovidUpdateBinding.inflate(inflater, container, false);

        //display progress dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //create handle for the RetrofitInstance interface
        GetCovidStatewiseData getCovidStatewiseData = RetrofitClientInstance.getRetrofitInstance().create(GetCovidStatewiseData.class);

        //create callback for retrofit response
        getCovidStatewiseData.getStatewiseData().enqueue(new Callback<RootCovidStatewiseStatsModel>() {
            @Override
            public void onResponse(Call<RootCovidStatewiseStatsModel> call, Response<RootCovidStatewiseStatsModel> response) {
                //dismiss progress dialog and call method to display data
                progressDialog.dismiss();
                generateStatewiseData(response.body().statewiselist);
            }

            @Override
            public void onFailure(Call<RootCovidStatewiseStatsModel> call, Throwable t) {
                //dismiss dialog and display error message
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong,Please try later!", Toast.LENGTH_LONG).show();
            }
        });
        return covidupdateBinding.getRoot();
    }

    //method to generate statewise data
    private void generateStatewiseData(List<CovidStateWiseStatsModel> statewiselist)
    {
        //set stats for across india
        covidupdateBinding.indiaActiveTvstats.setText(statewiselist.get(0).getActive());
        covidupdateBinding.indiaConfirmedTvstats.setText(statewiselist.get(0).getConfirmed());
        covidupdateBinding.indiaRecoveredTvstats.setText(statewiselist.get(0).getRecovered());
        covidupdateBinding.indiaDeadTvstats.setText(statewiselist.get(0).getDeaths());

        //Convert lastupdatedtime in suitable format
        Date currentDate = new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date updatedate= null;
        try {
            updatedate = dateFormat.parse(statewiselist.get(0).getLastupdatedtime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long sec= TimeUnit.MILLISECONDS.toSeconds(currentDate.getTime()-updatedate.getTime());
        Long min=TimeUnit.MILLISECONDS.toMinutes(currentDate.getTime()-updatedate.getTime());
        Long hr=TimeUnit.MILLISECONDS.toHours(currentDate.getTime()-updatedate.getTime());

        if(sec<60){
            covidupdateBinding.indiaLastupdatedTvstats.setText("Few seconds ago");
        }
        else if(min<60)
        {
            covidupdateBinding.indiaLastupdatedTvstats.setText(min+" minutes ago");
        }
        else if (hr<24)
        {
            covidupdateBinding.indiaLastupdatedTvstats.setText((min/60)+" hours ago");
        }
        else
        {
            covidupdateBinding.indiaLastupdatedTvstats.setText((DateFormat.format("dd/MM/yyyy",updatedate).toString()));
        }

        //set stats for states
        statewiselist.remove(0);
        covid_statewise_adapter=new CovidStatewiseAdapter(getActivity(),statewiselist);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        covidupdateBinding.covidindiaRview.setLayoutManager(layoutManager);
        covidupdateBinding.covidindiaRview.setAdapter(covid_statewise_adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
package com.example.jeewan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeewan.Model.CovidStateWiseStats;
import com.example.jeewan.Model.RootCovidStatewiseStats;
import com.example.jeewan.Networking.GetCovidStatewiseData;
import com.example.jeewan.Networking.RetrofitClientInstance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CovidUpdates extends AppCompatActivity {

    //reference to covidindia recycler view and adapter
    private RecyclerView covidindia_rview;
    private CovidStatewiseAdapter covid_statewise_adapter;
    ProgressDialog progressDialog;
    //references to all the views related to india covidstats
    TextView india_active_tv;
    TextView india_confirmed_tv;
    TextView india_recovered_tv;
    TextView india_deceased_tv;
    TextView india_lastupdated_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_updates);

        //display progress dialog
        progressDialog = new ProgressDialog(CovidUpdates.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //create handle for the RetrofitInstance interface
        GetCovidStatewiseData getCovidStatewiseData = RetrofitClientInstance.getRetrofitInstance().create(GetCovidStatewiseData.class);

        //create callback for retrofit response
        getCovidStatewiseData.getStatewiseData().enqueue(new Callback<RootCovidStatewiseStats>() {
            @Override
            public void onResponse(Call<RootCovidStatewiseStats> call, Response<RootCovidStatewiseStats> response) {
                //dismiss progress dialog and call method to display data
                progressDialog.dismiss();
                generateStatewiseData(response.body().statewiselist);
            }

            @Override
            public void onFailure(Call<RootCovidStatewiseStats> call, Throwable t) {
                //dismiss dialog and display error message
                progressDialog.dismiss();
                Toast.makeText(CovidUpdates.this,"Something went wrong,Please try later!",Toast.LENGTH_LONG).show();
            }
        });
    }

    //method to generate statewise data
    private void generateStatewiseData(List<CovidStateWiseStats> statewiselist)
    {
        //set stats for across india
        india_active_tv=(TextView)findViewById(R.id.india_active_tvstats);
        india_active_tv.setText(statewiselist.get(0).getActive());
        india_confirmed_tv=(TextView)findViewById(R.id.india_confirmed_tvstats);
        india_confirmed_tv.setText(statewiselist.get(0).getConfirmed());
        india_recovered_tv=(TextView)findViewById(R.id.india_recovered_tvstats);
        india_recovered_tv.setText(statewiselist.get(0).getRecovered());
        india_deceased_tv=(TextView)findViewById(R.id.india_dead_tvstats);
        india_deceased_tv.setText(statewiselist.get(0).getDeaths());
        india_lastupdated_tv=(TextView)findViewById(R.id.india_lastupdated_tvstats);
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
            india_lastupdated_tv.setText("Few seconds ago");
        }
        else if(min<60)
        {
            india_lastupdated_tv.setText(min+" minutes ago");
        }
        else if (hr<24)
        {
            india_lastupdated_tv.setText((min/60)+" hours ago");
        }
        else
        {
            india_lastupdated_tv.setText((DateFormat.format("dd/MM/yyyy",updatedate).toString()));
        }

        //set stats for states
        covidindia_rview=(RecyclerView)findViewById(R.id.covidindia_rview);
        statewiselist.remove(0);
        covid_statewise_adapter=new CovidStatewiseAdapter(this,statewiselist);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(CovidUpdates.this);
        covidindia_rview.setLayoutManager(layoutManager);
        covidindia_rview.setAdapter(covid_statewise_adapter);
    }
}

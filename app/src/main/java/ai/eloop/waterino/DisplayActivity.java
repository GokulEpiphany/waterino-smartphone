package ai.eloop.waterino;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import ai.eloop.waterino.interfaces.DepthInterface;
import ai.eloop.waterino.model.Depth;
import eo.view.batterymeter.BatteryMeterView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DisplayActivity extends AppCompatActivity {

    TextView batteryStatus;
    TextView distance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        final BatteryMeterView batteryMeterView = (BatteryMeterView)findViewById(R.id.batteryView);
        batteryMeterView.setCriticalChargeLevel(50);
        batteryMeterView.setCharging(false);
        batteryStatus = (TextView)findViewById(R.id.batteryStatus);
        distance = (TextView)findViewById(R.id.distance);
        Intent intent = getIntent();
        final String depthGiven = intent.getStringExtra("Depth");
        final float depthMax = Float.parseFloat(depthGiven);
        Log.d("In Waterino","Given max depth is "+depthMax);


        final Retrofit retrofit = ApiManager.getAdapter();

        final boolean shouldStopLoop = false;
        final Handler mHandler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("TESTING TWO","CALLING THIS THREAD YEAH BITCHESS");
                if (!shouldStopLoop) {
                    DepthInterface apiInterface = retrofit.create(DepthInterface.class);
                    Call<Depth> call = apiInterface.getDistance();
                    call.enqueue(new Callback<Depth>() {
                        @Override
                        public void onResponse(Call<Depth> call, Response<Depth> response) {
                            Depth responseDepth = response.body();

                            Log.d("Inside Waterino",responseDepth.getDistance()+"Is the distance");
                            int distanceGot = (int)(response.body().getDistance());

                            int percent = (int)(((58.0 - distanceGot))*(100.0/23.0));
                            batteryMeterView.setChargeLevel(percent);
                            Log.d("In waterino","Meter set is "+percent+" : "+depthMax+" : "+distanceGot+" : "+(depthMax-distanceGot));

                            if(distanceGot>35 && distanceGot<58){
                                batteryStatus.setText("ON");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    batteryStatus.setTextColor(getApplicationContext().getColor(R.color.colorAccent));
                                }
                            }else{
                                batteryStatus.setText("OFF");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    batteryStatus.setTextColor(getApplicationContext().getColor(R.color.colorPrimary));
                                }

                            }

                            distance.setText(responseDepth.getDistance()+" cm");
                        }

                        @Override
                        public void onFailure(Call<Depth> call, Throwable t) {

                        }
                    });

                    mHandler.postDelayed(this, 2000);
                }
            }
        };

        runOnUiThread(runnable);
    }
}

package ai.eloop.waterino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import ai.eloop.waterino.interfaces.DepthInterface;
import ai.eloop.waterino.model.Depth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    EditText getDistance;
    Button proceedButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDistance = (EditText)findViewById(R.id.getInitialDepth);
        proceedButton = (Button)findViewById(R.id.proceedButton);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String depthGiven = getDistance.getText().toString();
                Intent intent = new Intent(MainActivity.this,DisplayActivity.class);
                intent.putExtra("Depth",depthGiven);
                startActivity(intent);
            }
        });


    }
}

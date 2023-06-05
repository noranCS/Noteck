package TopicsActivities;

import static TopicsActivities.PrimePage.userName;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.noteckv1.R;

public class BiologyActivity extends AppCompatActivity {
    private TextView nameTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biology);

        nameTV = findViewById(R.id.textNameBiologyAc);
        userName = getIntent().getStringExtra("userName");
        nameTV.setText(userName);
    }
}
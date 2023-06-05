package TopicsActivities;

import static TopicsActivities.PrimePage.userName;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.noteckv1.R;


public class ChemistryActivity extends Activity {
    private TextView nameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemistry);

        nameTV = findViewById(R.id.textNameChemistryAc);
        userName = getIntent().getStringExtra("userName");
        nameTV.setText(userName);
    }
}
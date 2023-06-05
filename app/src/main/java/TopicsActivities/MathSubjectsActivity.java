package TopicsActivities;

import static TopicsActivities.PrimePage.userName;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteckv1.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class MathSubjectsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private DatabaseReference databaseReference;
    private List<Upload> uploadsList;

    private TextView nameTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_subjects);

        nameTV = findViewById(R.id.textNameMathAc);
        userName = getIntent().getStringExtra("userName");
        nameTV.setText(userName);

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        uploadsList = new ArrayList<Upload>();
//        databaseReference = FirebaseDatabase.getInstance().getReference("Uploads");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for( DataSnapshot postSnapshot : snapshot.getChildren() ){//for each loop
//                    Upload upload = postSnapshot.getValue(Upload.class);
//                    uploadsList.add(upload);
//                }
//                imageAdapter = new ImageAdapter(MathSubjectsActivity.this,uploadsList);
//                recyclerView.setAdapter(imageAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MathSubjectsActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        });


    }
}
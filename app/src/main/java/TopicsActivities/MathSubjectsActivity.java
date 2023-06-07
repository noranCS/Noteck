package TopicsActivities;

import static TopicsActivities.PrimePage.userName;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteckv1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class MathSubjectsActivity extends AppCompatActivity {
    public static final int PICK_IMAGE_REQUEST = 878;
    private ListView listView;
    private FloatingActionButton uploadBtn;
    private ListViewAdapter listViewAdapter;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private List<Upload> uploadsList;

    private TextView textTV;

    private Uri imageUri;
    private StorageTask uploadTask;
    private ProgressBar uploadProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_subjects);
        listView = findViewById(R.id.mathListView);
        textTV = findViewById(R.id.textMathAc);
        userName = getIntent().getStringExtra("userName");
        textTV.setText(userName);

//        recyclerView = findViewById(R.id.recycleView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uploadProgressBar = findViewById(R.id.upload_progress_bar);
        uploadBtn = findViewById(R.id.upload_file_pic);

        uploadsList = new ArrayList<Upload>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Uploads");
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");
        ListViewAdapter listViewAdapter = new ListViewAdapter(MathSubjectsActivity.this,R.layout.row_item,uploadsList);
        listView.setAdapter(listViewAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadsList.clear();
                for( DataSnapshot postSnapshot : snapshot.getChildren() ){//for each loop
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploadsList.add(upload);
                }
                listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MathSubjectsActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });
    }

    public String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadImage() {
        if( imageUri != null ){
            String imageName = System.currentTimeMillis()+"."+ getFileExtension(imageUri);
            StorageReference fileReference = storageReference.child(imageName);
            uploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postAtTime(new Runnable() {
                                @Override
                                public void run() {
                                   uploadProgressBar.setProgress(0);
                                   uploadProgressBar.setVisibility(View.VISIBLE);
                                }
                            },2000);
                            Toast.makeText(MathSubjectsActivity.this,"Upload Successful",Toast.LENGTH_LONG).show();
                            Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                            Log.d("AAAAAA",taskSnapshot.getStorage().getName());
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String uploadId = databaseReference.push().getKey();
                                    Upload upload = new Upload(textTV.getText().toString().trim(), uri.toString(),imageName,uploadId);
                                    databaseReference.child(uploadId).setValue(upload);
                                    uploadProgressBar.setVisibility(View.INVISIBLE);
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MathSubjectsActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = 100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount();
                            uploadProgressBar.setProgress((int)progress);
                        }
                    });

        }else{
            Toast.makeText(this,"No File selected",Toast.LENGTH_SHORT).show();
        }

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() !=null ){
            imageUri = data.getData();
            uploadImage();
           // image.setImageURI( imageUri);
        }
    }
}
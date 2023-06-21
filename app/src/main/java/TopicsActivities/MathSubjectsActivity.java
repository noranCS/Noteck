package TopicsActivities;

import static TopicsActivities.PrimePage.userName;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class MathSubjectsActivity extends AppCompatActivity {
    public static final int PICK_IMAGE_REQUEST = 878;
    private ListView listView;
    private FloatingActionButton addFileToUpload; // open a new window to choose file to upload
    private ListViewAdapter algebralistViewAdapter, geoListViewAdapter, trioListViewAdapter, statisticsListViewAdapter; // to set  list view a specific subject's LVA
    private List<Upload> algebraList, geometryList, trigonometryList, statisticsList;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private TextView textTVURName;

    private Uri imageUri;
    private StorageTask uploadTask;
    private ProgressBar uploadProgressBar;
    private String subject; // the chosen category

    //image view icons     math subjects
    private ImageView statisticsIcon, trigonometryIcon, algebraIcon, geometryIcon,    //image view icons     math subjects
                    statisticsIconShadow, trigonometryIconShadow, algebraIconShadow,geometryIconShadow;   //image view icons     math subject's shadow "display when icon is clicked"
    private FrameLayout geometryFrame, algebraFrame, trioFrame, statisticsFrame;  // adjust each frameLayout margin when clicked using params
    private ViewGroup.MarginLayoutParams algebraParams, geoParams, statisticsParams, trioParams; // < -- this params.
    private int preClickedIconId = 1 ; // ID for the previous clicked icon __  at the begging  clicked icon is algebraIcon
                                                // IDs : 1 = algebra , 2 = geometry , 3 = trigonometry , 4 = statistics
    //linear list -- attributes
    private LinearLayout imageLinearContainer;
    private TextView imageInfoTV, topTextForSubjects; // imageInfoTV _ the name of the image you've selected
    private ImageView imageV; // the image that you want to upload
    private Button uploadBtnToList; // close "select a file window" --> get back to list/subjects page
    private RelativeLayout mainRelativeContainer;

    private BlurView blurView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_subjects);

        // finds the View by the ID it is given in activity_math_subjects XML
        listView = findViewById(R.id.mathListView);
        textTVURName = findViewById(R.id.textNameMathAc);
            userName = getIntent().getStringExtra("userName");//display your account name topLeft screen
             textTVURName.setText(userName);

             //uploading window
        imageLinearContainer = findViewById(R.id.addContainer);
        imageInfoTV = findViewById(R.id.imageInfoTV);
        imageV = findViewById(R.id.imageV);
        uploadBtnToList = findViewById(R.id.uploadBtnToList);
        mainRelativeContainer = findViewById(R.id.mainMathRelativeLay);
        
        blurView = findViewById(R.id.blurLayout);

        uploadProgressBar = findViewById(R.id.upload_progress_bar);
        addFileToUpload = findViewById(R.id.upload_file_pic);

        topTextForSubjects = findViewById(R.id.topTVSubjects);

        //math subjects icons and attributes
        algebraIcon = findViewById(R.id.algebra_icon);
        algebraIconShadow = findViewById(R.id.algebra_icon_shadow);
        geometryIcon = findViewById(R.id.geometry_icon);
        geometryIconShadow = findViewById(R.id.geometry_icon_shadow);
        trigonometryIcon = findViewById(R.id.trigonometry_icon);
        trigonometryIconShadow = findViewById(R.id.trigonometry_icon_shadow);
        statisticsIcon = findViewById(R.id.statistics_icon);
        statisticsIconShadow = findViewById(R.id.statistics_icon_shadow);

        geometryFrame = findViewById(R.id.geometryFrameLay);
        algebraFrame = findViewById(R.id.algebraFrameLay);
        trioFrame = findViewById(R.id.trigonometryFrameLay);
        statisticsFrame = findViewById(R.id.statisticsFrameLay);
                        //to change frameLayout margin we need params to adjust it !
        geoParams = (ViewGroup.MarginLayoutParams) geometryFrame.getLayoutParams();
        algebraParams = (ViewGroup.MarginLayoutParams) algebraFrame.getLayoutParams();
        trioParams = (ViewGroup.MarginLayoutParams) trioFrame.getLayoutParams();
        statisticsParams = (ViewGroup.MarginLayoutParams) statisticsFrame.getLayoutParams();

        // math category that has been chosen
        subject ="Algebra";

        //add button
        algebraList = new ArrayList<Upload>();
        geometryList = new ArrayList<Upload>();
        trigonometryList = new ArrayList<Upload>();
        statisticsList = new ArrayList<Upload>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Uploads" );
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");

        algebralistViewAdapter = new ListViewAdapter(MathSubjectsActivity.this,R.layout.row_item,algebraList);
        listView.setAdapter(algebralistViewAdapter); //at the beginning

        geoListViewAdapter = new ListViewAdapter(MathSubjectsActivity.this,R.layout.row_item,geometryList);
        trioListViewAdapter = new ListViewAdapter(MathSubjectsActivity.this,R.layout.row_item,trigonometryList);
        statisticsListViewAdapter = new ListViewAdapter(MathSubjectsActivity.this,R.layout.row_item,statisticsList);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(subject.equals("algebra"))
                    addToListBYSubFromStorage(snapshot,algebraList,algebralistViewAdapter);
                else if(subject.equals("geometry"))
                    addToListBYSubFromStorage(snapshot,geometryList,geoListViewAdapter);
                else if(subject.equals("statistics"))
                    addToListBYSubFromStorage(snapshot,statisticsList,statisticsListViewAdapter);
                else if(subject.equals("trigonometry"))
                    addToListBYSubFromStorage(snapshot,trigonometryList,trioListViewAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MathSubjectsActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


        addFileToUpload.setOnClickListener(new View.OnClickListener() { // display the adding window
            @Override
            public void onClick(View v) {
                openFileChooser();
                imageLinearContainer.setVisibility(View.VISIBLE);
                blurBackground();// outside method
            }
        });

        uploadBtnToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();// sent to fireStorage  by using a public method
                blurView.setVisibility(View.GONE);
            }
        });

        // on click ImageView " math subject "  -->  display list that contain -subject- images only !
        algebraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preClickedIconId != 1){
                    changePreviousIconShadow(preClickedIconId); // the last clicked before this icon
                    algebraParams.setMargins(13,13,13,13);
                    algebraFrame.setLayoutParams(algebraParams);
                    algebraIconShadow.setVisibility(View.VISIBLE);
                    topTextForSubjects.setText("Math Category_Algebra");
                    subject = "algebra";
                    listView.setAdapter(algebralistViewAdapter);
                    preClickedIconId = 1;
                }
            }
        });

        geometryIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preClickedIconId != 2){
                    changePreviousIconShadow(preClickedIconId); // the last clicked before this icon
                    geoParams.setMargins(13,13,13,13);
                    geometryFrame.setLayoutParams(geoParams);
                    geometryIconShadow.setVisibility(View.VISIBLE);
                    topTextForSubjects.setText("Math Category_Geometry");
                    subject = "geometry";
                    listView.setAdapter(geoListViewAdapter);
                    preClickedIconId = 2;
                }
            }
        });
        trigonometryIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preClickedIconId != 3){
                    changePreviousIconShadow(preClickedIconId); // the last clicked before this icon
                    trioParams.setMargins(13,13,13,13);
                    trioFrame.setLayoutParams(trioParams);
                    trigonometryIconShadow.setVisibility(View.VISIBLE);
                    topTextForSubjects.setText("Math Category_Trigonometry");
                    subject = "trigonometry";
                    listView.setAdapter(trioListViewAdapter);
                    preClickedIconId = 3;
                }
            }
        });
        statisticsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preClickedIconId != 4){
                    changePreviousIconShadow(preClickedIconId); // the last clicked before this icon
                    statisticsParams.setMargins(13,13,13,13);
                    statisticsFrame.setLayoutParams(statisticsParams);
                    statisticsIconShadow.setVisibility(View.VISIBLE);
                    topTextForSubjects.setText("Math Category_Statistics");
                    subject = "statistics";
                    listView.setAdapter(statisticsListViewAdapter);
                    preClickedIconId = 4;
                }
            }
        });

    }

    //      O U T S I D E      M E T H O D S      //

    private void blurBackground() {
        float radius = 10f; // 0 < radius < 25
        View decorV = getWindow().getDecorView();
        ViewGroup rootV = (ViewGroup) decorV.findViewById(android.R.id.content);
        Drawable windowBG = decorV.getBackground();

        blurView.setupWith(rootV)
                .setFrameClearDrawable(windowBG)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setHasFixedTransformationMatrix(true);
    }
    private void changePreviousIconShadow(int preClickedIconId) {
        if( preClickedIconId == 1){
            algebraIconShadow.setVisibility(View.GONE);
            algebraParams.setMargins(9,9,9,9);
            algebraFrame.setLayoutParams(algebraParams);
        }
        else if( preClickedIconId == 2 ) {
            geometryIconShadow.setVisibility(View.GONE);
            geoParams.setMargins(9,9,9,9);
            geometryFrame.setLayoutParams(geoParams);
        }
        else if ( preClickedIconId == 3 ) {
            trigonometryIconShadow.setVisibility(View.GONE);
            trioParams.setMargins(9,9,9,9);
            trioFrame.setLayoutParams(geoParams);
        }
        else if ( preClickedIconId == 4 ) {
            statisticsIconShadow.setVisibility(View.GONE);
            statisticsParams.setMargins(9,9,9,9);
            statisticsFrame.setLayoutParams(geoParams);
        }
    }

    //           upload to list by subject from firebase storage
    private void addToListBYSubFromStorage(DataSnapshot snapshot, List<Upload> subjectList, ListViewAdapter subjectLVA){
            subjectList.clear();
            for( DataSnapshot postSnapshot : snapshot.child(subject).getChildren() ) {//for each loop
                Upload upload = postSnapshot.getValue(Upload.class);
                subjectList.add(upload);
            }
            subjectLVA.notifyDataSetChanged();
    }
    public String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadImage() {
        if( imageUri != null ){
            String imageName = System.currentTimeMillis()+"."+ getFileExtension(imageUri);
            StorageReference fileReference = storageReference.child(subject).child(imageName); // image name is the child of uploads
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
                            Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();//get url(connection with firebase) to use for downloads //
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String uploadId = databaseReference.push().getKey();
                                    Upload upload = new Upload(imageInfoTV.getText().toString().trim(), uri.toString(),imageName,uploadId,subject);
                                    //id image for deleting
                                    databaseReference.child(subject).child(uploadId).setValue(upload);
                                    uploadProgressBar.setVisibility(View.INVISIBLE);
                                    imageLinearContainer.setVisibility(View.GONE);
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
        intent.setType("image/*");//or downloads... from system
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//result -- image    after the method openFileChooser
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() !=null ){
            imageUri = data.getData();
            imageV.setImageURI(imageUri);//display image in linear image container

//            uploadImage();
           // image.setImageURI( imageUri);
        }
    }
}
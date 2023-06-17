package TopicsActivities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.noteckv1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Upload> {
    private Context context;
    private int resource;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;


    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<Upload> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if( convertView == null )
            convertView = inflater.inflate(resource,parent,false);
        ImageView imageView = convertView.findViewById(R.id.row_item_img);
        TextView textView = convertView.findViewById(R.id.row_item_tv);
        ImageView imageViewDel = convertView.findViewById(R.id.row_item_del);

        Upload upload = getItem(position);
        setImageViewBimap(upload.getImageUrl() , imageView);
        textView.setText(upload.getText());
        imageViewDel.setTag(upload);//tag : saves list info when clicked to use it for removing images
        //view -- contain tag as editor

        databaseReference = FirebaseDatabase.getInstance().getReference("Uploads");
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");
        imageViewDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromDataBase((Upload) v.getTag());
            }
        });

        return convertView;

    }

    private void deleteFromDataBase(Upload upload) {
        //              I M P O R T A N T     F O R     O R G A N I Z I N G     F I R E B A S E
        //                   chile( subject )  organize firebase as selected categories
        storageReference.child(upload.getSubject()).child(upload.getImageName()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                databaseReference.child(upload.getSubject()).child(upload.getId()).removeValue();
                remove(upload);
                notifyDataSetChanged();
            }
        });
    }

    private void setImageViewBimap(String url,ImageView imgV){
//        Log.d("test",url);
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        final long ONE_MEGABYTE = 1024 * 1024 * 5;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imgV.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        // Bitmap  bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
    }


}

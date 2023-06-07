package TopicsActivities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.noteckv1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Upload> {
    private Context context;
    private int resource;

    private StorageReference storageReference;

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
        Button btnDel = convertView.findViewById(R.id.row_item_del);

        Upload upload = getItem(position);
        setImageViewBimap(upload.getImageUrl() , imageView);
        textView.setText(upload.getText());
        btnDel.setTag(upload.getImageUrl());
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromDataBase(v.getTag().toString());
            }
        });

        return convertView;

    }

    private void deleteFromDataBase(String url) {
        Log.d("ListViewAdapter",url);
    }

    private void setImageViewBimap(String url,ImageView imgV){
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        final long ONE_MEGABYTE = 1024 * 1024;
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

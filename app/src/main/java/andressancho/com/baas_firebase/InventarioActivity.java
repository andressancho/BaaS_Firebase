package andressancho.com.baas_firebase;

import android.content.ContentResolver;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class InventarioActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    Button choose;
    Button subir;
    Button inv;
    ImageView foto;
    EditText nombre;
    EditText precio;
    EditText des;

    Uri uri;

    private StorageReference stRef;
    private DatabaseReference dRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        choose=findViewById(R.id.archivo);
        subir = findViewById(R.id.agregar);
        inv=findViewById(R.id.verInv);
        foto = findViewById(R.id.imagen);
        nombre = findViewById(R.id.nombre);
        precio = findViewById(R.id.precio);
        des=findViewById(R.id.descripcion);

        stRef= FirebaseStorage.getInstance().getReference("productos");
        dRef= FirebaseDatabase.getInstance().getReference("productos");

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subir();
            }
        });

        inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),verInventarioActivity.class);
                startActivity(intent);
            }
        });


    }
    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null) {
            uri=data.getData();
            Picasso.with(this).load(uri).into(foto);
        }

    }

    private String getFileExtension(Uri u) {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(u));
    }

    private void subir() {
        if(uri!=null){
            StorageReference fileReference = stRef.child(System.currentTimeMillis() + "." + getFileExtension(uri));
            fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    String n =nombre.getText().toString();
                    String pr= precio.getText().toString();
                    String d=des.getText().toString();

                    Producto p= new Producto(n,pr,d,taskSnapshot.getDownloadUrl().toString());

                    String pid= dRef.push().getKey();
                    dRef.child(pid).setValue(p);

                    foto.setImageResource(R.mipmap.ic_launcher_round);
                    nombre.setText("");
                    precio.setText("");
                    des.setText("");
                }
            });
        }
        else{

        }

    }

}

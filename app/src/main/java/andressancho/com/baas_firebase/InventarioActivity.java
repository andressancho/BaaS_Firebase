package andressancho.com.baas_firebase;

import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class InventarioActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    Button choose;
    Button subir;
    ImageView foto;
    EditText nombre;
    EditText precio;
    EditText des;

    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        choose=findViewById(R.id.archivo);
        subir = findViewById(R.id.agregar);
        foto = findViewById(R.id.imagen);
        nombre = findViewById(R.id.nombre);
        precio = findViewById(R.id.precio);
        des=findViewById(R.id.descripcion);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
}

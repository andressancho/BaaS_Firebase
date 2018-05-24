package andressancho.com.baas_firebase;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class verInventarioActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Producto> productos;
    ArrayList<String> keyList = new ArrayList<>();
    ProductoAdapter adapter;
    DatabaseReference dreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_inventario);

        lv=findViewById(R.id.lv);
        productos= new ArrayList<>();
        dreference= FirebaseDatabase.getInstance().getReference("productos");

        dreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Producto p = postSnapshot.getValue(Producto.class);
                    productos.add(p);
                    keyList.add(postSnapshot.getKey());
                }

                adapter= new ProductoAdapter(verInventarioActivity.this, R.layout.listview_item,productos);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                new AlertDialog.Builder(verInventarioActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Advertencia?")
                        .setMessage("Está seguro de eliminar el producto?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                productos.remove(position);
                                productos.clear();
                                Toast.makeText(verInventarioActivity.this, "Producto eliminado", Toast.LENGTH_LONG).show();
                                adapter.notifyDataSetChanged();
                                dreference.getRoot().child("productos").child(keyList.get(position)).removeValue();
                                keyList.remove(position);
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();


                return true;
            }
        });


    }
}

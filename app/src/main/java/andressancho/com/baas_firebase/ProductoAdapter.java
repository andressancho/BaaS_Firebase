package andressancho.com.baas_firebase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Usuario on 24/05/2018.
 */

public class ProductoAdapter extends ArrayAdapter<Producto>{

    Context context;
    int layoutResourceId;
    ArrayList<Producto> data=null;

    public ProductoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Producto> objects) {
        super(context, resource, objects);
        this.context=context;
        layoutResourceId=resource;
        data=objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View row=convertView;
        ProductosHolder holder=null;
        if(row==null){
            LayoutInflater inflater=((Activity)context).getLayoutInflater();
            row=inflater.inflate(layoutResourceId,parent,false);
            holder= new ProductosHolder();
            holder.image=row.findViewById(R.id.image);
            holder.nom=row.findViewById(R.id.nom);
            holder.price=row.findViewById(R.id.price);
            holder.desc=row.findViewById(R.id.desc);

            row.setTag(holder);
        }else{
            holder=(ProductosHolder)row.getTag();
        }
        Producto p=data.get(position);

        Picasso.with(getContext()).load(p.getImgURL()).fit().centerCrop().into(holder.image);
        holder.nom.setText(p.getName());
        holder.price.setText(p.getPrecio());
        holder.desc.setText(p.getDescripcion());

        return row;

    }
    static class ProductosHolder{
        ImageView image;
        TextView nom;
        TextView price;
        TextView desc;
    }

}

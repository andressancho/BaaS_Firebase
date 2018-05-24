package andressancho.com.baas_firebase;

/**
 * Created by Usuario on 24/05/2018.
 */

public class Producto {

    private String name;
    private String precio;
    private String descripcion;
    private String imgURL;

    public Producto(String name, String precio, String descripcion, String imgURL) {
        this.name = name;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imgURL = imgURL;
    }

    public Producto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}

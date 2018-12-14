package cifprodolfoucha.com.listapp.Loxica;

import java.io.Serializable;

public class Loxica_Articulo implements Serializable {
    private int id;
    private String nombre;
    private boolean seleccionado;
    private int cantidad;
    private double precio;
    private String notas;
    private String rutaImagen="";
    private boolean marcado=false;


    public Loxica_Articulo() {

    }

    public Loxica_Articulo(int id, String nombre, boolean seleccionado, int cantidad, double precio, String notas, String rutaImagen) {
        this.id=id;
        this.nombre = nombre;
        this.seleccionado = seleccionado;
        this.cantidad = cantidad;
        this.precio = precio;
        this.notas = notas;
        this.rutaImagen= rutaImagen;
        this.marcado=false;
    }


    /**
     * Comproba se o Artículo ten imaxe.
     * @return true se o Artículo ten unha imaxe.
     **/
    public boolean riExiste(){
        return rutaImagen.equals("");
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

}

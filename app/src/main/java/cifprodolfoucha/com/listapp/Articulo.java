package cifprodolfoucha.com.listapp;

import java.io.Serializable;

public class Articulo  implements Serializable {
    private String nombre;
    private boolean seleccionado;
    private int cantidad;
    private double precio;
    private String notas;


    public Articulo() {

    }



    public Articulo(String nombre, boolean seleccionado, int cantidad, double precio, String notas) {
        this.nombre = nombre;
        this.seleccionado = seleccionado;
        this.cantidad = cantidad;
        this.precio = precio;
        this.notas = notas;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

        /*
    public Articulo(String nombre) {
        this.nombre = nombre;
        this.seleccionado=false;
    }

    public Articulo(String nombre, boolean seleccionado) {
        this.nombre = nombre;
        this.seleccionado = seleccionado;
    }

    public Articulo(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.seleccionado=false;
    }

    public Articulo(String nombre, boolean seleccionado, int cantidad) {
        this.nombre = nombre;
        this.seleccionado = seleccionado;
        this.cantidad = cantidad;
    }

    public Articulo(String nombre, int cantidad, String notas) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.notas = notas;
        this.seleccionado=false;
    }

    public Articulo(String nombre, boolean seleccionado, int cantidad, String notas) {
        this.nombre = nombre;
        this.seleccionado = seleccionado;
        this.cantidad = cantidad;
        this.notas = notas;
    }

    public Articulo(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
        this.seleccionado=false;
    }

    public Articulo(String nombre, boolean seleccionado, double precio) {
        this.nombre = nombre;
        this.seleccionado = seleccionado;
        this.precio = precio;
    }

    public Articulo(String nombre, int cantidad, double precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.seleccionado=false;
    }

    public Articulo(String nombre, boolean seleccionado, int cantidad, double precio) {
        this.nombre = nombre;
        this.seleccionado = seleccionado;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Articulo(String nombre, int cantidad, double precio, String notas) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.notas = notas;
        this.seleccionado=false;
    }

    public Articulo(String nombre, String notas) {
        this.nombre = nombre;
        this.notas = notas;
        this.seleccionado=false;
    }

    public Articulo(String nombre, boolean seleccionado, String notas) {
        this.nombre = nombre;
        this.seleccionado = seleccionado;
        this.notas = notas;
    }

    public Articulo(String nombre, double precio, String notas) {
        this.nombre = nombre;
        this.precio = precio;
        this.notas = notas;
        this.seleccionado=false;
    }

    public Articulo(String nombre, boolean seleccionado, double precio, String notas) {
        this.nombre = nombre;
        this.seleccionado = seleccionado;
        this.precio = precio;
        this.notas = notas;
    }
    */
}

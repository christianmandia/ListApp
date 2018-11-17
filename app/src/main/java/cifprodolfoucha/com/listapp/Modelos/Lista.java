package cifprodolfoucha.com.listapp.Modelos;

import java.util.ArrayList;

public class Lista {
    private String nombre;
    private Class Categoria;
    private float precio;
    private ArrayList<Articulo> articulos;

    public Lista() {
    }

    public Lista(String nombre, Class categoria, float precio, ArrayList<Articulo> articulos) {
        this.nombre = nombre;
        Categoria = categoria;
        this.precio = precio;
        this.articulos = articulos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Class getCategoria() {
        return Categoria;
    }

    public void setCategoria(Class categoria) {
        Categoria = categoria;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public ArrayList<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(ArrayList<Articulo> articulos) {
        this.articulos = articulos;
    }
}

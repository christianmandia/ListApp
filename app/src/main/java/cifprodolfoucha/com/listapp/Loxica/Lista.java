package cifprodolfoucha.com.listapp.Loxica;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

public class Lista implements Serializable {
    private int id;
    private String nombre;
    private Categoria Categoria;
    private float precio;
    private ArrayList<Articulo> articulos;


    public Lista() {
    }
/*
    public Lista(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
*/
/*
    public Lista(int id, String nombre, Categoria categoria) {
        this.id=id;
        this.nombre = nombre;
        Categoria = categoria;
        this.articulos=new ArrayList();
    }
*/
    public Lista(int id, String nombre, Categoria categoria, ArrayList<Articulo> articulos) {
        this.id=id;
        this.nombre = nombre;
        this.Categoria = categoria;
        this.articulos = articulos;
        for(int i=0;i<articulos.size();i++){
            this.precio+=articulos.get(i).getPrecio();
        }

    }
    public float precioChecked(){
        float precioChecked=0;
        if(articulos!=null) {
            for (int i = 0; i < articulos.size(); i++) {
                precioChecked += articulos.get(i).getPrecio();
            }
        }
        return precioChecked;
    }

    public String diferenciaArticulos(){
        int total=0;
        int checked=0;

        if(articulos!=null) {
            for (int i = 0; i < articulos.size(); i++) {
                total++;
                if (articulos.get(i).isSeleccionado()) {
                    checked++;
                }
            }
        }
        if(total==0)return "-";
        return checked+"/"+total;

    }

    public boolean TienesTodo(){
        int total=0;
        int checked=0;

        Log.i("prueba", "Entra");

        if(articulos!=null) {
            for (int i = 0; i < articulos.size(); i++) {
                total++;

                //Log.i("prueba", total+"");

                //Log.i("prueba", articulos.get(i).isSeleccionado()+"");
                if (articulos.get(i).isSeleccionado()) {

                    checked++;
                }
            }
        }
        return (checked-total==0);

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return Categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.Categoria = categoria;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

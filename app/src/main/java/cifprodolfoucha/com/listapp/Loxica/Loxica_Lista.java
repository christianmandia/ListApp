package cifprodolfoucha.com.listapp.Loxica;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Loxica_Lista implements Serializable {
    private int id;
    private String nombre;
    private Loxica_Categoria Loxica_Categoria;
    private float precio;
    private ArrayList<Loxica_Articulo> loxicaArticulos;


    public Loxica_Lista() {
    }
/*
    public Loxica_Lista(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
*/
/*
    public Loxica_Lista(int id, String nombre, Loxica_Categoria loxicaCategoria) {
        this.id=id;
        this.nombre = nombre;
        Loxica_Categoria = loxicaCategoria;
        this.loxicaArticulos=new ArrayList();
    }
*/
    public Loxica_Lista(int id, String nombre, Loxica_Categoria loxicaCategoria, ArrayList<Loxica_Articulo> loxicaArticulos) {
        this.id=id;
        this.nombre = nombre;
        this.Loxica_Categoria = loxicaCategoria;
        this.loxicaArticulos = loxicaArticulos;
        for(int i = 0; i< loxicaArticulos.size(); i++){
            this.precio+= loxicaArticulos.get(i).getPrecio();
        }

    }
    public float precioChecked(){
        float precioChecked=0;
        if(loxicaArticulos !=null) {
            for (int i = 0; i < loxicaArticulos.size(); i++) {
                precioChecked += loxicaArticulos.get(i).getPrecio();
            }
        }
        return precioChecked;
    }

    public String diferenciaArticulos(){
        int total=0;
        int checked=0;

        if(loxicaArticulos !=null) {
            for (int i = 0; i < loxicaArticulos.size(); i++) {
                total++;
                if (loxicaArticulos.get(i).isSeleccionado()) {
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

        if(loxicaArticulos !=null) {
            for (int i = 0; i < loxicaArticulos.size(); i++) {
                total++;

                //Log.i("prueba", total+"");

                //Log.i("prueba", loxicaArticulos.get(i).isSeleccionado()+"");
                if (loxicaArticulos.get(i).isSeleccionado()) {

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

    public Loxica_Categoria getLoxica_Categoria() {
        return Loxica_Categoria;
    }

    public void setLoxica_Categoria(Loxica_Categoria loxica_Categoria) {
        this.Loxica_Categoria = loxica_Categoria;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public ArrayList<Loxica_Articulo> getLoxicaArticulos() {
        return loxicaArticulos;
    }

    public void setLoxicaArticulos(ArrayList<Loxica_Articulo> loxicaArticulos) {
        this.loxicaArticulos = loxicaArticulos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

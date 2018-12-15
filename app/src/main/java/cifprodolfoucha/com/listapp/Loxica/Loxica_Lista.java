package cifprodolfoucha.com.listapp.Loxica;


import java.io.Serializable;
import java.util.ArrayList;

public class Loxica_Lista implements Serializable {
    private int id;
    private String nombre;
    private Loxica_Categoria categoria;
    private float precio;
    private ArrayList<Loxica_Articulo> articulos;

    public Loxica_Lista() {
    }

    public Loxica_Lista(int id, String nombre, Loxica_Categoria loxicaCategoria, ArrayList<Loxica_Articulo> loxicaArticulos) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = loxicaCategoria;
        this.articulos = loxicaArticulos;
        for (int i = 0; i < loxicaArticulos.size(); i++) {
            this.precio += loxicaArticulos.get(i).getPrecio();
        }

    }

    /**
     * @return o número de articulos comprados e o total de articulos da lista.
     **/
    public String diferenciaArticulos() {
        int total = 0;
        int checked = 0;
        if (articulos != null) {
            for (int i = 0; i < articulos.size(); i++) {
                total++;
                if (articulos.get(i).isSeleccionado()) {
                    checked++;
                }
            }
        }
        if (total == 0) return "-";
        return checked + "/" + total;
    }
    /**
     * Obten o precio dos artículos comprados.
     * @return o precio dos productos da lista que estean comprados.
     **/
    public float precioChecked(){
        float precioChecked=0;
        if(articulos !=null) {
            for (int i = 0; i < articulos.size(); i++) {
                if(articulos.get(i).isSeleccionado()) {
                    precioChecked += articulos.get(i).getPrecio() * articulos.get(i).getCantidad();
                }
            }
        }
        return precioChecked;
    }
    /**
     * Comproba se todos os artículos da lista estan comprados.
     * @return true se todos os artículos da lista están comprados.
     **/
    public boolean TienesTodo(){
        int total=0;
        int checked=0;
        if(articulos !=null) {
            for (int i = 0; i < articulos.size(); i++) {
                total++;
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

    public Loxica_Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Loxica_Categoria loxica_Categoria) {
        this.categoria = loxica_Categoria;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public ArrayList<Loxica_Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(ArrayList<Loxica_Articulo> loxicaArticulos) {
        this.articulos = loxicaArticulos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

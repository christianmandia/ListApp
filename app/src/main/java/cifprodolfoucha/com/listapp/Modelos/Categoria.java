package cifprodolfoucha.com.listapp.Modelos;

import java.io.Serializable;

public class Categoria implements Serializable {
    private int id;
    private String nombre;

    public Categoria() {

    }
    public Categoria(int id, String nombre) {
        this.id=id;
        this.nombre = nombre;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

package fr.utt.if26.troyes;

/**
 * Created by Elkin R on 17/12/2017.
 */

public class problemes {

    private  int id;
    private String plato;
    private String precio;
    private byte[] image;

    public problemes(int id, String plato, String precio, byte[] image) {

        this.id = id;
        this.plato = plato;
        this.precio = precio;
        this.image = image;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlato() {
        return plato;
    }

    public void setPlato(String plato) {
        this.plato = plato;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

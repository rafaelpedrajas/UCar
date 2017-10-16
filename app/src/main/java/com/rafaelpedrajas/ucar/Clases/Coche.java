package com.rafaelpedrajas.ucar.Clases;

public class Coche {

    private int idCoche, porDefecto;
    private String marca, modelo, plazas, año, consumo;

    public Coche(int idCoche, String marca, String modelo, String plazas, String año, String consumo, int porDefecto) {
        this.idCoche = idCoche;
        this.marca = marca;
        this.modelo = modelo;
        this.plazas = plazas;
        this.año = año;
        this.consumo = consumo;
        this.porDefecto = porDefecto;
    }

    public Coche() {
    }


    public int getIdCoche() {
        return idCoche;
    }

    public void setIdCoche(int idCoche) {
        this.idCoche = idCoche;
    }

    public int getPorDefecto() {
        return porDefecto;
    }

    public void setPorDefecto(int porDefecto) {
        this.porDefecto = porDefecto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlazas() {
        return plazas;
    }

    public void setPlazas(String plazas) {
        this.plazas = plazas;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public String getConsumo() {
        return consumo;
    }

    public void setConsumo(String consumo) {
        this.consumo = consumo;
    }
}

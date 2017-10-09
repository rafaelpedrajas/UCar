package com.rafaelpedrajas.ucar.Clases;

public class Coche {

    private int idCoche, año, plazas, porDefecto;
    private String marca, modelo;
    private double consumo;

    public Coche(int idCoche, int año, int plazas, String marca, String modelo, double consumo, int porDefecto) {
        this.idCoche = idCoche;
        this.año = año;
        this.plazas = plazas;
        this.marca = marca;
        this.modelo = modelo;
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

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
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

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public int isPorDefecto() {
        return porDefecto;
    }

    public void setPorDefecto(int porDefecto) {
        this.porDefecto = porDefecto;
    }
}

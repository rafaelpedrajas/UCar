package com.rafaelpedrajas.ucar.Clases;

/**
 * Created by Rafael on 19/09/2017.
 */

public class Usuario
{
    private String correo, nombre, apellido, foto, telefono, nombreUniversidad, nombreProvincia, marcaCoche, modeloCoche;
    private int idUniversidad, idProvincia, idCoche, plazas;
    private float valoracionPuntualidad, valoracionAmabilidad, valoracionConduccion;

    public Usuario(String correo, String nombre, String apellido, String foto, String telefono, String nombreUniversidad, String nombreProvincia, String marcaCoche, String modeloCoche, int idUniversidad, int idProvincia, int idCoche, int plazas, float valoracionPuntualidad, float valoracionAmabilidad, float valoracionConduccion)
    {
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.foto = foto;
        this.telefono = telefono;
        this.nombreUniversidad = nombreUniversidad;
        this.nombreProvincia = nombreProvincia;
        this.marcaCoche = marcaCoche;
        this.modeloCoche = modeloCoche;
        this.idUniversidad = idUniversidad;
        this.idProvincia = idProvincia;
        this.idCoche = idCoche;
        this.plazas = plazas;
        this.valoracionPuntualidad = valoracionPuntualidad;
        this.valoracionAmabilidad = valoracionAmabilidad;
        this.valoracionConduccion = valoracionConduccion;
    }

    public Usuario()
    {
    }

    public String getCorreo()
    {
        return correo;
    }

    public void setCorreo(String correo)
    {
        this.correo = correo;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getApellido()
    {
        return apellido;
    }

    public void setApellido(String apellido)
    {
        this.apellido = apellido;
    }

    public String getFoto()
    {
        return foto;
    }

    public void setFoto(String foto)
    {
        this.foto = foto;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }

    public String getNombreUniversidad()
    {
        return nombreUniversidad;
    }

    public void setNombreUniversidad(String nombreUniversidad)
    {
        this.nombreUniversidad = nombreUniversidad;
    }

    public String getNombreProvincia()
    {
        return nombreProvincia;
    }

    public void setNombreProvincia(String nombreProvincia)
    {
        this.nombreProvincia = nombreProvincia;
    }

    public String getMarcaCoche()
    {
        return marcaCoche;
    }

    public void setMarcaCoche(String marcaCoche)
    {
        this.marcaCoche = marcaCoche;
    }

    public String getModeloCoche()
    {
        return modeloCoche;
    }

    public void setModeloCoche(String modeloCoche)
    {
        this.modeloCoche = modeloCoche;
    }

    public int getIdUniversidad()
    {
        return idUniversidad;
    }

    public void setIdUniversidad(int idUniversidad)
    {
        this.idUniversidad = idUniversidad;
    }

    public int getIdProvincia()
    {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia)
    {
        this.idProvincia = idProvincia;
    }

    public int getIdCoche()
    {
        return idCoche;
    }

    public void setIdCoche(int idCoche)
    {
        this.idCoche = idCoche;
    }

    public int getPlazas()
    {
        return plazas;
    }

    public void setPlazas(int plazas)
    {
        this.plazas = plazas;
    }

    public float getValoracionPuntualidad()
    {
        return valoracionPuntualidad;
    }

    public void setValoracionPuntualidad(float valoracionPuntualidad)
    {
        this.valoracionPuntualidad = valoracionPuntualidad;
    }

    public float getValoracionAmabilidad()
    {
        return valoracionAmabilidad;
    }

    public void setValoracionAmabilidad(float valoracionAmabilidad)
    {
        this.valoracionAmabilidad = valoracionAmabilidad;
    }

    public float getValoracionConduccion()
    {
        return valoracionConduccion;
    }

    public void setValoracionConduccion(float valoracionConduccion)
    {
        this.valoracionConduccion = valoracionConduccion;
    }

    @Override
    public String toString()
    {
        return nombre+" "+apellido;
    }
}

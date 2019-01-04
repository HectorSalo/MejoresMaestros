package com.example.chirinos.mejoresmaestros;

import java.util.Date;

public class ConstructorPublicadores {

    private int idPublicador, imagen;
    private String NombrePublicador, ApellidoPublicador,  correo;

    private Date ultAsignacion, ultAyudante, ultSustitucion;

    //public ConstructorPublicadores () {}


    public ConstructorPublicadores(int idPublicador, String nombrePublicador, String apellidoPublicador, int imagen) {
        this.idPublicador = idPublicador;
        NombrePublicador = nombrePublicador;
        ApellidoPublicador = apellidoPublicador;
        this.imagen = imagen;
    }

    public int getIdPublicador() {
        return idPublicador;
    }

    public String getNombrePublicador() {
        return NombrePublicador;
    }

    public String getApellidoPublicador() {
        return ApellidoPublicador;
    }

    public int getImagen() {
        return imagen;
    }

    public String getCorreo() {
        return correo;
    }

    public Date getUltAsignacion() {
        return ultAsignacion;
    }

    public Date getUltAyudante() {
        return ultAyudante;
    }

    public Date getUltSustitucion() {
        return ultSustitucion;
    }


    public void setIdPublicador(int idPublicador) {
        this.idPublicador = idPublicador;
    }

    public void setNombrePublicador(String nombrePublicador) {
        NombrePublicador = nombrePublicador;
    }

    public void setApellidoPublicador(String apellidoPublicador) {
        ApellidoPublicador = apellidoPublicador;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setUltAsignacion(Date ultAsignacion) {
        this.ultAsignacion = ultAsignacion;
    }

    public void setUltAyudante(Date ultAyudante) {
        this.ultAyudante = ultAyudante;
    }

    public void setUltSustitucion(Date ultSustitucion) {
        this.ultSustitucion = ultSustitucion;
    }
}

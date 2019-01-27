package com.example.chirinos.mejoresmaestros;

import java.util.Date;

public class ConstructorPublicadores {

    private String NombrePublicador, ApellidoPublicador, telefono, correo, genero,idPublicador, imagen;
    private String ultAsignacion, ultAyudante, ultSustitucion;

    public ConstructorPublicadores () {}



    public String getGenero() {
        return genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getIdPublicador() {
        return idPublicador;
    }

    public String getNombrePublicador() {
        return NombrePublicador;
    }

    public String getApellidoPublicador() {
        return ApellidoPublicador;
    }

    public String getImagen() {
        return imagen;
    }

    public String getCorreo() {
        return correo;
    }

    public String getUltAsignacion() {
        return ultAsignacion;
    }

    public String getUltAyudante() {
        return ultAyudante;
    }

    public String getUltSustitucion() {
        return ultSustitucion;
    }


    public void setIdPublicador(String idPublicador) {
        this.idPublicador = idPublicador;
    }

    public void setNombrePublicador(String nombrePublicador) {
        NombrePublicador = nombrePublicador;
    }

    public void setApellidoPublicador(String apellidoPublicador) {
        ApellidoPublicador = apellidoPublicador;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setUltAsignacion(String ultAsignacion) {
        this.ultAsignacion = ultAsignacion;
    }

    public void setUltAyudante(String ultAyudante) {
        this.ultAyudante = ultAyudante;
    }

    public void setUltSustitucion(String ultSustitucion) {
        this.ultSustitucion = ultSustitucion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}

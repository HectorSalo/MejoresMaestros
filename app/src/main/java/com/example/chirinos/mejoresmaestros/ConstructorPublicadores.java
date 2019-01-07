package com.example.chirinos.mejoresmaestros;

import java.util.Date;

public class ConstructorPublicadores {

    private Integer idPublicador, imagen;
    private String NombrePublicador, ApellidoPublicador, telefono, correo, genero;
    private Date ultAsignacion, ultAyudante, ultSustitucion;

    public ConstructorPublicadores () {}


    /*public ConstructorPublicadores(Integer idPublicador, String nombrePublicador, String apellidoPublicador, Integer imagen) {
        this.idPublicador = idPublicador;
        NombrePublicador = nombrePublicador;
        ApellidoPublicador = apellidoPublicador;
        this.imagen = imagen;
    }*/

    public String getGenero() {
        return genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public Integer getIdPublicador() {
        return idPublicador;
    }

    public String getNombrePublicador() {
        return NombrePublicador;
    }

    public String getApellidoPublicador() {
        return ApellidoPublicador;
    }

    public Integer getImagen() {
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


    public void setIdPublicador(Integer idPublicador) {
        this.idPublicador = idPublicador;
    }

    public void setNombrePublicador(String nombrePublicador) {
        NombrePublicador = nombrePublicador;
    }

    public void setApellidoPublicador(String apellidoPublicador) {
        ApellidoPublicador = apellidoPublicador;
    }

    public void setImagen(Integer imagen) {
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

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}

package com.example.appclnica;

import org.json.JSONObject;

public class MantConstructor {
    private int id;
    private String sucursal;
    private String area;
    private String nombre;
    private String tipo;
    private String empresa;
    private String fecha;
    private String mantenimiento;


    public MantConstructor(int id,String tipo, String sucursal, String area, String nombre, String empresa, String fecha,String mantenimiento) {
        this.id = id;
        this.sucursal = sucursal;
        this.area = area;
        this.nombre = nombre;
        this.tipo = tipo;
        this.empresa = empresa;
        this.fecha = fecha;
        this.mantenimiento = mantenimiento;
    }

    public MantConstructor(int id_equipo, String s, String s1, String s2, String s3, String empresa, String s4) {
    }

    public int getId() {
        return id;
    }

    public String getSucursal() {
        return sucursal;
    }

    public String getArea() {
        return area;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getFecha() {
        return fecha;
    }

    public String getMantenimiento () { return mantenimiento;   }
}

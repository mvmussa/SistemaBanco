package modelo;

import java.util.ArrayList;
import java.util.List;

public class Banco {

    /**
     * Atributos
     * id, identificador numerico del banco
     * nombreBanco de tipo string
     * sucursales es una lista donde tiene 1 0 más sucursales
     * cuentas es una lista donde tiene 0 o más cuentas
     */
    private Integer id;
    private String nombreBanco;
    private List<Sucursal> sucursales;
    private List<Cuenta> cuentas;

    /*
    * constructor del banco
    * parametros Id nombre del banco
    * */
    public Banco(Integer id, String nombreBanco) {
        super();
        this.id = id;
        this.nombreBanco = nombreBanco;
        this.sucursales = new ArrayList<>();
        this.cuentas = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }



}

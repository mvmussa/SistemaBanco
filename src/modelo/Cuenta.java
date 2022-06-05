package modelo;
public abstract class Cuenta {
    protected Integer numCuenta;
    protected Double saldo;
    protected String cbu;

    private String tipoDescripcion= "";


    // Constructor
    public Cuenta() {
    }

    public Cuenta(Integer numCuenta, Double saldo, String cbu, String tipo) {
        this.numCuenta = numCuenta;
        this.saldo = saldo;
        this.cbu = cbu;
        this.tipoDescripcion = tipo;
    }

    public String getTipoDescripcion() {
        return tipoDescripcion;
    }

    public void setTipoDescripcion(String tipoDescripcion) {
        this.tipoDescripcion = tipoDescripcion;
    }

    public Integer getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(Integer numCuenta) {
        this.numCuenta = numCuenta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

}


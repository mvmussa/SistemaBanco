package constants;

public enum TipoCuenta {

    //todo me falto poner cuenta en pesos y dolares
    CAJA_AHORRO("Caja de Ahorro", 1),
    CUENTA_CORRIENTE("Cuenta Corriente", 2);

    private Integer nroCuenta;
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }
    public Integer getNroCuenta() {
        return nroCuenta;
    }
    TipoCuenta(String descripcion, Integer nroCuenta){
        this.nroCuenta = nroCuenta;
        this.descripcion = descripcion;

    }

}

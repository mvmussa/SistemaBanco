package modelo;

public class CuentaCorriente extends Cuenta {

    public CuentaCorriente(Integer numCuenta, Double saldo, String cbu) {
        super(numCuenta, saldo, cbu, "CC");
    }

}

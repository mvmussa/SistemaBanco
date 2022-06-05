package modelo;

public class CajaDeAhorro extends Cuenta {
    // Atributos
    private String tipoMoneda;
    // COSNTRUCTOR
    public CajaDeAhorro() {
    }

    /**
     *
     * @param numCuenta
     * @param saldo
     * @param tipoMoneda: pesos 'P', dolares 'D'
     */
    public CajaDeAhorro(Integer numCuenta, Double saldo, String cbu, String tipoMoneda) {
        super(numCuenta, saldo, cbu, "CA");
        this.tipoMoneda = tipoMoneda;
    }

}

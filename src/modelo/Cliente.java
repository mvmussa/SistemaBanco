package modelo;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class Cliente extends Persona {
    // ATRIBUTOS

    private String domicilio;
    private Date altaCliente;
    // private Sucursal sucursal;

    private List<Cuenta> cuentas;


    public Cliente(String dni, String nombreApellido, String telefono, String email, String domicilio,
                   List<Cuenta> cuentas, Date altaCliente, BigInteger cuil) {
        super(dni, nombreApellido, telefono, email, cuil);
        this.domicilio = domicilio;
        this.cuentas = cuentas;
        this.altaCliente = altaCliente;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public Date getAltaCliente() {
        return altaCliente;
    }

    public void setAltaCliente(Date altaCliente) {
        this.altaCliente = altaCliente;
    }

    // CONSTRUCTOR

}

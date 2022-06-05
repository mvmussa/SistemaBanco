package services;

import modelo.Cliente;
import modelo.Cuenta;

import java.util.List;

public interface ICuentaService {

    public Double extraer(Double monto);

    public void depositar(Double monto);

    public void addCuenta(Integer cliente, Cuenta cuenta) throws Exception;

    public List<Cuenta> getCuentasCliente(Integer nroCliente)throws Exception;

    void extraerSaldo(Integer nroCuenta, Double saldo)throws Exception;

    void consultarSaldo(Integer nroCuenta)throws Exception;

    void depositarSaldo(Integer nroCuenta, Double saldo)throws Exception;

    void TranferirSaldo(String cbuCuentaOrigen, String cbuCuentaDestino, Double saldo)throws Exception;

    Cuenta findCuenta(String cbu)throws Exception;
}


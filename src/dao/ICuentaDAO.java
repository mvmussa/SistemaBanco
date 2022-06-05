package dao;
import modelo.Cuenta;

import java.util.List;


public interface ICuentaDAO {

    void addCuenta(Cuenta cuenta, Integer nroCliente)throws Exception;

    List<Cuenta> getCuentasCliente(Integer nroCliente)throws Exception;

    Cuenta findCuenta(Integer nroCuenta)throws Exception;

    void actualizarSaldo(Cuenta cuenta) throws Exception;

    Cuenta findCuentaByCbu(String cbu)throws Exception;

    List<Cuenta> getCuentasBySucursal(Integer nroSucursal)throws  Exception;
}

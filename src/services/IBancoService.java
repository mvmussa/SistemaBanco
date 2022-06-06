package services;

import modelo.Cliente;
import modelo.Cuenta;
import modelo.Sucursal;

import java.util.List;

public interface IBancoService {

     Boolean eliminarSucursal(Integer nrosucursal)throws Exception;

    void agregarClienteASuc(int i, Cliente clienteAlta);

    void listarClientes();

    Integer sucActiva (Integer nroSucursalBorrar)throws Exception;

    void borroSucursalCuenta(List<Cuenta> cuentas, Integer nroSucursal)throws Exception;

    void agregoSucursalCuenta(List<Cuenta> cuentas, Integer nroSucursal)throws Exception;

    void verificarSucursal()throws Exception;

    void agregarCuentaASuc(int numSucursal, Cuenta cuenta);

}

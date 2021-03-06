package dao;

import modelo.Cuenta;

public interface ISucursalDAO {

    void borrarSucursal(Integer nroSucursal)throws Exception;

    Integer getSucursalRemplazo(Integer nroSucursal)throws Exception;

    Boolean borroSucursal(Integer nroSucursal)throws Exception;

    Boolean borrarSucursalCuenta(Cuenta cuenta,Integer nroSucursal)throws Exception;

    Boolean asignoSucursalCuenta(Cuenta itCuenta, Integer nroSucursal)throws Exception;
}

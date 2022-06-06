package services;

import dao.CuentaDAO;
import dao.SucursalDAO;
import exception.BancoException;
import modelo.Cuenta;

import java.util.List;

public class SucursalService implements ISucursalService {

    /**
     *
     * @return int cantidad de sucursales creadas en la bd
     * @throws Exception -  captura errores de bd
     */
    @Override
    public int getCantidadSucursal() throws Exception {

        SucursalDAO sucursalDao = SucursalDAO.getInstance();
        return sucursalDao.getCantidadSucursal();
    }

    /**
     * Crea una nueva sucursal
     * @param numero valor numérico
     * @throws Exception de bd
     */
    @Override
    public void crearSucursal(int numero) throws Exception {

        SucursalDAO sucursalDao = SucursalDAO.getInstance();
        sucursalDao.crearSucursal(numero);


    }

    /**
     * Borra sucursal si es que hay una sucursal auxiliar para tranferir las cuentas de los clientes
     * @param nroSucursal valor numérico
     * @throws Exception de bd
     */
    @Override
    public void borrarSucursal(Integer nroSucursal) throws Exception {
            //pido todas las cuentas con la suc nroSucursal
            CuentaDAO cuentaDao = CuentaDAO.getInstance();
            List<Cuenta> cuentas =cuentaDao.getCuentasBySucursal(nroSucursal);

            //busco la primer sucursal activa para remplazar
            BancoService servicioBanco =new BancoService();
            Integer nroSucRemplazo= servicioBanco.sucActiva(nroSucursal);
            if(nroSucRemplazo == null){
                throw new BancoException("No se puede borrar la sucursal antes debe crear otra");
            }
            servicioBanco.borroSucursalCuenta(cuentas,nroSucursal);
            Boolean borrada= servicioBanco.eliminarSucursal(nroSucursal);
            if(borrada){
                servicioBanco.agregoSucursalCuenta(cuentas,nroSucRemplazo);
            }

    }

    @Override
    public void asociarCuenta(int numSucursal, Integer numCuenta) {

        SucursalDAO sucursalDao = SucursalDAO.getInstance();
        sucursalDao.asociarCuentaSucursal(numSucursal,numCuenta);
    }
}

package services;

import dao.CuentaDAO;
import exception.BancoException;
import modelo.Cuenta;

import java.util.List;

public class SucursalService implements ISucursalService {


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

            // borro la suc
            Boolean borrada= servicioBanco.eliminarSucursal(nroSucursal);


            if(borrada){
                servicioBanco.agregoSucursalCuenta(cuentas,nroSucRemplazo);
            }

    }
}

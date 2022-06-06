package services;

public interface ISucursalService
{
    int getCantidadSucursal()throws Exception;
    void crearSucursal(int cantidad)throws Exception;
    void borrarSucursal(Integer nroSucursal)throws Exception;


    void asociarCuenta(int numSucursal, Integer numCuenta);
}

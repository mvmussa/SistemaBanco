package services;

import dao.CuentaDAO;
import dao.SucursalDAO;
import modelo.Banco;
import modelo.Cliente;
import modelo.Cuenta;
import modelo.Sucursal;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BancoService implements IBancoService {

    private Banco miBanco;

    /*
     * constructor
     * */
    public BancoService() {
    }

    public BancoService(Banco banco) {
        this.setMiBanco(banco);
    }

    public Banco getMiBanco() {
        return miBanco;
    }

    public void setMiBanco(Banco miBanco) {
        this.miBanco = miBanco;
    }


    /**
     *  borra la sucursal de la tabla sucursales
     * @param nroSucursal valor numérido
     * @return boolean
     * @throws Exception bd
     */
    @Override
    public Boolean eliminarSucursal(Integer nroSucursal) throws Exception{

        SucursalDAO sucursalDao = SucursalDAO.getInstance();
        Boolean sucursalBorrada = sucursalDao.borroSucursal(nroSucursal);
        return sucursalBorrada;
    }

    @Override
    public void agregarClienteASuc(int i, Cliente clienteAlta) {

        Banco banco = getMiBanco();
        List<Sucursal> sucursales = banco.getSucursales();
        sucursales.stream().forEach((Sucursal s) -> {
            if (s.getNumSucursal() == i) s.getListaClientes().add(clienteAlta);
        });

    }

    @Override
    public void listarClientes() {

        Banco banco = getMiBanco();
        List<Sucursal> sucursales = banco.getSucursales();
        sucursales.stream().forEach((s) -> System.out.println("La suc: " + s.getNombreSucursal() + " Tiene :" + s.listarClientes()));

    }

    /**
     * selecciona la suc activo diferente al numero de suc a borrar
     * @param nroSucursal num de suc
     * @return Integer
     * @throws Exception bd
     */
    @Override
    public Integer sucActiva(Integer nroSucursal) throws Exception {

        Integer suc = 0;
        SucursalDAO sucursalDao = SucursalDAO.getInstance();
        Integer sucursalAux = sucursalDao.getSucursalRemplazo(nroSucursal);
        return sucursalAux;
    }

    /**
     * recorre cada cuenta y elimina la relación con la sucursal a borrar
     * para luego poder borrar la sucursal
     * y asignar la nueva sucursal a la cuenta
     *
     * @param cuentas lista cuentas
     * @param nroSucursal valor numérico
     * @throws Exception bd
     */
    @Override
    public void borroSucursalCuenta(List<Cuenta> cuentas, Integer nroSucursal) throws Exception {
        SucursalDAO sucursalDao = SucursalDAO.getInstance();
        for (Cuenta itCuenta : cuentas) {
            Boolean ok= sucursalDao.borrarSucursalCuenta(itCuenta,nroSucursal);
            if(ok){
                System.out.println("se borro correctamente de la sucursal la cuenta "+itCuenta.getNumCuenta());
            }
        }
    }

    /**
     * Agrego cuentas a una sucursal
     * @param cuentas lista de cuentas
     * @param nroSucursal numero de suc
     * @throws Exception bd
     */
    @Override
    public void agregoSucursalCuenta(List<Cuenta> cuentas, Integer nroSucursal) throws Exception {

        SucursalDAO sucursalDao = SucursalDAO.getInstance();
        for (Cuenta itCuenta : cuentas) {
            Boolean ok= sucursalDao.asignoSucursalCuenta(itCuenta,nroSucursal);
            if(ok){
                System.out.println("se asigno una nueva sucursal a la cuenta "+itCuenta.getNumCuenta());
            }
        }
    }

    /**
     * Verifico que al iniciar el sistema existan como mínimo 2 sucursales
     * @throws Exception bd
     */
    @Override
    public void verificarSucursal() throws Exception {

        SucursalService servicioCucursal = new SucursalService();
        int cantidadSucursales= servicioCucursal.getCantidadSucursal();
        if(cantidadSucursales < 2 ){
            do{
                servicioCucursal.crearSucursal(cantidadSucursales++);
            }while(cantidadSucursales < 2);

        }
    }

    /**
     * Agega una cuenta a una sucursal cuando se crea
     * @param numSucursal valor numerico
     * @param cuenta cuenta
     *
     */
    @Override
    public void agregarCuentaASuc(int numSucursal, Cuenta cuenta) {

        SucursalService servicioCucursal = new SucursalService();
        servicioCucursal.asociarCuenta(numSucursal, cuenta.getNumCuenta());


    }

}

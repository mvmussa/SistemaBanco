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


    @Override
    public void registrarSucursal(Sucursal sucursal) {

        //todo registrar sucursal que impacte en la bd
        Banco banco = getMiBanco();
        List<Sucursal> sucursales = new ArrayList<>();
        sucursales.add(sucursal);
        banco.setSucursales(sucursales);

    }

    @Override
    public Boolean eliminarSucursal(Integer nroSucursal) throws Exception{

        //borra la sucursal de la tabla sucursales
        //todo eliminar sucursal que impacte en la bd
        SucursalDAO sucursalDao = SucursalDAO.getInstance();
        Boolean sucursalBorrada = sucursalDao.borroSucursal(nroSucursal);
        return sucursalBorrada;
    }

    @Override
    public void agregarClienteASuc(int i, Cliente clienteAlta) {

        //todo impactar en la base de datos
        Banco banco = getMiBanco();
        List<Sucursal> sucursales = banco.getSucursales();
        sucursales.stream().forEach((Sucursal s) -> {
            if (s.getNumSucursal() == i) s.getListaClientes().add(clienteAlta);
        });

        /*Sucursal sucFind = sucursales.stream().filter(sucursal -> sucursal.getNumSucursal() == i).findFirst()
                .orElse(null);
        me devuelve 1 o mas objetos
        */
        /* Customer james = customers.stream()
                .filter(customer -> "James".equals(customer.getName()))
                .findAny()
                .orElse(null);*/
    }

    @Override
    public void listarClientes() {

        //todo listar clientes
        Banco banco = getMiBanco();
        List<Sucursal> sucursales = banco.getSucursales();
        sucursales.stream().forEach((s) -> System.out.println("La suc: " + s.getNombreSucursal() + " Tiene :" + s.listarClientes()));

    }

    @Override
    public Integer sucActiva(Integer nroSucursal) throws Exception {

        //selecciona la suc activo diferente al numero de suc a borrar
        Integer suc = 0;
        SucursalDAO sucursalDao = SucursalDAO.getInstance();
        Integer sucursalAux = sucursalDao.getSucursalRemplazo(nroSucursal);
        return sucursalAux;
    }

    @Override
    public void borroSucursalCuenta(List<Cuenta> cuentas, Integer nroSucursal) throws Exception {
        //recorre cada cuenta y elimina la relación con la sucursal a borrar
        // para luego poder borrar la sucursal
        // y asignar la nueva sucursal a la cuenta
        SucursalDAO sucursalDao = SucursalDAO.getInstance();
        for (Cuenta itCuenta : cuentas) {
            Boolean ok= sucursalDao.borrarSucursalCuenta(itCuenta,nroSucursal);
            if(ok){
                System.out.println("se borro correctamente de la sucursal la cuenta "+itCuenta.getNumCuenta());
            }
        }
    }

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


}

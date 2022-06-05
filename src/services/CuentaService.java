package services;

import dao.CuentaDAO;
import exception.BancoException;
import modelo.Cliente;
import modelo.Cuenta;

import java.util.List;

public class CuentaService implements ICuentaService {

    @Override
    public Double extraer(Double monto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void depositar(Double monto) {
        // TODO Auto-generated method stub

    }


    @Override
    public void addCuenta(Integer nroCliente, Cuenta cuenta) throws Exception {

        //agrega una cuenta nueva a un cliente que ya existe
        CuentaDAO cuentaDao = CuentaDAO.getInstance();
        cuentaDao.addCuenta(cuenta, nroCliente);
    }

    public List<Cuenta> getCuentasCliente(Integer nroCliente) throws Exception {

        CuentaDAO cuentaDao = CuentaDAO.getInstance();
        return cuentaDao.getCuentasCliente(nroCliente);
    }

    @Override
    public void extraerSaldo(Integer nroCuenta, Double saldo) throws Exception {

        CuentaDAO cuentaDao = CuentaDAO.getInstance();
        Cuenta cuenta = cuentaDao.findCuenta(nroCuenta);
        if (cuenta.getSaldo() >= saldo) {
            Double resto = cuenta.getSaldo() - saldo;
            cuenta.setSaldo(resto);
            cuentaDao.actualizarSaldo(cuenta);
        } else {
            throw new BancoException("El saldo es menor a la extracción ");
        }


    }

    @Override
    public void consultarSaldo(Integer nroCuenta) throws Exception {

        CuentaDAO cuentaDao = CuentaDAO.getInstance();
        Cuenta cuenta = cuentaDao.findCuenta(nroCuenta);
        System.out.println("Su saldo es: " + cuenta.getSaldo());

    }

    @Override
    public void depositarSaldo(Integer nroCuenta, Double saldo) throws Exception {

        CuentaDAO cuentaDao = CuentaDAO.getInstance();
        Cuenta cuenta = cuentaDao.findCuenta(nroCuenta);
        Double sumo = cuenta.getSaldo() + saldo;
        cuenta.setSaldo(sumo);
        cuentaDao.actualizarSaldo(cuenta);


    }


    @Override
    public Cuenta findCuenta(String cbu) throws Exception {

        CuentaDAO cuentaDao = CuentaDAO.getInstance();
        Cuenta cuenta = cuentaDao.findCuentaByCbu(cbu);
        return cuenta;

    }


    @Override
    public void TranferirSaldo(String cbuCuentaOrigen, String cbuCuentaDestino, Double saldo) throws Exception {


        Cuenta cuentaOrigen = findCuenta(cbuCuentaOrigen);
        Cuenta cuentaDestino = findCuenta(cbuCuentaDestino);
        if (cuentaOrigen.getSaldo() >= saldo) {
            extraerSaldo(cuentaOrigen.getNumCuenta(), saldo);
            depositarSaldo(cuentaDestino.getNumCuenta(), saldo);
        }


    }


}
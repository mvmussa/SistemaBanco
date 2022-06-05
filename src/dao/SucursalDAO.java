package dao;

import Config.MysqlConection;
import exception.BancoException;
import modelo.CajaDeAhorro;
import modelo.Cuenta;
import modelo.CuentaCorriente;
import modelo.Sucursal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class SucursalDAO implements ISucursalDAO {

    private static SucursalDAO instance;

    public static SucursalDAO getInstance() {
        if (instance == null) {
            instance = new SucursalDAO();
        }
        return instance;
    }


    public void borrarCuentaSucursal(Integer nroSucursal) throws Exception {

        //List<Cuenta> getCuentasSucursal(nroSucursal);
        //TODO borra en la tabla intermedia la cuenta asociada a la sucursal

    }

    @Override
    public void borrarSucursal(Integer nroSucursal) throws Exception {

        //List<Cuenta> getCuentasSucursal(nroSucursal);
        //TODO borra  la sucursal de su tabla o update


    }

    @Override
    public Integer getSucursalRemplazo(Integer nroSucursal) throws Exception {

        //busca una sucursal que este activa y sea distinta a nroSucursal
        Connection co;
        Statement stm;
        co = MysqlConection.getConnection();
        stm = co.createStatement();
        Integer nro = null;
        try {
            String sql = "SELECT NRO_SUCURSAL FROM sucursales  where NRO_SUCURSAL <> " + nroSucursal;
            //System.out.println(sql);
            java.sql.ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                nro = rs.getInt("NRO_SUCURSAL");
            }
            rs.close();
            stm.close();
        } catch (java.sql.SQLException ex) {
            co.rollback();
            throw new BancoException("Error en ejecución en busqueda sucursal activa " + ex.getMessage());
        } finally {
            co.close();
        }
        return nro;


    }

    @Override
    public Boolean borroSucursal(Integer nroSucursal) throws Exception {

        Connection co;
        Statement stm;
        co = MysqlConection.getConnection();
        stm = co.createStatement();
        Boolean borradoOk = false;
        try {
            String sql = "DELETE FROM sucursales  where NRO_SUCURSAL = " + nroSucursal;
            //System.out.println(sql);
            stm.execute(sql);
            stm.close();
            borradoOk = true;
        } catch (java.sql.SQLException ex) {
            co.rollback();
            throw new BancoException("Error en ejecución en borrado sucursal " + ex.getMessage());
        } finally {
            co.close();
        }
        return borradoOk;
    }

    @Override
    public Boolean borrarSucursalCuenta(Cuenta cuenta, Integer nroSucursal) throws Exception {
        // borra la relación suc - cuenta
        Connection co;
        Statement stm;
        co = MysqlConection.getConnection();
        stm = co.createStatement();
        Boolean borradoOk = false;
        try {
            String sql = "DELETE FROM sucursal_cuentas  where NRO_SUCURSAL = " + nroSucursal + " and NRO_CUENTA = " + cuenta.getNumCuenta();
            //System.out.println(sql);
            stm.execute(sql);
            stm.close();
            borradoOk = true;

        } catch (java.sql.SQLException ex) {
            co.rollback();
            throw new BancoException("Error en ejecución en borrado sucursal-cuenta " + ex.getMessage());
        } finally {
            co.close();
        }
        return borradoOk;

    }

    @Override
    public Boolean asignoSucursalCuenta(Cuenta itCuenta, Integer nroSucursal) throws Exception {

        // agrega  la relación sucursal - cuenta
        Connection co;
        Statement stm;
        co = MysqlConection.getConnection();
        stm = co.createStatement();
        Boolean asignaOk = false;
        String sql = "INSERT INTO sucursal_cuentas (id,NRO_SUCURSAL,NRO_CUENTA) VALUES (?, ?, ?)";
        java.sql.PreparedStatement stmt = co.prepareStatement(sql);
        //System.out.println(sql);
        try {
            co.setAutoCommit(false);
            stmt.setInt(1, 0);
            stmt.setInt(2, nroSucursal);
            stmt.setInt(3, itCuenta.getNumCuenta());
            stmt.execute();
            co.commit();
            asignaOk = true;
        } catch (java.sql.SQLException ex) {
            co.rollback();
            throw new BancoException("Error en ejecución en asiganacion sucursal-cuenta " + ex.getMessage());
        } finally {
            co.close();
        }
        return asignaOk;
    }

    public void agregarCuentaSucursal(Integer nroSucursal) throws Exception {

        //List<Cuenta> getCuentasSucursal(nroSucursal);
        //TODO agreaga la cuenta a la sucursal


    }


}

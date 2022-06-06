package dao;

import Config.MysqlConection;
import exception.BancoException;
import modelo.Cuenta;

import java.math.BigDecimal;
import java.sql.*;

public class SucursalDAO implements ISucursalDAO {

    private static SucursalDAO instance;

    public static SucursalDAO getInstance() {
        if (instance == null) {
            instance = new SucursalDAO();
        }
        return instance;
    }


    public void borrarCuentaSucursal(Integer nroSucursal) throws Exception {

        //TODO borra en la tabla intermedia bd la cuenta asociada a la sucursal

    }

    @Override
    public void borrarSucursal(Integer nroSucursal) throws Exception {

        //TODO borra  la sucursal de su tabla o update


    }

    /**
     * busca una sucursal que este activa y sea distinta al parametro  nroSucursal
     * @param nroSucursal - numero de sucursal integer
     * @return Integer
     * @throws Exception de bd
     */
    @Override
    public Integer getSucursalRemplazo(Integer nroSucursal) throws Exception {

        Connection co;
        Statement stm;
        co = MysqlConection.getConnection();
        stm = co.createStatement();
        Integer nro = null;
        try {
            String sql = "SELECT NRO_SUCURSAL FROM sucursales  where NRO_SUCURSAL <> " + nroSucursal;
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

    /**
     * borra la sucursal de la bd
     * @param nroSucursal - numero de sucursal integer
     * @return boolean
     * @throws Exception de bd
     */
    @Override
    public Boolean borroSucursal(Integer nroSucursal) throws Exception {

        Connection co;
        Statement stm;
        co = MysqlConection.getConnection();
        stm = co.createStatement();
        boolean borradoOk = false;
        try {
            String sql = "DELETE FROM sucursales  where NRO_SUCURSAL = " + nroSucursal;
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

    /**
     * borra la relaciín sucursal-cuenta
     * @param cuenta  objeto cuenta
     * @param nroSucursal numero de sucursal de tipo integer
     * @return boolean
     * @throws Exception de bd
     */
    @Override
    public Boolean borrarSucursalCuenta(Cuenta cuenta, Integer nroSucursal) throws Exception {

        Connection co;
        Statement stm;
        co = MysqlConection.getConnection();
        stm = co.createStatement();
        boolean borradoOk = false;
        try {
            String sql = "DELETE FROM sucursal_cuentas  where NRO_SUCURSAL = " + nroSucursal + " and NRO_CUENTA = " + cuenta.getNumCuenta();
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

    /**
     * agrega  la relación sucursal - cuenta
     * @param itCuenta objeto cuenta
     * @param nroSucursal numero de sucursal
     * @return boolean
     * @throws Exception  de bd
     */
    @Override
    public Boolean asignoSucursalCuenta(Cuenta itCuenta, Integer nroSucursal) throws Exception {

        Connection co;
        //Statement stm;
        co = MysqlConection.getConnection();
        //stm = co.createStatement();
        boolean asignaOk = false;
        String sql = "INSERT INTO sucursal_cuentas (id,NRO_SUCURSAL,NRO_CUENTA) VALUES (?, ?, ?)";
        java.sql.PreparedStatement stmt = co.prepareStatement(sql);
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

    /**
     * Retorna la cantidad de sucursales
     * @return int
     * @throws Exception de bd
     */
    @Override
    public int getCantidadSucursal() throws Exception {

        Connection co;
        Statement stm;
        co = MysqlConection.getConnection();
        stm = co.createStatement();
        int cantidad = 0;
        try {
            String sql = "SELECT count(*) as cantidad  FROM sucursales ";
            java.sql.ResultSet rs = stm.executeQuery(sql);
            if (rs.next()){
                cantidad = rs.getInt("cantidad");
            }
            rs.close();
            stm.close();
        } catch (java.sql.SQLException ex) {
            throw new BancoException("Error en ejecución en cantidad sucursales  " + ex.getMessage());
        } finally {
            co.close();
        }

        return cantidad;
    }

    /**
     * crea una sucursal
     * @param numero valor numerico para agregar al nombre de la sucursal
     * @throws Exception bd
     */
    @Override
    public void crearSucursal(int numero) throws Exception {

        Connection co;
        java.sql.PreparedStatement stm;
        co = MysqlConection.getConnection();
        co.setAutoCommit(false);
        ResultSet rs = null;
        String nombre = "Sucursal "+numero;
        String sql = "INSERT INTO sucursales (NOMBRE_SUCURSAL) VALUES ( ?)";
        try {
            stm = co.prepareStatement(sql);
            stm.setString(1, nombre);
            stm.execute();
            co.commit();

        } catch (java.sql.SQLException ex) {
            co.rollback();
            throw new BancoException("Error de inserción en tabla sucursales " + ex.getMessage());
        } finally {
            co.close();
        }

    }

    @Override
    public void asociarCuentaSucursal(int numSucursal, Integer numCuenta) {

       /* Connection co;
        java.sql.PreparedStatement stm;
        co = MysqlConection.getConnection();
        co.setAutoCommit(false);
        ResultSet rs = null;
        String sql = "INSERT INTO sucursal_cuentas (NRO_SUCURSAL, NRO_CUENTA) VALUES (?, ?)";
        try {
            stm = co.prepareStatement(sql);
            stm.setInt(1, numSucursal);
            stm.setInt(2, numCuenta);
            stm.execute();
            co.commit();

        } catch (java.sql.SQLException ex) {
            co.rollback();
            throw new BancoException("Error de inserción en tabla sucursal_cuentas " + ex.getMessage());
        } finally {
            co.close();
        }
*/

    }

    /**
     * vincula una cuenta a una sucursal
     * @param nroSucursal valor numerico
     * @throws Exception bd
     */
    public void agregarCuentaSucursal(Integer nroSucursal) throws Exception {

        //TODO agreaga la cuenta a la sucursal


    }


}

package dao;

import Config.MysqlConection;
import exception.BancoException;
import modelo.CajaDeAhorro;
import modelo.Cuenta;
import modelo.CuentaCorriente;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAO implements ICuentaDAO {

    private static CuentaDAO instance;

    public static CuentaDAO getInstance() {
        if (instance == null) {
            instance = new CuentaDAO();
        }
        return instance;
    }

    @Override
    public void addCuenta(Cuenta cuenta, Integer nroCliente) throws Exception {

        //agrega una cuenta
        Connection co;
        co = MysqlConection.getConnection();
        co.setAutoCommit(false);
        Integer idCuenta;
        ResultSet rs = null;
        String sql = "INSERT INTO cuentas (NRO_CUENTA,CBU,SALDO, NRO_CLIENTE, TIPO_CUENTA) VALUES (?, ?, ?,?,?)";
        java.sql.PreparedStatement stmt = co.prepareStatement(sql);
        try {
            co.setAutoCommit(false);
            stmt = co.prepareStatement(sql, new String[]{"NRO_CUENTA"});
            stmt.setInt(1, 0);
            stmt.setBigDecimal(2, new BigDecimal(cuenta.getCbu()));
            stmt.setDouble(3, cuenta.getSaldo());
            stmt.setInt(4, nroCliente);
            stmt.setString(5, cuenta.getTipoDescripcion());
            stmt.execute();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idCuenta = rs.getInt(1);
            }
            co.commit();

        } catch (java.sql.SQLException ex) {
            co.rollback();
            throw new BancoException("Error de inserción en tabla cuentas " + ex.getMessage());
        } finally {
            stmt.close();
        }


    }

    @Override
    public List<Cuenta> getCuentasCliente(Integer nroCliente) throws Exception {

        // devuelve LISTADO de cuenta de 1 cliente
        Connection co;
        Statement stm;
        co = MysqlConection.getConnection();
        stm = co.createStatement();
        try {
            String sql = "SELECT s.NOMBRE_SUCURSAL, cli.NOMBRE_APELLIDO , cu.TIPO_CUENTA, CU.NRO_CUENTA, CU.CBU FROM `sucursales` as s inner join sucursal_cuentas as sc on s.NRO_SUCURSAL = sc.NRO_SUCURSAL inner join cuentas as cu on sc.NRO_CUENTA = cu.NRO_CUENTA inner join clientes as cli on cu.NRO_CLIENTE = cli.NRO_CLIENTE where cli.NRO_CLIENTE = " + nroCliente;
           //System.out.println(sql);
            java.sql.ResultSet rs = stm.executeQuery(sql);
            System.out.println("NOMBRE SUCURSAL           CLIENTE           TIPO CUENTA       NRO CUENTA           CBU");
            while (rs.next()) {
                String fila = rs.getString("NOMBRE_SUCURSAL") + "              " + rs.getString("NOMBRE_APELLIDO") + "          " + rs.getString("TIPO_CUENTA") + "             " + rs.getString("NRO_CUENTA") + "              " + rs.getString("CBU") + "              ";
                System.out.println(fila);


            }
            rs.close();
            stm.close();
        } catch (java.sql.SQLException ex) {
            co.rollback();
            throw new BancoException("Error en ejecución en get cuentas clientes " + ex.getMessage());
        } finally {
            co.close();
        }
        return null;
    }

    @Override
    public Cuenta findCuenta(Integer nroCuenta) throws Exception {

        //busca una cuenta y devuelve el objeto
        Connection co;
        Statement stm;
        co = MysqlConection.getConnection();
        stm = co.createStatement();
        Cuenta cu = null;
        try {
            String sql = "SELECT * FROM cuentas  where NRO_CUENTA = " + nroCuenta;
            // System.out.println(sql);
            java.sql.ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                String tipo_cuenta = rs.getString("TIPO_CUENTA");
                Integer nro_cuenta = rs.getInt("NRO_CUENTA");
                String cbu = rs.getString("CBU");
                Double saldo = rs.getDouble("SALDO");
                Integer nro_cliente = rs.getInt("NRO_CLIENTE");

                if (tipo_cuenta == "CC") {
                    cu = new CuentaCorriente(nro_cuenta, saldo, cbu);
                } else {
                    cu = new CajaDeAhorro(nro_cuenta, saldo, cbu, "D");
                }

            }
            rs.close();
            stm.close();
        } catch (java.sql.SQLException ex) {
            co.rollback();
            throw new BancoException("Error en ejecución en get cuentas clientes " + ex.getMessage());
        } finally {
            co.close();
        }
        return cu;
    }

    @Override
    public void actualizarSaldo(Cuenta cuenta) throws Exception {

        Connection co;
        co = MysqlConection.getConnection();
        co.setAutoCommit(false);
        ResultSet rs = null;
        String sql = "UPDATE cuentas  set SALDO = ? where NRO_CUENTA = " + cuenta.getNumCuenta();
        //System.out.println(sql);
        java.sql.PreparedStatement stmt = co.prepareStatement(sql);
        try {
            co.setAutoCommit(false);
            stmt.setDouble(1, cuenta.getSaldo());
            stmt.execute();
            System.out.println("se completo la operación de extracción de saldo ");
            co.commit();

        } catch (java.sql.SQLException ex) {
            co.rollback();
            throw new BancoException("Error actualización de saldo cuenta " + ex.getMessage());
        } finally {
            stmt.close();
        }

    }

    @Override
    public Cuenta findCuentaByCbu(String paramCbu) throws Exception {

        //busca una cuenta por cbu , devuelve el objeto
        Connection co;
        Statement stm;
        co = MysqlConection.getConnection();
        stm = co.createStatement();
        Cuenta cu = null;
        try {
            String sql = "SELECT * FROM cuentas  where CBU = " + paramCbu;
            //System.out.println(sql);
            java.sql.ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                String tipo_cuenta = rs.getString("TIPO_CUENTA");
                Integer nro_cuenta = rs.getInt("NRO_CUENTA");
                String cbu = rs.getString("CBU");
                Double saldo = rs.getDouble("SALDO");
                Integer nro_cliente = rs.getInt("NRO_CLIENTE");

                if (tipo_cuenta == "CC") {
                    cu = new CuentaCorriente(nro_cuenta, saldo, cbu);
                } else {
                    cu = new CajaDeAhorro(nro_cuenta, saldo, cbu, "D");
                }

            }
            rs.close();
            stm.close();
        } catch (java.sql.SQLException ex) {
            co.rollback();
            throw new BancoException("Error en ejecución busqueda find cuenta by cbu " + ex.getMessage());
        } finally {
            co.close();
        }
        return cu;

    }

    @Override
    public List<Cuenta> getCuentasBySucursal(Integer nroSucursal) throws Exception {

        Connection co;
        Statement stm;
        co = MysqlConection.getConnection();
        stm = co.createStatement();
        List<Cuenta> cuentas = new ArrayList<Cuenta>();
        try {
            String sql = "SELECT cu.TIPO_CUENTA, cu.NRO_CUENTA, cu.CBU, cu.SALDO,cu.NRO_CLIENTE FROM sucursal_cuentas as su_cu inner join sucursales as su on su_cu.NRO_SUCURSAL =su.NRO_SUCURSAL inner join cuentas as cu on cu.NRO_CUENTA = su_cu.NRO_CUENTA where su.NRO_SUCURSAL = " + nroSucursal;
            //System.out.println(sql);
            java.sql.ResultSet rs = stm.executeQuery(sql);
            Cuenta cu = null;
            while (rs.next()) {
                String tipo_cuenta = rs.getString("TIPO_CUENTA");
                Integer nro_cuenta = rs.getInt("NRO_CUENTA");
                String cbu = rs.getString("CBU");
                Double saldo = rs.getDouble("SALDO");
                Integer nro_cliente = rs.getInt("NRO_CLIENTE");
                if (tipo_cuenta == "CC") {
                    cu = new CuentaCorriente(nro_cuenta, saldo, cbu);
                } else {
                    cu = new CajaDeAhorro(nro_cuenta, saldo, cbu, "D");
                }
                cuentas.add(cu);
            }
            rs.close();
            stm.close();
        } catch (java.sql.SQLException ex) {
            co.rollback();
            throw new BancoException("Error en ejecución busqueda find cuenta by sucursal " + ex.getMessage());
        } finally {
            co.close();
        }

        return cuentas;
    }
}

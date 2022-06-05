package dao;

import Config.MysqlConection;
import exception.BancoException;
import modelo.Cliente;
import modelo.Cuenta;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.List;


public class ClienteDAO implements IClienteDAO {

    private List<Cliente> clientes;
    private static ClienteDAO instance;

    //3-. Retornar el único objeto disponible a través de un método estático
    public static ClienteDAO getInstance() {
        if (instance == null) {
            instance = new ClienteDAO();
        }
        return instance;
    }

    @Override
    public void addCliente(Cliente cliente) throws Exception {

        Connection co;
        Integer idCliente = 0;
        ResultSet rs = null;
        List<Cuenta> listaCuentas = null;

        co = MysqlConection.getConnection();
        String sql = "INSERT INTO clientes (NRO_CLIENTE,CUIL,NOMBRE_APELLIDO) VALUES (?, ?, ?)";
        java.sql.PreparedStatement stmt = co.prepareStatement(sql);
        try {
            co.setAutoCommit(false);
            stmt = co.prepareStatement(sql, new String[]{"NRO_CLIENTE"});
            stmt.setInt(1, 0);
            stmt.setBigDecimal(2, new BigDecimal(cliente.getCuil()));
            stmt.setString(3, cliente.getNombreApellido());
            stmt.execute();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idCliente = rs.getInt(1);
            }
            listaCuentas = cliente.getCuentas();
            co.commit();
            stmt.close();
        } catch (java.sql.SQLException ex) {
            co.rollback();
            throw new BancoException("Error de inserción en tabla" + ex.getMessage());
        } finally {
            stmt.close();

            //recorro todas las cuentas y las doy de alta con el NRO_CLIENTE
            CuentaDAO cuentaDao = CuentaDAO.getInstance();
            for (Cuenta itCuenta : listaCuentas) {
                cuentaDao.addCuenta(itCuenta, idCliente);
            }
        }
    }

    @Override
    public void deleteCliente(String cuil) {
        // TODO Auto-generated method stub

    }

    @Override
    public void getClientes(String nombreSuc) throws Exception {

        Connection co;
        Statement stm;
        co = MysqlConection.getConnection();
        stm = co.createStatement(); //evita el sql injection :)

        try {
            String where = "";
            if(nombreSuc != ""){
                where = "WHERE s.NOMBRE_SUCURSAL LIKE '%"+nombreSuc+"%'";
            }
            String sql = "SELECT s.NRO_SUCURSAL, s.NOMBRE_SUCURSAL, cli.NOMBRE_APELLIDO,  cu.NRO_CUENTA ,cu.TIPO_CUENTA  FROM `sucursales` as s inner join sucursal_cuentas as sc on s.NRO_SUCURSAL = sc.NRO_SUCURSAL inner join cuentas as cu on sc.NRO_CUENTA = cu.NRO_CUENTA inner join clientes as cli on cu.NRO_CLIENTE = cli.NRO_CLIENTE "+where;
            //System.out.println(sql);
            java.sql.ResultSet rs = stm.executeQuery(sql);
            System.out.println("NRO SUC                  NOMBRE SUCU                  CLIENTE                  NRO-CUENTA          TIPO");
            while (rs.next()) {
                String fila = rs.getString("NRO_SUCURSAL") + "                  " + rs.getString("NOMBRE_SUCURSAL") + "                  " + rs.getString("NOMBRE_APELLIDO")+"                  "+rs.getString("NRO_CUENTA")+"                  "+rs.getString("TIPO_CUENTA");
                System.out.println(fila);

            }
            rs.close();
            stm.close();
        } catch (java.sql.SQLException ex) {
            co.rollback();
            throw new BancoException("Error en ejecución select get clientes" + ex.getMessage());
        } finally {
            co.close();
        }

    }

    @Override
    public Integer getIdCliente(BigInteger cuil) throws Exception {

        Connection conn;
        Integer idCliente = null;
        conn = MysqlConection.getConnection();
        ResultSet rs = null;
        String sql = "select NRO_CLIENTE from clientes where cuil = ?";
        java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
        try {
            conn.setAutoCommit(false);
            stmt.setBigDecimal(1, new BigDecimal(cuil));
            rs = stmt.executeQuery();
            while (rs.next()) {
                idCliente = rs.getInt("NRO_CLIENTE");
            }
            conn.commit();

        } catch (java.sql.SQLException ex) {
            conn.rollback();
            throw new BancoException("Error select en tabla " + ex.getMessage());
        } finally {
            stmt.close();
        }
        return idCliente;
    }

    public void altaCliente(String ayn, BigInteger cuil, BigInteger cbu, Double saldo, String tipoCuenta) {

        Connection co;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String strConect = "jdbc:mysql://localhost:3306/sistemabanco";
            String usuario = "root";
            String pass = "";
            co = DriverManager.getConnection(strConect, usuario, pass);
            Integer idCliente;
            ResultSet rs = null;
            String sql = "INSERT INTO clientes (NRO_CLIENTE,CUIL,NOMBRE_APELLIDO) VALUES (?, ?, ?)";
            java.sql.PreparedStatement stmt = co.prepareStatement(sql);
            try {
                co.setAutoCommit(false);
                stmt = co.prepareStatement(sql, new String[]{"NRO_CLIENTE"});
                stmt.setInt(1, 0);
                stmt.setBigDecimal(2, new BigDecimal(cuil));
                stmt.setString(3, ayn);
                stmt.execute();
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    idCliente = rs.getInt(1);
                    //System.out.println(idCliente);
                }
                co.commit();

            } catch (java.sql.SQLException ex) {
                System.out.println(ex.toString());
                //errores a nivel tabla o inserción
                System.out.println("Error de conexion : " + ex);
                co.rollback();
            } finally {
                stmt.close();
            }

        } catch (SQLException e) {
            // error al conectar a la BD
            System.out.println("Error de conexion : " + e);
        } catch (Exception e) {
            //error mas amplio
            System.out.println("Error desconocido : " + e);
        }


    }


}

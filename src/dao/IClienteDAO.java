package dao;
import modelo.Cliente;

import java.math.BigInteger;

public interface IClienteDAO {

    void addCliente(Cliente cliente) throws Exception;

    void deleteCliente(String cuil);

    void getClientes(String nombreSuc)throws Exception;

    Integer getIdCliente(BigInteger cuil) throws Exception;
}

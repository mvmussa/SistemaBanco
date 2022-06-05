package services;

import constants.TipoCuenta;
import dao.ClienteDAO;
import modelo.Cliente;
import modelo.Cuenta;

import java.math.BigInteger;
import java.util.List;

public interface IClienteService {


    public  void addCliente(Cliente cliente) throws Exception;

    public void deleteCiente(String cuil);

    public void validarFechaNacimiento(String fechaNacimiento);

    public Boolean validarEmail(String email);

    public void getClientes(String nombreSuc) throws Exception;

    public Integer getIdCliente(BigInteger cuil) throws Exception;

}

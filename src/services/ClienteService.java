package services;
import dao.IClienteDAO;
import dao.ClienteDAO;
import exception.BancoException;
import modelo.Cliente;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClienteService implements IClienteService {

    private IClienteDAO clienteDao;
    @Override
    public void addCliente(Cliente cliente) throws Exception {
        if (validarEmail(cliente.getEmail())) {
            clienteDao = ClienteDAO.getInstance();
            clienteDao.addCliente(cliente);

        }else{
            throw new BancoException("Error al ingresar email "+cliente.getEmail());
        }
    }

    public void deleteCiente(String cuil) {
        clienteDao.deleteCliente(cuil);
    }

    public void validarFechaNacimiento(String fechaNacimiento) {
        // TODO: Validar que la edad sea mayor a 18 a�os

    }

    public Boolean validarEmail(String email) {
        // valida formato email
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();

    }

    @Override
    public void getClientes(String nombreSuc) throws Exception {

        clienteDao = ClienteDAO.getInstance();
        clienteDao.getClientes(nombreSuc);
    }

    @Override
    public Integer getIdCliente(BigInteger cuil) throws Exception {

        clienteDao = ClienteDAO.getInstance();
        Integer id = clienteDao.getIdCliente(cuil);
        return id;
    }

 }

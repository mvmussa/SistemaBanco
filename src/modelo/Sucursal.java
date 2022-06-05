package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sucursal {
    // Atributos
    private String nombreSucursal;
    private Integer numSucursal;

    private List<Cliente> listaClientes;
    private List<Empleado> listaEmpleados;

    public Sucursal( Integer numSucursal, String nombreSucursal) {
        /*
        * cuando se instancia crea sus atributos y las listas de clientes y empleados vacia
        * */
        this.nombreSucursal = nombreSucursal;
        this.numSucursal = numSucursal;
        this.listaClientes = new ArrayList<Cliente>();
        this.listaEmpleados = new ArrayList<Empleado>();
    }

    // METODOS

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public void setNumSucursal(Integer numSucursal) {
        this.numSucursal = numSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public int getNumSucursal() {
        return numSucursal;
    }

    public void setNumSucursal(int numSucursal) {
        this.numSucursal = numSucursal;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> clientes) {
        this.listaClientes = clientes;
    }

    /**
     *
     * @param dni
     * @return
     */
    public Empleado obtenerEmpleado(String dni) {
        for (Empleado empleado : listaEmpleados) {
            if (empleado.getDni().equals(dni)) {
                return empleado;
            }

        }
        return null;
    }

    public String listarClientes() {

       //código viejo
       //todo refactorizar
       String resultado= "";
       resultado=  this.listaClientes.stream().map((Cliente c) -> " - El cliente: "+c.getNombreApellido() ).collect(Collectors.joining(", "));
       return resultado;
    }
}

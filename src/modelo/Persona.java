package modelo;

import java.math.BigInteger;

public abstract class Persona {

    // Atributos
    protected String dni;
    protected String nombreApellido;
    protected String telefono;
    private String email;

    private BigInteger cuil;



    // Constructor
    public Persona(String dni, String nombreApellido, String telefono, String email, BigInteger cuil) {
        this.dni = dni;
        this.nombreApellido = nombreApellido;
        this.telefono = telefono;
        this.email = email;
        this.cuil = cuil;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getCuil() {
        return cuil;
    }

    public void setCuil(BigInteger cuil) {
        this.cuil = cuil;
    }

}


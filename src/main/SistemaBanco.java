package main;

import modelo.*;
import services.BancoService;
import services.ClienteService;
import services.CuentaService;
import services.SucursalService;
import java.math.BigInteger;
import java.util.*;


/** OPciones del sistema Bancario
 * 1) Agregar Cliente<br>
 * 2) Agregar cuenta a Cliente<br>
 * 3) Listar Clientes por sucursal<br>
 * 4) Listar Clientes de una sucursal<br>
 * 5) Extraer dinero<br>
 * 6) Consultar Saldo<br>
 * 7) Realizar Deposito<br>
 * 8) Realizar transferencias<br>
 * 9) Eliminar una sucursal<br>
 *
 * @author Mussa Maria Victoria
 */

public class SistemaBanco {
    public static void main(String[] args) {


        inicializarBanco();
        int op;
        System.out.println("***************************************");
        System.out.println("* Operaciones bancarias               *");
        System.out.println("***************************************");
        System.out.println("* 1) Agregar Cliente                  *");
        System.out.println("* 2) Agregar cuenta a Cliente         *");
        System.out.println("* 3) Listar Clientes por sucursal     *");
        System.out.println("* 4) Listar Clientes de una sucursal  *");
        System.out.println("* 5) Extraer dinero                   *");
        System.out.println("* 6) Consultar Saldo                  *");
        System.out.println("* 7) Realizar Deposito                *");
        System.out.println("* 8) Realizar transferencias          *");
        System.out.println("* 9) Eliminar una sucursal            *");
        System.out.println("***************************************");
        op = leerOpcion();

        switch (op) {
            case 1:
                altaCliente();
                break;
            case 2:
                agregarCuentaCliente();
                break;
            case 3:
                listarClientesSucursales("");
                break;
            case 4:
                String nombreSuc = leerCadena("Nombre de sucursal");
                listarClientesSucursales(nombreSuc);
                break;
            case 5:
                //extraer dinero
                listarCuentasCliente();
                break;
            case 6:
                consultarSaldo();
                break;
            case 7:
                depositarDinero();
                break;
            case 8:
                realizarTranferencia();
                break;
            case 9:
                borrarSucursal();
                break;
            default:
                listarClientesSucursales("");
                break;
        }
    }

    /**
     * Métodos menú principal
     **/


    /**
     * Pide que ingrese por teclado los datos del nuevo cliente a dar de alta
     * con una cuenta como mínimo
     *
     * @return Cliente
     */
    private static Cliente pedirDatosCliente() {

        // datos personales
        String nombreCompleto = leerCadena("Nombre Completo");
        String dni = leerCadena("DNI (sin puntos)");
        BigInteger cuil = leerNumeroGrande("CUIL");
        String telefono = leerCadena("Teléfono");
        String email = leerCadena("E-mail");
        String domicilio = leerCadena("Domicilio");

        //datos cuenta
        String cbu = leerCadena("CBU (solo números)");
        Double saldo = 0.0;

        List<Cuenta> cuentas = new ArrayList<>();
        boolean crearOtraCuenta= true;

        /* utilice un do while por que como mínimo se hace una cuenta */
        do{
            String tipoCuenta = leerCadena("tipo cuenta CC|CA  (Cuenta Corriente o Caja Ahorro) ");
            if (tipoCuenta.equals("CA")) {
                Cuenta cajaDeAhorro = new CajaDeAhorro(0, saldo, cbu, "P");
                cuentas.add(cajaDeAhorro);

            } else {
                Cuenta cuentaCorriente = new CuentaCorriente(0, saldo, cbu);
                cuentas.add(cuentaCorriente);
            }
            String respuesta = leerCadena("Desea crear una nueva cuenta S/N");
            if(respuesta.equals("N")) {
                crearOtraCuenta= false;
            }
        }while(crearOtraCuenta);
        Date date = new Date();
        return  new Cliente(dni, nombreCompleto, telefono, email, domicilio, cuentas, date, cuil);

    }

    /**
     * Da de alta un nuevo Cliente
     **/

    private static void altaCliente() {

        Cliente cliente =pedirDatosCliente();
        try {
            ClienteService servicioCliente = new ClienteService();
            servicioCliente.addCliente(cliente);
            System.out.println("Se ha dado de alta al cliente correctamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Borrar una sucursal: verifica que exista otra sucuarsal auxiliar para
     * trasladar todos los clientes con sus cuentas, luegopodría borrar la sucursal
     */
     private static void borrarSucursal() {

        Integer nroSucursal = 1;
        try {
            SucursalService servicioCucursal = new SucursalService();
            Integer nroSuc= leerNumero("Sucursar a borrar");
            servicioCucursal.borrarSucursal(nroSuc);
            System.out.println("Se Elimino la sucursal con Exito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    /**
     * Realiza la tranferencia del monto de una cuenta origen a una cuenta destino si es que el monto es igual
     * al saldo a tranferir
    **/
    private static void realizarTranferencia() {

        System.out.println("Para poder tranferir dinero le pediremos los siguientas datos:");
        String cbu_destino = leerCadena("CBU ORIGEN");
        String cbu_origen = leerCadena("CBU DESTINO");
        Double saldo = leerSaldo("DINERO A TRANFERIR");
        try {
            CuentaService servicioCuenta = new CuentaService();
            servicioCuenta.TranferirSaldo(cbu_origen, cbu_destino, saldo);
            System.out.println("Tranferencia realizada con Exito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Depositar Dinero en una cuenta usando el cbu de la cuenta de origen y destino
     */

    private static void depositarDinero() {

        try {
            System.out.println("Para depositar dinero le pediremos algunos datos:");
            BigInteger cuil = leerNumeroGrande("Cuil Destino ");
            ClienteService servicioCliente = new ClienteService();
            Integer id = servicioCliente.getIdCliente(cuil);

            CuentaService servicioCuenta = new CuentaService();
            servicioCuenta.getCuentasCliente(id);

            System.out.println("Debe elegir el Nro de cuenta a la cual depositar");
            Integer nroCuenta = leerNumero("Nro Cuenta Destino");
            Double saldo = leerSaldo("Saldo a Depositar");
            servicioCuenta.depositarSaldo(nroCuenta, saldo);
            System.out.println("Se realizó el deposito con Exito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Consulta el saldo de una cuenta específica
     *
     */
    private static void consultarSaldo() {

        try {
            System.out.println("Para poder consultar el saldo de una cuenta  le pediremos los siguientes datos del Cliente:");
            BigInteger cuil = leerNumeroGrande("Cuil");

            ClienteService servicioCliente = new ClienteService();
            Integer id = servicioCliente.getIdCliente(cuil);

            CuentaService servicioCuenta = new CuentaService();
            servicioCuenta.getCuentasCliente(id);

            System.out.println("Ingrese el Nro de cuenta a la cual consultar");
            Integer nroCuenta = leerNumero("Nro Cuenta");
            servicioCuenta.consultarSaldo(nroCuenta);
            System.out.println("Se realizo la operación con Exito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Muestra por pantalla las cuentas asociadas a un cliente
     */
    private static void listarCuentasCliente() {

        try {
            System.out.println("Para poder extraer dinero le pediremos los siguientes datos:");
            BigInteger cuil = leerNumeroGrande("Cuil");
            ClienteService servicioCliente = new ClienteService();
            Integer id = servicioCliente.getIdCliente(cuil);

            CuentaService servicioCuenta = new CuentaService();
            servicioCuenta.getCuentasCliente(id);

            System.out.println("Ingrese un número de cuenta del listado, a la cual quiere extraer dinero");
            Integer nroCuenta = leerNumero("Nro Cuenta");
            System.out.println("Ahora le vamos a pedir el valor en pesos (sin signo pesos y sin puntos)");
            Double saldo = leerSaldo("Saldo");
            servicioCuenta.extraerSaldo(nroCuenta, saldo);
            System.out.println("La extracción fue realizada con Exito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Agrega cuenta a un cliente existente
     */
    private static void agregarCuentaCliente() {

        try {
            System.out.println("Para agregar una cuenta al CLiente necesitamos Cuil");
            BigInteger cuil = leerNumeroGrande("Cuil");

            ClienteService servicioCliente = new ClienteService();
            Integer id = servicioCliente.getIdCliente(cuil);

            //todo cbu armarlo segun la sucursal y demas digitos
            String cbu = leerCadena("CBU");
            String tipoCuenta = leerCadena("Tipo Cuenta a crear CC | CA");
            CuentaService servicioCuenta = new CuentaService();
            if (tipoCuenta.equals("CC") ) {
                Cuenta cuentaCorriente = new CuentaCorriente(0, 0.0, cbu);
                servicioCuenta.addCuenta(id, cuentaCorriente);
                System.out.println("se agrego correctamente la cuenta Corriente");

            } else {
                Cuenta cajaAhorro = new CajaDeAhorro(0, 0.0, cbu, "P");
                servicioCuenta.addCuenta(id, cajaAhorro);
                System.out.println("se agrego correctamente la Caja de Ahorro");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listarClientesSucursales(String nombreSuc) {

        //si nombreSuc esta vacio lista todas las sucursales en tal caso busca por coincidencia de nombre
        ClienteService servicioCliente = new ClienteService();
        try {
            servicioCliente.getClientes(nombreSuc);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Inicialice el banco con 2 sucursales en la bd si es que no existen dos sucursales
     * @preturn void
     * */
    private static void inicializarBanco() {

        try {
            BancoService servicioBanco =new BancoService();
            servicioBanco.verificarSucursal();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     *  se usa para ingresar datos como nombreCompleto , cbu , o cualquier dato de tipo String,
     *  label pide la información a ingresar por el usuario
     *  @param label -título del campo a ingresar
     *  @return String
     */
    public static String leerCadena(String label) {

        Scanner input = new Scanner(System.in);
        boolean repetir ;
        String strLeido = "";
        do {
            repetir = false;
            try {
                System.out.println("Ingresar texto para " + label);
                strLeido = input.nextLine();
                if (strLeido.equals("")) {
                    System.out.println("No se permiten ingresar blancos");
                    repetir = true;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                repetir = true;
            }
        } while (repetir);

        return strLeido;
    }

    /**
     *  se usa para ingresar del tipo número ej: nro_sucursal
     *  label pide la información a ingresar por el usuario
     *  @param label -título del campo a ingresar
     *  @return int
     */
    public static int leerNumero(String label) {

        Scanner input = new Scanner(System.in);
        boolean repetir;
        int num = 0;
        do {
            repetir = false;
            try {
                System.out.println("Ingresar valores para " + label);
                num = input.nextInt();
                if (num <= 0) {
                    throw new ArithmeticException("No es un valor permitido < 0");
                }
            } catch (InputMismatchException e) {
                System.out.println("Valor numérico no válido " + e);
                input.nextLine();
                repetir = true;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                repetir = true;
            }
        } while (repetir);

        return num;
    }

    /**
     *  se usa para ingresar  valores del tipo moneda
     *  label pide la información a ingresar por el usuario
     *  @param label -título del campo a ingresar
     *  @return Double
     */
    public static Double leerSaldo(String label) {
        //se usa para dinero
        Scanner input = new Scanner(System.in);
        boolean repetir;
        double num = 0.0;
        do {
            repetir = false;
            try {
                System.out.println("Ingresar valores para " + label);
                num = input.nextDouble();
                if (num <= 0.0) {
                    throw new ArithmeticException("No es un valor permitido < 0 para cargar dinero ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Valor numérico no válido " + e.getMessage());
                input.nextDouble();
                repetir = true;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                repetir = true;
            }
        } while (repetir);

        return num;
    }

    /**
     *  se usa para ingresar  valores del tipo CUIL
     *  label pide la información a ingresar por el usuario
     *  @param label -título del campo a ingresar
     *  @return BigInteger
     */
    public static BigInteger leerNumeroGrande(String label) {

        Scanner input = new Scanner(System.in);
        boolean repetir ;
        BigInteger num = new BigInteger("0");
        do {
            repetir = false;
            try {
                System.out.println("Ingresar valores para " + label);
                num = input.nextBigInteger();
            } catch (InputMismatchException e) {
                System.out.println("Valor numérico no válido " + e.getMessage());
                input.nextBigInteger();
                repetir = true;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                repetir = true;
            }
        } while (repetir);

        return num;
    }

    /**
     *  se usa para ingresar  valores entre 0 y 9
     *  label pide la información a ingresar por el usuario
     *  @return int
     */
    public static int leerOpcion() {

        Scanner input = new Scanner(System.in);
        boolean repetir;
        int num = 0;
        do {
            repetir = false;
            try {
                System.out.println("Ingresar una opción: ");
                num = input.nextInt();
                if (num <= 0) {
                    throw new ArithmeticException("No es una opción válida ");
                }
            } catch (InputMismatchException e) {
                //captura el error si no es un numero ej:letra
                System.out.println("Valor no válido para opción" + e.getMessage());
                input.nextLine();
                repetir = true;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                repetir = true;
            }
        } while (repetir);

        return num;
    }

}

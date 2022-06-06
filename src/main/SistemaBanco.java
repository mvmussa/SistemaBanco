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
                //todo agregar cuenta a Sucursal
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

    private static Cliente pedirDatosCliente() {

        String nombreCompleto = leerCadena("Nombre Completo");
        String dni = leerCadena("DNI (sin puntos)");
        BigInteger cuil = leerNumeroGrande("CUIL");

        /*datos cuenta*/
        String cbu = leerCadena("CBU (solo números)");
        Double saldo = 0.0;

        String telefono = "155792534";
        String email = "victoria.mussa@gmail.com";
        String domicilio = "calle 45 n 1129";

        //TODO le permito crear CA y CC o una sola

        List<Cuenta> cuentas = new ArrayList<>();
        boolean crearOtraCuenta= true;

        /* utilice un do while por que como mínimo se hace una cuenta */
        do{
            String tipoCuenta = leerCadena("tipo cuenta CC|CA  (Cuenta Corriente o Caja Ahorro) ");
            if (tipoCuenta.equals("CA")) {
                //Cuenta cajaDeAhorro = new CajaDeAhorro(0, saldo, "0000193", "P");
                Cuenta cajaDeAhorro = new CajaDeAhorro(0, saldo, cbu, "P");
                cuentas.add(cajaDeAhorro);

            } else {
                //Cuenta cuentaCorriente = new CuentaCorriente(0, saldo, "0000194");
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

    private static void altaCliente() {

        //Da de alta 1 cliente con 1 o más cuentas (lista de cuentas)
        //pido datos cliente  y cuenta a crear
        Cliente cliente = pedirDatosCliente();
        try {
            ClienteService servicioCliente = new ClienteService();
            servicioCliente.addCliente(cliente);
            System.out.println("Se ha dado de alta al cliente correctamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private static void borrarSucursal() {

        Integer nroSucursal = 1;
        //todo borrar sucursal

        //busco sucursal objeto con el numero de la sucursal
        //si hay mas de 2 sucursales o igual a 2 puedo borrar

        //select con sucursal_cuentas y actualizo toda lista esa
        try {
            SucursalService servicioCucursal = new SucursalService();
            Integer nroSuc= leerNumero("Sucursar a borrar");
            servicioCucursal.borrarSucursal(nroSuc);
            System.out.println("Se Elimino la sucursal con Exito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    private static void realizarTranferencia() {

        /*una cuenta tranfiere un monto a otra cuenta
        nro cuenta origen
        saldo a tranferir
        nro cuenta destino */

        //PRUEBA
        //Double saldo = 5.0;
        //String cbu_origen = "192";
        //String cbu_destino = "193";

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

    private static void depositarDinero() {

        try {
            System.out.println("Para depositar dinero le pediremos algunos datos:");
            //BigInteger cuil = new BigInteger("23299946134");
            BigInteger cuil = leerNumeroGrande("Cuil Destino ");
            ClienteService servicioCliente = new ClienteService();
            Integer id = servicioCliente.getIdCliente(cuil);

            CuentaService servicioCuenta = new CuentaService();
            servicioCuenta.getCuentasCliente(id);

            System.out.println("Debe elegir el Nro de cuenta a la cual depositar");
            //Integer nroCuenta = 30;
            Integer nroCuenta = leerNumero("Nro Cuenta Destino");
            //Double saldo = 100.0;
            Double saldo = leerSaldo("Saldo a Depositar");
            servicioCuenta.depositarSaldo(nroCuenta, saldo);
            System.out.println("Se realizó el deposito con Exito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void consultarSaldo() {

        //Muestro para un ID cliente sus cuentas  (formato listado )
        try {
            //BigInteger cuil = new BigInteger("23299946134");
            System.out.println("Para poder consultar el saldo de una cuenta  le pediremos los siguientes datos del Cliente:");
            BigInteger cuil = leerNumeroGrande("Cuil");

            ClienteService servicioCliente = new ClienteService();
            Integer id = servicioCliente.getIdCliente(cuil);

            CuentaService servicioCuenta = new CuentaService();
            servicioCuenta.getCuentasCliente(id);

            //TODO consultar saldo
            //Integer nroCuenta = 30;
            System.out.println("Ingrese el Nro de cuenta a la cual consultar");
            Integer nroCuenta = leerNumero("Nro Cuenta");
            servicioCuenta.consultarSaldo(nroCuenta);
            System.out.println("Se realizo la operación con Exito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listarCuentasCliente() {

        //Muestro para un ID cliente sus cuentas  (formato listado )

        try {
            System.out.println("Para poder extraer dinero le pediremos los siguientes datos:");
            //BigInteger cuil = new BigInteger("23299946134");
            BigInteger cuil = leerNumeroGrande("Cuil");
            ClienteService servicioCliente = new ClienteService();
            Integer id = servicioCliente.getIdCliente(cuil);

            CuentaService servicioCuenta = new CuentaService();
            servicioCuenta.getCuentasCliente(id);

            //Integer nroCuenta = 25;
            //Double saldo = 10.0;
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

    private static void agregarCuentaCliente() {

        //se agrega una nueva cuenta a un cliente existente
        //se pido el cuil 23299946111 y traigo el id cliente
        //le pregunto que tipo de cuenta quiere y la creo

        try {
            //BigInteger cuil = new BigInteger("23299946134");
            System.out.println("Para agregar una cuenta al CLiente necesitamos Cuil");
            BigInteger cuil = leerNumeroGrande("Cuil");

            ClienteService servicioCliente = new ClienteService();
            Integer id = servicioCliente.getIdCliente(cuil);

            //todo cbu armarlo segun la sucursal y demas digitos
            //String cbu = "0000191";
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

    private static void inicializarBanco() {

        /* Inicialice el banco con dos sucursales en la bd */

       /* todo este código quedo sin uso
          todo REFACTORIZAR function inicializar
          Creamos el banco y le creamos 2 sucursal como mínimo
          Si tuvieramos la Base de datos se realizaria la carga de datos en la base y no debería invocar cada
          vez que se ejecuta el SistemaBanco * */

        BancoService servicioBanco = new BancoService(new Banco(1, "Banco Provincia"));
        Sucursal sucursalInicial = new Sucursal(1, "Suc 1");

        //creo una sucursal como mínimo
        servicioBanco.registrarSucursal(sucursalInicial);
        System.out.println("se creo el banco --->" + servicioBanco.getMiBanco().getNombreBanco());
        System.out.println("se agrego la sucursal  --->" + servicioBanco.getMiBanco().getSucursales().get(0).getNombreSucursal());

        /*
         *   FIN  inicialización
         * */
    }

    public static String leerCadena(String label) {

        //se usa para nombreCompleto , cbu ...
        //label es lo que necesito que ingrese
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
                //captura el error si no es un numero ej:letra
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
                //captura el error si no es un numero ej:letra
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
                //captura el error si no es un numero ej:letra
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

package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteCajero {

    InetAddress direccion;
    Socket servidor;
    int puerto = 5555;
    int idCliente = 0;
    boolean salir = false;
    boolean conectado = false;

    DataInputStream datoEntrada = null;
    DataOutputStream datoSalida = null;

    public void iniciarCliente() throws IOException {

        comprobarDir();

        menu();

    }

    private void conectarServidor() {
        try {
            // Me conecto
            servidor = new Socket(direccion, puerto);
            datoEntrada = new DataInputStream(servidor.getInputStream());
            datoSalida = new DataOutputStream(servidor.getOutputStream());
            System.out.println("Cliente->Conectado al servidor...");
            this.conectado = true;
        } catch (IOException ex) {
            System.err.println("Cliente->ERROR: Al conectar al servidor " + ex.getMessage());
            conectado = false;
            salir = true;
            System.exit(-1);
        }
    }

    private void desconectarServidor() {
        try {
            servidor.close();
            datoEntrada.close();
            datoSalida.close();
            System.out.println("Cliente->Desconectado");
            conectado = false;
            salir = true;
        } catch (IOException ex) {
            System.err.println("Cliente->ERROR: Al desconectar al servidor " + ex.getMessage());
            conectado = false;
            salir = true;
            System.exit(-1);
        }
    }

    private void comprobarDir() {
        try {

            direccion = InetAddress.getLocalHost(); // dirección local (localhost)
        } catch (UnknownHostException ex) {
            System.err.println("Cliente->ERROR: No encuetra dirección del servidor " + ex.getMessage());
            conectado = false;
            salir = true;
            System.exit(-1);
        }
    }

    private void menu() throws IOException {
        System.out.println("Bienvenido a tu Cajero de PSP");
        System.out.println("1.- Identificate");
        System.out.println("2.- Sacar dinero");
        System.out.println("3.- Ingresar dinero");
        System.out.println("4.- Consultar saldo");
        System.out.println("5.- Salir");

        int opcion;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("Indique opción: ");
            opcion = sc.nextInt();
        } while (opcion < 1 || opcion > 7);

        realizarConsulta(opcion);

    }


    private void realizarConsulta(int opcion) throws IOException {

        switch (opcion){
            case 1:
                identificarUsuario();
                break;
            case 2: 
                sacarDinero();
                break;
            case 3:
                ingresarDinero();
                break;
            case 4:
                consultarDinero();
                break;
            case 5:
                System.exit(0);
                //desconectarServidor();
                break;
        }

    }

    private void consultarDinero() throws IOException {
        conectarServidor();
        datoSalida.writeInt(4);
        System.out.println("Su saldo actual es de: ");

        volverAlMenu();

    }

    private void ingresarDinero() throws IOException {
        conectarServidor();
        datoSalida.writeInt(3);
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Cuanto dinero desea ingresar?");
        double dineroMeter = sc.nextDouble();

        datoSalida.writeDouble(dineroMeter);

        System.out.println("Peticion de dinero a meter mandada");

        volverAlMenu();

    }

    private void sacarDinero() throws IOException {
        conectarServidor();
        datoSalida.writeInt(2);
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Cuanto dinero desea sacar?");
        double dineroSacar = sc.nextDouble();

        datoSalida.writeDouble(dineroSacar);

        System.out.println("Peticion de dinero mandada");

        volverAlMenu();


    }

    private void identificarUsuario() throws IOException {
        conectarServidor();
        datoSalida.writeInt(1);
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca su email:");
        String email = sc.nextLine();
        //System.out.println("Introduzca su password:");
        //String password = Utilidad.nuevaInstancia().convertirSHA256(sc.nextLine().toString()); // Ciframos

        datoSalida.writeUTF(email);

        System.out.println("Email y password mandadas");

        volverAlMenu();


    }

    private void volverAlMenu() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println(" ");
        System.out.println("¿Deseas realizar mas consultas?");
        System.out.println("1.- SI  2.- NO");
        int volver = sc.nextInt();

        if(volver==1){
            menu();
        }else if (volver==2){
            System.exit(0);
        }else
            System.out.println("Has introducido mal las opciones");
    }

}









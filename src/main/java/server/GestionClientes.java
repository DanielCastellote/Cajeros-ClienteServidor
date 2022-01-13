package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GestionClientes extends Thread {

    private Socket cliente;
    private DataInputStream datoEntrada = null;
    private DataOutputStream datoSalida = null;
    private boolean salir = false;

    public GestionClientes(Socket cliente) {
        this.cliente = cliente;

    }

    @Override
    public void run() {

        if (salir == false) {
            crearFlujosES();
            tratarConexion();
            cerrarFlujosES();
        } else {
            this.interrupt();
        }
    }


    private void crearFlujosES() {
        try {
            datoEntrada = new DataInputStream(cliente.getInputStream());
            datoSalida = new DataOutputStream(cliente.getOutputStream());
        } catch (IOException ex) {
            System.err.println("ServidorGC->ERROR: crear flujos de entrada y salida " + ex.getMessage());
            salir = true;
        }
    }

    private void cerrarFlujosES() {

        try {

            datoEntrada.close();
            datoSalida.close();

        } catch (IOException ex) {
            System.err.println("ServidorGC->ERROR: cerrar flujos de entrada y salida " + ex.getMessage());

        }
    }

    private void tratarConexion() {

        try {

            int opcion = datoEntrada.readInt();
            switch (opcion) {
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
                    consultarSaldo();
                    break;
                case 5:
                    salirServicio();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void salirServicio() {
        System.out.println("El cliente se ha salido del servicio");

    }

    private void consultarSaldo() {
        System.out.println("El cliente conectado quiere consultar su saldo");



    }

    private void ingresarDinero() throws IOException {
        System.out.println("El cliente conectado quiere ingresar dinero");

        double dineroIngresado = datoEntrada.readDouble();

        System.out.println("Ingresando dinero al saldo para el cliente...");
        System.out.println("Usted ha ingresado con exito "+ dineroIngresado);

    }

    private void sacarDinero() throws IOException {
        System.out.println("El cliente conectado quiere sacar dinero");

        double dineroSacado = datoEntrada.readDouble();

        System.out.println("Descontando dinero del saldo del cliente...");
        System.out.println("Usted ha sacado con exito "+ dineroSacado);

    }

    private void identificarUsuario() throws IOException {
        System.out.println("El cliente conectado se va a identificar");

        String email = datoEntrada.readUTF();

        System.out.println("Verificando email y contraseña...");

    }

}
package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket servidor = null;
        Socket cliente;
        int puerto = 5555;

        System.out.println("Arrancando el cajero automático");

        try {
            servidor = new ServerSocket(puerto);
            while (true) {
                System.out.println("Esperando usuarios...");
                System.out.println(" ");
                cliente = servidor.accept();

                System.out.println("Peticion de cliente -> " + cliente.getInetAddress() + " --- " + cliente.getPort());

                GestionClientes gc = new GestionClientes(cliente);
                gc.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            servidor.close();
        }
    }
    }


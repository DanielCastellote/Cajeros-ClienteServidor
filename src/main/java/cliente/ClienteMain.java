package cliente;

import java.io.IOException;

public class ClienteMain {
    public static void main(String[] args) throws IOException {
        ClienteCajero cliente = new ClienteCajero();

        cliente.iniciarCliente();
    }
}

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

// Server class
class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);

                Thread t = new Thread(clientHandler);
                t.start();
            }
        } catch (IOException io) {
            closeServer();
        }
    }
    public void closeServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "Error closing server.");
        }
    }


    // ClientHandler class
    private static class ClientHandler implements Runnable {
        public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
        private Socket clientSocket;
        private BufferedReader reader;
        private PrintWriter writer;

        // Constructor
        public ClientHandler(Socket socket) throws IOException {
            this.clientSocket = socket;
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
            clientHandlers.add(this);
        }

        public void run() {
            try {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException io) {
                JOptionPane.showMessageDialog(null, "Error running ClientHandler.");
            }
        }
    }


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            Server server = new Server(serverSocket);
            server.startServer();
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "Error starting server");
        }
    }
}

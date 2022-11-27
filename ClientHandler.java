import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    }
    @Override
    public void run() {

    }

    {

}
}

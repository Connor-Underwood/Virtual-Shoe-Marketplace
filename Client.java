import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.*;

// Client class
public class Client implements Runnable{
    private PrintWriter writer;
    private BufferedReader reader;
    // find some way to differentiate clients in server

    @Override
    public void run() {
        
    }

    //public static void main(String[] args) {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            
            // writing to server
            PrintWriter writer = new PrintWriter(
                    socket.getOutputStream(), true);

            // reading from server
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));


            // request format to send to server: "Login: {username} {password}"

            

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
        // establish a connection by providing host and port
        // number




}

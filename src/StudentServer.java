import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.*;

public class StudentServer {
    private ObjectOutputStream outputToFile;
    private ObjectInputStream inputFromClient;

    public static void main(String[] args) {
        new StudentServer();
    }

    public StudentServer() {
        try {
            // create server socket
            ServerSocket serverSocket = new ServerSocket(8675);
            System.out.print("Server Started\n");

            // create object output stream
            outputToFile = new ObjectOutputStream(new FileOutputStream("student.dat", true));

            while (true) {
                // listen for client connections
                Socket socket = serverSocket.accept();

                // create input stream from socket
                inputFromClient = new ObjectInputStream(socket.getInputStream());

                // read from input
                Object object = inputFromClient.readObject();

                // write to the file
                outputToFile.writeObject(object);
                System.out.println("A new student object is stored");
                StudentAddress test = (StudentAddress)object;
                System.out.println(test.getName());
            }
        }
        catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        catch (IOException ex) {
            ex.printStackTrace();
        }

        finally {
            try {
                inputFromClient.close();
                outputToFile.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
}

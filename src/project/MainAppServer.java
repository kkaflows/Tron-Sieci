package project;

import project.model.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Lenovo on 2017-05-11.
 */
public class MainAppServer {

    static ServerSocket serverSocket;
    static Socket socket;
    static ArrayList<Socket> socketList;



    public static void main(String[] args) {


        try {
            serverSocket = new ServerSocket(4444);

        } catch (IOException e) {
            e.printStackTrace();
        }
        socketList = new ArrayList<Socket>();

        while (true){
            try {
                socket = null;
                socket = serverSocket.accept();
                System.out.println("Established connection");
                socketList.add(socket);
                ServerThread serverThread = new ServerThread(socket);

                serverThread.start();

            }catch(IOException e){
                e.printStackTrace();
            }

        }
    }



}


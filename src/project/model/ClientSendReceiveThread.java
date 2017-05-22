package project.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import project.view.ClientController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0.runnable;

/**
 * Created by Lenovo on 2017-05-16.
 */
public class ClientSendReceiveThread extends Thread {

    String name;
    ClientController controller;
    static boolean isRunning = true;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private boolean[][] board = new boolean[400][400];
    private DataPackage dataPackage;
    private GraphicsContext graphicsContext;
    private static int score = 0;


    public ClientSendReceiveThread(String name, ClientController controller) {
        this.name = name;
        this.controller = controller;
    }

    public ClientSendReceiveThread(String name, ClientController controller, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.name = name;
        this.controller = controller;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        graphicsContext = controller.canvas.getGraphicsContext2D();
    }

    @Override
    public void run() {
        if (name == "send") {
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (isRunning) {
                controller.move();
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (name == "receive") {
            while (true) {
                while (isRunning) {
                    Object o = null;
                    try {
                        o = objectInputStream.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (o instanceof DataPackage) {
                        dataPackage = (DataPackage) o;
                        boolean endRound = isWinner();
                        board[dataPackage.getX()][dataPackage.getY()] = true;
                        //graphicsContext.setFill(Color.BLUE);
                        graphicsContext.fillOval(dataPackage.getX(), dataPackage.getY(), 5, 5);

                        if (endRound == true) {
                            score += 1;
                            String tmp = "" + score;
                            controller.textField.setText(tmp);
                            graphicsContext.clearRect(5, 5, 390, 390);
                        }
                        dataPackage = null;
                    }
                }
                clearBoard();
                isRunning = true;
            }
        }
    }

    private boolean isWinner() {
        if (board[dataPackage.getX()][dataPackage.getY()] == true) {
            isRunning = false;
            return true;
        } else if (dataPackage.getX() > 395 || dataPackage.getX() < 5) {
            isRunning = false;
            return true;
        } else if (dataPackage.getY() > 395 || dataPackage.getY() < 5) {
            isRunning = false;

            return true;
        } else
            return false;
    }

    private void clearBoard() {
        for (int i = 0; i < 400; i++) {
            for (int j = 0; j < 400; j++) {
                board[i][j] = false;
            }
        }
    }
}


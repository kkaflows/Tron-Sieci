package project.model;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import project.MainAppServer;
import project.view.ClientController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.List;

/**
 * Created by Lenovo on 2017-05-11.
 */
public class ClientReceiveThread extends Thread {

    private String msg;
    private DataPackage dataPackage;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private GraphicsContext graphicsContext;
    private BufferedImage bufferedImage;
    private ClientController controller;
    private boolean[][] board = new boolean[400][400];




    public ClientReceiveThread(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, GraphicsContext graphicsContext) {
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
        this.graphicsContext = graphicsContext;


    }

    @Override
    public void run() {




        System.out.println("New ClientReceiveThread");
        //bufferedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);

        while (true) {
            try {
                Object o = objectInputStream.readObject();
                if (o instanceof DataPackage) {
                    dataPackage = (DataPackage) o;

                    if(isLooser()) {
                        Thread.sleep(200000);
                    }
                    board[dataPackage.getX()][dataPackage.getY()] = true;
//                      System.out.println(board[dataPackage.getX()][dataPackage.getY()]);
//                    bufferedImage.setRGB(dataPackage.getX(), dataPackage.getY(), Color.RED.getRGB());
//                    graphicsContext.drawImage(SwingFXUtils.toFXImage(bufferedImage, null), 0, 0);


                    graphicsContext.fillOval(dataPackage.getX(), dataPackage.getY(), 5, 5);

                    dataPackage = null;
                }
                if (o instanceof String) {
                    msg = (String) o;
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    private boolean isLooser() {
        if (board[dataPackage.getX()][dataPackage.getY()] == true) {
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("You lost");
//                    alert.setHeaderText("You lost!");
//                    alert.setContentText("You lost!");
//                    alert.showAndWait();
//                }
//            });
            return true;
        }
        return false;

    }



}

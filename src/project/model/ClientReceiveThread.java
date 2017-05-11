package project.model;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import project.view.ClientController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

    public ClientReceiveThread(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, GraphicsContext graphicsContext) {
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
        this.graphicsContext = graphicsContext;
    }

    public ClientReceiveThread(DataPackage dataPackage, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, ClientController controller) {
        this.dataPackage = dataPackage;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
        this.controller = controller;
    }

    @Override
    public void run() {
        System.out.println("New ClientReceiveThread");
        bufferedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);

        while (true) {
            try {
                Object o = objectInputStream.readObject();
                if (o instanceof DataPackage) {
                    dataPackage = (DataPackage) o;
                    System.out.println(dataPackage.getX());
//                    bufferedImage.setRGB(dataPackage.getX(), dataPackage.getY(), Color.RED.getRGB());
//                    graphicsContext.drawImage(SwingFXUtils.toFXImage(bufferedImage, null), 0, 0);
                    graphicsContext.fillOval(dataPackage.getX(), dataPackage.getY(), 5,5);
                    dataPackage = null;
                }
                if (o instanceof String) {
                    msg = (String) o;

                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }
}

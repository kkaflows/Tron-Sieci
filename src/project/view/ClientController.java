package project.view;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import project.MainAppClient;
import project.model.DataPackage;

import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Lenovo on 2017-05-11.
 */
public class ClientController {

    public DataPackage dataPackage;
    public MainAppClient mainAppClient;
    public Socket socket;
    public ObjectInputStream objectInputStream;
    public ObjectOutputStream objectOutputStream;
    public GraphicsContext graphicsContext;
    public BufferedImage bufferedImage;


    public void setMainAppClient(MainAppClient mainAppClient) {
        this.mainAppClient = mainAppClient;
    }

    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {

        try {
            socket = new Socket("localhost", 4444);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());


        } catch (IOException e) {
            e.printStackTrace();
        }
        dataPackage = new DataPackage(0, 200, 1, 0);
        bufferedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        graphicsContext = canvas.getGraphicsContext2D();
    }

    @FXML
    private void handleButtonUp() {
        dataPackage.goUp();

        dataPackage.nextStep();
        bufferedImage.setRGB(dataPackage.getX(), dataPackage.getY(), Color.YELLOW.getRGB());
        graphicsContext.drawImage(SwingFXUtils.toFXImage(bufferedImage, null), 0, 0);
    }

    @FXML
    private void handleButtonRight() {
        dataPackage.goRight();

        dataPackage.nextStep();
        bufferedImage.setRGB(dataPackage.getX(), dataPackage.getY(), Color.YELLOW.getRGB());
        graphicsContext.drawImage(SwingFXUtils.toFXImage(bufferedImage, null), 0, 0);
    }

    @FXML
    private void handleButtonDown() {
        dataPackage.goDown();

        dataPackage.nextStep();
        bufferedImage.setRGB(dataPackage.getX(), dataPackage.getY(), Color.YELLOW.getRGB());
        graphicsContext.drawImage(SwingFXUtils.toFXImage(bufferedImage, null), 0, 0);
    }

    @FXML
    private void handleButtonLeft() {
        dataPackage.goLeft();

        dataPackage.nextStep();
        bufferedImage.setRGB(dataPackage.getX(), dataPackage.getY(), Color.YELLOW.getRGB());
        graphicsContext.drawImage(SwingFXUtils.toFXImage(bufferedImage, null), 0, 0);
    }

    @FXML
    private void handleButtonStart(){
        try {
            objectOutputStream.writeObject("start");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread move = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    move();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        move.start();
    }




    public void move() {
        if (dataPackage.getDirX() == 1)
            handleButtonRight();
        if (dataPackage.getDirX() == -1)
            handleButtonLeft();
        if (dataPackage.getDirY() == 1)
            handleButtonDown();
        if (dataPackage.getDirY() == -1)
            handleButtonUp();

        dataPackage.setBoard(dataPackage.getX(), dataPackage.getY());

        try {
            DataPackage tmp = new DataPackage(dataPackage.getX(), dataPackage.getY(),dataPackage.getDirX(),dataPackage.getDirY());
            System.out.println(dataPackage.getX());
            objectOutputStream.writeObject(tmp);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package org.example;
import java.util.Scanner;
import com.fazecast.jSerialComm.*;

public class Main {
    public static void main(String[] args) throws SerialPortInvalidPortException {

        System.out.println("Hello world!");

        // Liste des ports disponibles
        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println("Available ports:");
        for (int i = 0; i < ports.length; i++) {
            System.out.println(i + ": " + ports[i].getSystemPortName());
        }

        SerialPort serialPort = SerialPort.getCommPort("COM10");
        serialPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        if (!serialPort.openPort()) {
            System.out.println("Unable to open port.");
            return;
        }
        System.out.println("Port opened successfully.");

        // Lire et écrire des données avec Arduino
        Scanner scanner = new Scanner(serialPort.getInputStream());
        new Thread(() -> {
            while (scanner.hasNextLine()) {
                System.out.println("Arduino says: " + scanner.nextLine());
            }
        }).start();

        // Envoyer des données à Arduino
        try {
            Thread.sleep(2000); // Laisser le temps à Arduino de se connecter
            serialPort.getOutputStream().write("Hello from Java!\n".getBytes());
            serialPort.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package javacore.controller;

import com.fazecast.jSerialComm.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ArduinoController {

    private SerialPort comPort = SerialPort.getCommPorts()[0];

    //Abrir a porta do arduino
    public void openComPort(){
        comPort.openPort();
    }
    //Fechar portar do arduino
    public void closeComPort(){
        comPort.closePort();
    }
    //Conseguir String do que o arduino mandou
    public String getComSerial(){
        try {
            comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 3000, 0);
            BufferedReader in = new BufferedReader(new InputStreamReader(comPort.getInputStream()));
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

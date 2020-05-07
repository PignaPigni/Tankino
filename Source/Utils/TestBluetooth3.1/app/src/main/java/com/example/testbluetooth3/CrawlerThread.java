package com.example.testbluetooth3;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.MainThread;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


/**
 * Il thread che gestisce l'invio dei dati e la connessione dal dispositivo android al tankino
 *
 * @author Michele Tomyslak , Nicholas Pigni
 */
public class CrawlerThread extends Thread {
    //private byte leftLastValue llv;


    /**
     * Lo slider di sinistra che controlla il cingolo sinistro del Tankino.
     * min: 0
     * max:127
     */
    private SeekBar left;
    /**
     * Lo slider di destra che controlla il cingolo destro del Tankino.
     * min:128
     * max:255
     */
    private SeekBar right;
    /**
     * L'adattatore bluetooth che si connetterà al Tankino.
     */
    private BluetoothAdapter BA;

    /**
     * Il socket bluetooth con cui il telefono e il tankino comunicano tra loro.
     */
    private BluetoothSocket bss = null;

    /**
     * Lo stream di output verso il tankino.
     */
    private OutputStream os;

    /**
     * Il nome identificativo del Tankino nei dispositivi bluetooth.
     */
    private String name = "Tankino";


    /**
     * Costruttore del gestore dei cingoli con i due slider(SeekBar) che controllano entrambi i motori del tankino.
     * @param left Lo slider di sinistra che controlla il cingolo sinistro del tankino.
     * @param right Lo slider di destra che controlla il cingolo destro del tankino.
     */
    public CrawlerThread(SeekBar left, SeekBar right){
        BA = BluetoothAdapter.getDefaultAdapter();
        this.left = left;
        this.right = right;
    }

    /**
     * Metodo loop della thread di CrawlerThread, in cui prima si connette.
     * Finchè non è interrotto dalla disconnessione del Tankino continuerà a mandare i dati degli slider
     */
    public void run(){
        this.connect();
        try{
            while(!this.isInterrupted()){
                //System.out.println("LEFT: " + (byte)left.getProgress());
                //System.out.println("right" + right.getProgress());
                sendValue((byte)left.getProgress());
                sendValue((byte)right.getProgress());
                Thread.sleep(100);
            }
        }catch (InterruptedException ie){

        }

    }

    /**
     * Metodo per la connessione del dispositivo android al Tankino
     */
    public void connect(){
        //if(!bss.isConnected()){
            for (BluetoothDevice bd:BA.getBondedDevices()){
                if(bd.getName().equalsIgnoreCase(name)){
                    bd = BA.getRemoteDevice("00:14:03:05:F3:0F");
                    System.out.println(bd.getName());
                    ParcelUuid[] p = bd.getUuids();
                    UUID uuid = UUID.fromString(p[0].toString());
                    try{
                        //bd = BA.getRemoteDevice(bd.getAddress().toString());
                        System.out.println("BD: " + bd.getAddress().toString());
                        bss = bd.createInsecureRfcommSocketToServiceRecord(uuid);
                        System.out.println("BSS CREATE...");
                        bss.connect();
                        System.out.println("BSS CONNECT");
                        os = bss.getOutputStream();
                        System.out.println("OS GETOUTPUTSTREAM");
                        /*bss.close();
                        os.close();*/
                        this.start();

                    }catch(IOException ioe){

                        System.out.println("IOE: " + ioe.getMessage());
                    }
                    break;
                }else{
                    System.out.println("Non corrisponde a Tankino");
                }
            }
        /*}else{
            System.out.println("Sei già collegato");
        }*/
    }


    /**
     * Metodo che invia un dato all'outputstream su cui è connesso il tankino con il dispositivo mobile.
     * @param value Il valore da mandare al tankino.
     */
    public void sendValue(byte value){
        try{
            //Istanzia l'outputstream

            os.write(value);
            os.flush();
            System.out.println("Invio del messaggio completato..." + value);
        }catch (IOException ioe){
            System.out.println("Errore 01: Invio del messaggio fallito");
        }catch(NullPointerException npe){
            System.out.println("Errore 02: Tankino non connesso o non trovato" + npe.getMessage());
        }

    }
}

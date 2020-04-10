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

public class CrawlerThread extends Thread {
    //private byte leftLastValue llv;
    private SeekBar left;
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

    public CrawlerThread(SeekBar left, SeekBar right){
        BA = BluetoothAdapter.getDefaultAdapter();
        this.left = left;
        this.right = right;
    }

    public void run(){
        this.connect();
        try{
            while(!this.isInterrupted()){
                System.out.println("left" + left.getProgress());
                System.out.println("right" + right.getProgress());

                Thread.sleep(2000);
            }
        }catch (InterruptedException ie){

        }

    }

    public void connect(){
        //if(!bss.isConnected()){
            for (BluetoothDevice bd:BA.getBondedDevices()){
                if(bd.getName().equalsIgnoreCase(name)){
                    System.out.println(bd.getName());
                    ParcelUuid[] p = bd.getUuids();
                    UUID uuid = UUID.fromString(p[0].toString());
                    try{
                        bd = BA.getRemoteDevice(bd.getAddress().toString());
                        bss = bd.createInsecureRfcommSocketToServiceRecord(uuid);
                        bss.connect();
                        os = bss.getOutputStream();
                        this.sendValue((byte)0b11111111);
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

    public void crowlerLeft(int percentage){

    }

    public void crowlerRight(int percentage){

    }

    public void sendValue(byte value){
        try{
            //Istanzia l'outputstrem

            os.write(value);
            os.flush();
            System.out.println("Invio del messaggio completato...");
        }catch (IOException ioe){
            System.out.println("Errore 01: Invio del messaggio fallito");
        }catch(NullPointerException npe){
            System.out.println("Errore 02: Tankino non connesso o non trovato");
        }

    }
}

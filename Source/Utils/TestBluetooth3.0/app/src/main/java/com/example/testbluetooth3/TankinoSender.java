package com.example.testbluetooth3;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.testbluetooth3.*;
import com.example.testbluetooth3.myapplication.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class TankinoSender extends Activity  {
    Button b1,b2,b3,b4;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice>pairedDevices;
    private OutputStream os;


    /**
     * Questo metodo permette di connettersi con il Tankino e per inviare le percentuali di potenza ai motori.
     * @param v Il contesto grafico.
     *
     */
    public void connect(View v){
        String address = "00:14:03:05:F3:0F";
        String name = "Tankino";
        for (BluetoothDevice bd:BA.getBondedDevices()){
            if(bd.getAddress().equalsIgnoreCase(address) ||  bd.getName().equalsIgnoreCase(name)){
                try{
                    ParcelUuid[] p =bd.getUuids();
                    UUID uuid;

                    BluetoothSocket bss = null;
                    for (ParcelUuid uuids:  p){
                        uuid = uuids.getUuid();
                        bss = bd.createRfcommSocketToServiceRecord(uuid);
                        System.out.println("L'applicazione sta tentando di connettersi");
                        bss.connect();

                        if(bss.isConnected()){
                            System.out.println("Connected to device "+bss.getRemoteDevice().getName());
                            sendValue(bss);

                            break;
                        }
                        break;
                    }


                }catch(IOException ioe){

                }
            }
        }
    }





    /**
     * Metodo che invia un messaggio al Tankino, (metodo di test)
     * @param bs
     */
    public void sendValue(BluetoothSocket bs ){
        try{



            byte[] arrB = {'c',127,-127};
            System.out.println(" Invio del messaggio in corso... ");
            os.write(arrB);
            os.flush();
            System.out.println("Invio del messaggio completato...");
        }catch (IOException ioe){
            System.out.println("Errore 01: Invio del messaggio fallito");
        }

    }


}
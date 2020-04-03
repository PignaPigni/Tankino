package com.example.testbluetooth3;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.view.View;

import com.example.testbluetooth3.myapplication.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    private OutputStream os;
    private BluetoothSocket bss = null;
    private String name = "Tankino";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BA = BluetoothAdapter.getDefaultAdapter();
    }

    public void connect(View v){
        for (BluetoothDevice bd:BA.getBondedDevices()){
            if(bd.getName().equalsIgnoreCase(name)){
                try{
                    ParcelUuid[] p = new ParcelUuid[0];
                    p = bd.getUuids();
                    UUID uuid;
                    for (ParcelUuid uuids:  p) {
                        uuid = uuids.getUuid();
                        bss = bd.createRfcommSocketToServiceRecord(uuid);
                        System.out.println("L'applicazione sta tentando di connettersi");
                        bss.connect();

                        if (bss.isConnected()) {
                            System.out.println("Connected to device " + bss.getRemoteDevice().getName());
                            break;
                        }
                        break;
                    }
                    os = bss.getOutputStream();
                }catch(IOException ioe){

                }catch(NullPointerException npe){
                    System.out.println("Sei una merda");
                }
            }else{
                System.out.println("not found");
            }
        }
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

    public void leftCrowlerForward(View view) {
        sendValue((byte) 'l');
        sendValue((byte) 100);
    }

    public void rightCrowlerForward(View view) {
        sendValue((byte) 'r');
        sendValue((byte) 100);
    }

    public void leftCrowlerBackward(View view) {
        sendValue((byte) 'l');
        sendValue((byte) -100);
    }

    public void rightCrowlerBackward(View view) {
        sendValue((byte) 'r');
        sendValue((byte) -100);
    }

}

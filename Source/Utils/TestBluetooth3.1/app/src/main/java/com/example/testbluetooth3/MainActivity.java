package com.example.testbluetooth3;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.testbluetooth3.myapplication.R;

import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    /**
     * L'adattatore bluetooth che si connetterà al Tankino.
     */
    private BluetoothAdapter BA;
    /**
     * Set dei device bluetooth associati con il telefono
     */
    private Set<BluetoothDevice> pairedDevices;
    /**
     * Lo stream di output verso il tankino.
     */
    private OutputStream os;
    /**
     * Il socket bluetooth con cui il telefono e il tankino comunicano tra loro.
     */
    private BluetoothSocket bss = null;
    /**
     * Il nome identificativo del Tankino nei dispositivi bluetooth.
     */
    private String name = "Tankino";
    /**
     * Lo slider di sinistra che invia i dati al tankino per il cingolo sinistro.
     */
    private SeekBar seekBarLeft;
    /**
     * Lo slider di destra che invia i dati al tankino per il cingolo destro.
     */
    private SeekBar seekBarRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BA = BluetoothAdapter.getDefaultAdapter();
        seekBarLeft = (SeekBar) findViewById(R.id.seekBarLeft);
        seekBarRight = (SeekBar) findViewById(R.id.seekBarRight);
    }

    public void connect(View v){
        for (BluetoothDevice bd:BA.getBondedDevices()){
            if(bd.getName().equalsIgnoreCase(name)){
                System.out.println(bd.getName());
                    ParcelUuid[] p = bd.getUuids();
                    UUID uuid = p[0].getUuid();
                    uuid = UUID.fromString(p[0].toString());
                    for(int i = 0; i < p.length; i++){
                        System.out.println(p.length + "ParcelUids: " + p[i].toString() + " UUID: " + uuid.toString());
                    }
                    try{
                        //bss = bd.createRfcommSocketToServiceRecord(uuid);
                        bss = bd.createInsecureRfcommSocketToServiceRecord(uuid);
                        System.out.println("isConnected:" + bss.isConnected());
                        System.out.println("Create Socket" + bss.toString());
                        System.out.println("sksk" + bss.getRemoteDevice());
                        bss.connect();
                        System.out.println("Connect" + bd.getAddress());
                        os = bss.getOutputStream();
                        System.out.println("OutputStream");
                    }catch(IOException ioe){
                        System.out.println("IOE: " + ioe.getMessage());
                    }
                    break;


                    /*for (ParcelUuid uuids:  p) {
                        uuid = uuids.getUuid();
                        bss = bd.createRfcommSocketToServiceRecord(uuid);
                        System.out.println("L'applicazione sta tentando di connettersi");
                        try{
                            bss.connect();
                        }catch(IOException ioe){
                            System.out.println("First IOE KVHGFKGJKFJGKFJGFJKFKHFGJFFF");

                        }
                        System.out.println("BSS: " + bss.toString());
                        if (bss.isConnected()) {
                            System.out.println("Connected to device " + bss.getRemoteDevice().getName());
                            break;
                        }
                    }
                    os = bss.getOutputStream();
                    break; */
            }else{
                System.out.println("Non corrisponde a Tankino");
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

    public void getLeftCrowlerSpeed(View view){
        seekBarLeft.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println(progress);
                sendValue((byte)progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                CrawlerThread t = new CrawlerThread();

            }

            public void sendLastValuesThread(SeekBar seekBar ){

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });

    }

    public void getRightCrowlerSpeed(View view){
        seekBarLeft.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println(progress);
                sendValue((byte)progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                System.out.println(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println(seekBar.getProgress());
            }
        });
    }

    public void leftCrowlerForward(View view) {
        System.out.println(0b11111111);
        sendValue((byte) 0b11111111);

    }

    public void rightCrowlerForward(View view) {
        System.out.println(0b01111111);

        sendValue((byte) 0b01111111);
    }

    public void leftCrowlerBackward(View view) {
        System.out.println(0b10000000);
        sendValue((byte) 0b10000000);
    }

    /**
     * Muove il cingolo di destra a massima potenza all'indietro (-100%)
     * @param view La vista
     */
    public void rightCrowlerBackward(View view) {
        System.out.println(0b00000000);
        sendValue((byte) 0b0111111);
    }

}

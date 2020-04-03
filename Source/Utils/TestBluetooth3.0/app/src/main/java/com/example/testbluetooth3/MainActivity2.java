/*package com.example.testbluetooth3;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.RequiresApi;

import com.example.testbluetooth3.myapplication.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity2 extends Activity  {
    Button b1,b2,b3,b4;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice>pairedDevices;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        b3=(Button)findViewById(R.id.button3);


        BA = BluetoothAdapter.getDefaultAdapter();

    }






    public  void visible(View v){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    /*public ArrayList<BluetoothDevice> list(View v){
        /*pairedDevices = BA.getBondedDevices();

        ArrayList list = new ArrayList();

        for(BluetoothDevice bt : pairedDevices) list.add(bt);
        Toast.makeText(getApplicationContext(), "Showing Paired Devices",Toast.LENGTH_SHORT).show();

        final ArrayAdapter adapter = new  ArrayAdapter(this,android.R.layout.simple_list_item_1, list);

        lv.setAdapter(adapter);
        //sendMessage(v);
        connect(v);
        return list;
    }*/
/*
    public void connect(View view){
        String address = "00:14:03:05:F3:0F";
        String name = "DS TECH HC-05";
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
     *
     * @param bs
     */
/*
    public void sendValue(BluetoothSocket bs){
        try{

            OutputStream os = bs.getOutputStream();

            byte[] arrB = {'c',127,-127};
            System.out.println(" Invio del messaggio in corso... ");
            os.write(arrB);
            os.flush();
            System.out.println("Invio del messaggio completato...");
        }catch (IOException ioe){
            System.out.println("Errore 01: Invio del messaggio fallito");
        }

    }

/*
}*/
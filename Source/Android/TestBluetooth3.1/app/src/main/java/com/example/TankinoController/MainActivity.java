package com.example.TankinoController;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import com.example.TankinoController.myapplication.R;

/**
 * deprecato
 * @author Michle Tomyslak, Nicholas Pigni
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Lo slider di sinistra che invia i dati al tankino per il cingolo sinistro.
     */
    private SeekBar seekBarLeft;
    /**
     * Lo slider di destra che invia i dati al tankino per il cingolo destro.
     */
    private SeekBar seekBarRight;

    /**
     * Il thread che gestisce il passaggio di dati
     */
    private CrawlerThread crawlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBarLeft = (SeekBar) findViewById(R.id.seekBarLeft);
        seekBarRight = (SeekBar) findViewById(R.id.seekBarRight);
        crawlerThread = new CrawlerThread(seekBarLeft, seekBarRight);
    }

    public void connect(View v){
        this.crawlerThread.connect();
    }
}

package de.mide.android.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public static final String TAG4LOGGING = "Empfaenger";

    /**
     * Broadcast-Receiver-Objekt, wird bei Erzeugung der Activity registriert, und
     * vor Zerstörung der Activity de-registriert.
     */
    private MeinBroadcastReceiver _meinBroadcastReceiver = null;


    /**
     * Lifecycle-Methode: Layout-Datei laden, Broadcast Receiver registrieren.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        broadcastReceiverRegistrieren();
    }


    /**
     * Ein Broadcast-Receiver-Objekt für mehrere Broadcast Intents registrieren.
     *
     * Alle Intents, für die der Receiver registriert, können im Emulator ausgelöst werden.
     */
    private void broadcastReceiverRegistrieren() {

        _meinBroadcastReceiver = new MeinBroadcastReceiver();

        final IntentFilter intentFilter1 = new IntentFilter(Intent.ACTION_BATTERY_LOW              );
        final IntentFilter intentFilter2 = new IntentFilter(Intent.ACTION_POWER_CONNECTED          );
        final IntentFilter intentFilter3 = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED       );
        final IntentFilter intentFilter4 = new IntentFilter(Intent.ACTION_BATTERY_CHANGED          );

        registerReceiver(_meinBroadcastReceiver, intentFilter1);
        registerReceiver(_meinBroadcastReceiver, intentFilter2);
        registerReceiver(_meinBroadcastReceiver, intentFilter3);
        registerReceiver(_meinBroadcastReceiver, intentFilter4);

        Log.i(TAG4LOGGING, "Broadcast-Receiver wurde registriert");
    }


    /**
     * Lifecycle-Methode: Broadcast Receiver de-registrieren.
     */
    @Override
    protected void onDestroy() {

        super.onDestroy();

        unregisterReceiver(_meinBroadcastReceiver);
        Log.i(TAG4LOGGING, "Broadcast-Receiver wurde deregistriert");
    }

}
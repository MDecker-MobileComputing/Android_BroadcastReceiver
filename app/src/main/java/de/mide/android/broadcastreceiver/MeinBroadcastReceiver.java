package de.mide.android.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


/**
 * Unterklasse der abstrakten Klasse {@code BroadcastReceiver}; Empfänger
 * für Broadcast Intents laut Intent Filtern in der Manifest-Datei.
 */
public class MeinBroadcastReceiver extends BroadcastReceiver  {

    private static final String TAG4LOGGING = "Empfaenger";


    /**
     * Einzige abstrakte Methode aus Klasse {@code BroadcastReceiver}; wird für jeden
     * Broadcast-Intent, für den wir uns laut Deklaration in der Manifest-Datei interessieren,
     * aufgerufen.
     *
     * @param context Context der App
     *
     * @param intent Empfangener Broadcast Intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        final String actionString = intent.getAction();
        Log.i(TAG4LOGGING, "Broadcast intent empfangen: " + actionString);

        switch (actionString) {

            case Intent.ACTION_BATTERY_LOW:
                zeigeNachrichtInToast(context, "Batterie niedrig!");
                break;

            default:
                zeigeNachrichtInToast(context,
                              "Unerwarteter Broadcast Intent empfangen: " + actionString);
        }
    }


    /**
     * Hilfesmethode, zeigt eine Nachricht in einem Toast an.
     *
     * @param context Context der App
     *
     * @param nachricht Anzuzeigende Nachricht
     */
    private static void zeigeNachrichtInToast(Context context, String nachricht) {

        Toast.makeText(context, nachricht, Toast.LENGTH_LONG).show();
    }

}

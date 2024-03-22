package de.mide.android.broadcastreceiver;

import static de.mide.android.broadcastreceiver.MainActivity.TAG4LOGGING;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


/**
 * Unterklasse der abstrakten Klasse {@code BroadcastReceiver}; Empfänger
 * für <i>Broadcast Intents</i> laut Intent Filtern in der Manifest-Datei.
 *
 * Ab Android 7+8 können viele <i>Broadcast Intents</i> nur noch mit dynamisch
 * registrieren Receivern empfangen werden und nicht mehr über Receiver, die
 * in der Manifest-Datei deklariert wurden.
 * (<a href="https://developer.android.com/about/versions/oreo/background#broadcasts">Quelle</a>)
 */
public class MeinBroadcastReceiver extends BroadcastReceiver  {

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
        Log.i(TAG4LOGGING, "Broadcast Intent empfangen: " + actionString);

        switch (actionString) {

            case Intent.ACTION_BATTERY_LOW:
                    zeigeNachrichtInToast(context, "Batterie niedrig!");
                break;

            case Intent.ACTION_POWER_CONNECTED:
                    zeigeNachrichtInToast(context, "Ladegerät angeschlossen!");
                break;

            case Intent.ACTION_POWER_DISCONNECTED:
                zeigeNachrichtInToast(context, "Ladegerät entfernt!");
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

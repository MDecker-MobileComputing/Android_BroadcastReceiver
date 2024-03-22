package de.mide.android.broadcastreceiver;

import static de.mide.android.broadcastreceiver.MainActivity.TAG4LOGGING;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.BatteryManager;
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
     * Einzige abstrakte Methode aus Klasse {@code BroadcastReceiver}.
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

            case Intent.ACTION_BATTERY_CHANGED:
                    onAenderungAkku(context, intent);
                break;

            case ConnectivityManager.CONNECTIVITY_ACTION:
                    onAenderungKonnektivitaet(context, intent);
                break;

            default:
                zeigeNachrichtInToast(context,
                              "Unerwarteter Broadcast Intent empfangen: " + actionString);
                Log.w(TAG4LOGGING, "Unerwarteter Broadcast Intent empfangen: " + intent);
        }
    }


    /**
     * Event-Handler-Methode für Änderung Ladestatus Akku.
     *
     * <b>Achtung:</b> Event wird bei Veränderungen Akku-Stand im Emulator evtl. sehr oft gefeuert
     * hintereinander gefeuert!
     *
     * @param context Context der App
     *
     * @param intent Empfangener Broadcast Intent von Typ {@code ACTION_BATTERY_CHANGED}
     */
    private void onAenderungAkku(Context context, Intent intent) {

        int akkuLevelAktuell = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int akkuLevelMax     = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int akkuProzent      = 100 * akkuLevelAktuell / akkuLevelMax;
        String akkuNachricht = "Neuer Akkustand empfangen: " + akkuProzent + "%";
        Log.i(TAG4LOGGING, akkuNachricht);
        zeigeNachrichtInToast(context, akkuNachricht);
    }


    /**
     * Event-Handler-Methode für Änderung Konnektivitäts-Status.
     * <br><br>
     *
     * Die Details werden nicht aus dem Extra des Intents ausgelesen, weil dies
     * deprecated ist, siehe
     * <a href="https://developer.android.com/reference/android/net/ConnectivityManager#EXTRA_NETWORK_INFO">hier</a>.
     * <br><br>
     *
     * Für das Auslesen der Netzwerkdetails muss die Berechtigung {@code ACCESS_NETWORK_STATE}
     * in der Manifest-Datei deklariert werden; es handelt sich dabei um eine Berechtigung mit
     * "Protection Level: Normal", sie wird also NICHT als Runtime Permission behandelt.
     *
     * @param context Context der App
     *
     * @param intent Empfangener Broadcast Intent von Typ {@code CONNECTIVITY_ACTION}
     */
    private void onAenderungKonnektivitaet(Context context, Intent intent) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetwork = cm.getActiveNetwork();
        NetworkCapabilities networkCapabilities = cm.getNetworkCapabilities(activeNetwork);

        if (networkCapabilities != null) {

            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                zeigeNachrichtInToast(context, "Änderung Konnektivität: WiFi");

            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

                zeigeNachrichtInToast(context, "Änderung Konnektivität: Mobilfunk");

            } else {

                zeigeNachrichtInToast(context, "Änderung Konnektivität: unbekanntes Netzwerk");
            }

        } else {

            zeigeNachrichtInToast(context, "Änderung Konnektivität: KEINE");
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

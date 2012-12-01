/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.manchini.secureBlueFC;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import br.com.manchini.utils.lista.ListItem;
import java.util.ArrayList;

/**
 *
 * @author manchini
 */
public class Servico extends Service implements Runnable {

    private boolean ativo = true;
    private ArrayList lista = new ArrayList();

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void run() {

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d("LISTA", "Receive called...");
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    Log.d("LISTA", "Adicionou na lista....");
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    lista.add(device);
                }
            }
        };
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, filter);//  
        mBluetoothAdapter.startDiscovery();
        
        while (ativo) {
            if(lista.size()> 3){
//                Intent disableBtIntent = new Intent(BluetoothAdapter.ACTION_STATE_CHANGED);
//                startActivityForResult(disableBtIntent, 0);
                
            }
            
        }
    }
}

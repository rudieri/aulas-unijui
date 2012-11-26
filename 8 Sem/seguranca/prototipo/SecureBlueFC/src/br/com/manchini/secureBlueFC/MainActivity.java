package br.com.manchini.secureBlueFC;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import br.com.manchini.utils.lista.ListAdapter;
import br.com.manchini.utils.lista.ListItem;
import br.com.manchini.utils.lista.Lista;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends Activity {

    private BroadcastReceiver broadcastReceiver;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final LinearLayout layoutLista = (LinearLayout) findViewById(R.tela.listaBlue);
        final ListAdapter<BluetoothDevice> listAdapter = new ListAdapter<BluetoothDevice>(this, false);
        final Lista<BluetoothDevice> lista = new Lista<BluetoothDevice>(this, listAdapter);
        ListItem listItem = new ListItem(this);
        listItem.addView("Teste", null);
        lista.addToList(listItem);
        layoutLista.addView(lista.getContentView());

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d("LISTA", "Receive called...");
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    Log.d("LISTA", "Adicionou na lista....");
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    ListItem<BluetoothDevice> item = new ListItem<BluetoothDevice>(MainActivity.this);
                    item.addView(device.getName(), device);
                    listAdapter.addToList(item);
                    layoutLista.recomputeViewAttributes(lista.getContentView());
                    layoutLista.refreshDrawableState();
                }
            }
        };


        ToggleButton brBlue = (ToggleButton) findViewById(R.tela.btBlue);
        brBlue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            private BluetoothAdapter mBluetoothAdapter;
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    Log.d("LISTA", "Crico no botão.");
                    if (isChecked) {
                        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (mBluetoothAdapter == null) {
                            Log.e("BLUETOOTH", "NÂO PEGO O BLUETOOTH", null);
                        } else {
                            if (!mBluetoothAdapter.isEnabled()) {
                                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                startActivityForResult(enableBtIntent, 0);
                            }
                            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                            
                            registerReceiver(broadcastReceiver, filter);//  
                            mBluetoothAdapter.startDiscovery();
                        }

                    } else {
                        unregisterReceiver(broadcastReceiver);
                        
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

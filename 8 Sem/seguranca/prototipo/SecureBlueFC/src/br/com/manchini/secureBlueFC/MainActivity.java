package br.com.manchini.secureBlueFC;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;
import br.com.manchini.utils.lista.ListAdapter;
import br.com.manchini.utils.lista.ListItem;
import br.com.manchini.utils.lista.Lista;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends Activity {

    private BroadcastReceiver broadcastReceiver;
    boolean pareoucomAlguem = false;

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
                    if (pairDevice(device)) {
                        pareoucomAlguem = true;
                    }
                    
                    EditText txtnr = (EditText)findViewById(R.tela.txtNr);
                    if(listAdapter.getCount() > new Integer(txtnr.getText().toString())){
                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        mBluetoothAdapter.disable();
                        Toast.makeText(MainActivity.this, "Quantidade de Dispositivos Atingida.. Desligando Bluetooth", Toast.LENGTH_LONG).show();
                    }
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
                        if (!pareoucomAlguem) {
                            Toast.makeText(MainActivity.this, "Não Pareou com Ninguem", Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        ToggleButton brAmbiente = (ToggleButton) findViewById(R.tela.btAmbiente);
        brAmbiente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                        if (!pareoucomAlguem) {
                            Toast.makeText(MainActivity.this, "Não Pareou com Ninguem", Toast.LENGTH_SHORT).show();
                        }

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

    public boolean pairDevice(BluetoothDevice device) {
        try {
            //            IBluetooth iBluetooth = getIBluetooth();            
            //            iBluetooth.setTrust(device.getAddress(), true);
            //            iBluetooth.getClass().getMethods()[100].getParameterTypes();
            //            iBluetooth.setPin(device, true, 0, convertPinToBytes("1234"));
            ArrayList<String> lista = new Senhas().getLista();
            for (int i = 0; i < lista.size(); i++) {
                Method method = device.getClass().getMethod("setPin", byte[].class);
                Object invoke = method.invoke(device, convertPinToBytes(lista.get(i)));

                if ((Boolean) invoke == true) {
                    Toast.makeText(this, "Conseguiu Parear com " + device.getName() + " Senha: " + lista.get(i), Toast.LENGTH_SHORT).show();
                    return true;
                }

            }
            return false;

        } catch (Exception ex) {
            Log.e("SECUREBLUEFC", "ERRO AO TENTAR PAREAR", ex);
            return false;
        }


    }

    private byte[] convertPinToBytes(String pin) {
        if (pin == null) {
            return null;
        }
        byte[] pinBytes;
        try {
            pinBytes = pin.getBytes("UTF8");
        } catch (Exception uee) {
            Log.e("SECUREBLUEFC", "UTF8 not supported?!?");  // this should not happen
            return null;
        }
        if (pinBytes.length <= 0 || pinBytes.length > 16) {
            Log.d("SECUREBLUEFC", "PIN Invalido");
            return null;
        }
        return pinBytes;
    }
}

package br.com.manchini.cursoarduino;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import br.com.manchini.cursoarduino.utils.Alert;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends Activity
{
    
    private static BluetoothSocket bluetoothSocket;

    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
            bluetoothSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        carregaBluetooth();
        
        ToggleButton btLed = (ToggleButton) findViewById(R.tela.btLed);
        btLed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if(isChecked){
                        bluetoothSocket.getOutputStream().write("l".getBytes());
                    }else{
                        bluetoothSocket.getOutputStream().write("d".getBytes());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        ToggleButton brPorta = (ToggleButton) findViewById(R.tela.btPorta);
        brPorta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if(isChecked){
                        bluetoothSocket.getOutputStream().write("a".getBytes());
                    }else{
                        bluetoothSocket.getOutputStream().write("f".getBytes());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
    }
    
    
    
    private void carregaBluetooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.e("BLUETOOTH", "NÂO PEGO O BLUETOOTH", null);
        } else {
            try {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, 0);
                }

                BluetoothDevice carrinho = null;
                //
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                // If there are paired devices
                if (pairedDevices.size() > 0) {
                    // Loop through paired devices
                    for (BluetoothDevice device : pairedDevices) {
                        // Add the name and address to an array adapter to show in a ListView
                        if (device.getAddress().equals("00:11:05:09:00:99")) {
                            carrinho = device;
                        }
                    }
                }
                if (carrinho == null) {
                    Alert alert = new Alert(this);
                    alert.setMessage("Da uma olhada que ta Desligado o Bluetooth.");
                    alert.addButton(" Então Tá.", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface di, int i) {
//                    finish();
                        }
                    });
                    alert.show();

                    return;
                }
                //21:03:30.026	225	#225	DEBUG	BluetoothService	    uuid(application): 00001101-0000-1000-8000-00805f9b34fb 1
                mBluetoothAdapter.cancelDiscovery();

                UUID MY_UUID =
                        UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
                Log.i("BLUETOOTH", MY_UUID.toString());
                if (bluetoothSocket == null) {
                    bluetoothSocket = carrinho.createRfcommSocketToServiceRecord(MY_UUID);
                    bluetoothSocket.connect();
                } else {
                    if (!bluetoothSocket.isConnected()) {
                        bluetoothSocket.connect();
                    }
                }
                Log.i("BLUETOOTH", "DEU CERTO ....");
                new Thread(new Runnable() {
                    public void run() {
                        boolean ativo = true;
                        while (ativo) {
                            try {
                                InputStream inputStream = bluetoothSocket.getInputStream();
//                                BufferedInputStream bis = new BufferedInputStream(inputStream);
                                InputStreamReader reader = new InputStreamReader(inputStream);
                                BufferedReader br = new BufferedReader(reader);
                                Log.i("ARDUINO", br.readLine());
                            } catch (IOException ex) {
                                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                                ativo = false;
                            }

                        }
                    }
                }).start();
                ////
                //this.mDigitalOnScreenControl = new DigitalOnScreenControl(0, CAMERA_HEIGHT - this.mOnScreenControlBaseTextureRegion.getHeight(), this.mCamera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, new IOnScreenControlListener() {



            } catch (Exception ex) {
                Log.e("BLUETOOTH", "FUDEU ao Conectar", ex);
            } finally {
               
            }


        }
    }

}

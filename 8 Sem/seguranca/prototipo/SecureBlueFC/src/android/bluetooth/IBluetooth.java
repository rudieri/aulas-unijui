package android.bluetooth;
 
import android.os.IBinder;
import android.os.ParcelUuid;
 
/**
 * System private API for talking with the Bluetooth service.
 *
 * {@hide}
 */
public interface IBluetooth
{
    boolean isEnabled();
    int getBluetoothState();
    boolean enable();
    boolean disable(boolean persistSetting);
 
    String getAddress();
    String getName();
    boolean setName(String name);
 
    int getScanMode();
    boolean setScanMode(int mode, int duration);
 
    int getDiscoverableTimeout();
    boolean setDiscoverableTimeout(int timeout);
 
    boolean startDiscovery();
    boolean cancelDiscovery();
    boolean isDiscovering();
 
    boolean createBond( String address);
    boolean cancelBondProcess( String address);
    boolean removeBond( String address);
    String[] listBonds();
    int getBondState( String address);
 
    String getRemoteName( String address);
    int getRemoteClass(String address);
    ParcelUuid[] getRemoteUuids( String address);
    boolean fetchRemoteUuids( String address,  ParcelUuid uuid,  IBluetoothCallback callback);
    int getRemoteServiceChannel( String address,  ParcelUuid uuid);
 
    boolean setPin( String address,  byte[] pin);
    boolean setPin(BluetoothDevice device, boolean accept, int len, byte[] pinCode);
    boolean setPasskey( String address, int passkey);
    boolean setPairingConfirmation( String address, boolean confirm);
    boolean cancelPairingUserInput( String address);
 
    boolean setTrust( String address,  boolean value);
    boolean getTrustState( String address);
 
    int addRfcommServiceRecord( String serviceName,  ParcelUuid uuid, int channel, IBinder b);
    void removeServiceRecord(int handle);
}
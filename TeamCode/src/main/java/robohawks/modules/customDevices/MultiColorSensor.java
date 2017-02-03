package robohawks.modules.customDevices;

import android.util.Log;
import com.qualcomm.robotcore.hardware.*;
import robohawks.utils.ArrayQueue;
import robohawks.utils.I2cTransfer;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.locks.Lock;

/**
 * Created by paarth on 2/2/17.
 */
public class MultiColorSensor implements ColorSensor{
    private HardwareMap hardwareMap;
    private String deviceName;
    private I2cAddr addr;

    public MultiColorSensor(HardwareMap hardwareMap, String deviceName, I2cAddr addr) {
        this.hardwareMap = hardwareMap;
        this.deviceName =deviceName;
        this.addr =addr;
    }

    @Override
    public int red() {
        return 0;
    }

    @Override
    public int green() {
        return 0;
    }

    @Override
    public int blue() {
        return 0;
    }

    @Override
    public int alpha() {
        return 0;
    }

    @Override
    public int argb() {
        return 0;
    }

    @Override
    public void enableLed(boolean enable) {

    }

    @Override
    public void setI2cAddress(I2cAddr newAddress) {
        this.addr = newAddress;
    }

    @Override
    public I2cAddr getI2cAddress() {
        return addr;
    }

    @Override
    public Manufacturer getManufacturer() {
        return null;
    }

    @Override
    public String getDeviceName() {
        return deviceName;
    }

    @Override
    public String getConnectionInfo() {
        return null;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {

    }

    @Override
    public void close() {

    }
}

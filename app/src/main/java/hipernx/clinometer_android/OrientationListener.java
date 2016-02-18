package hipernx.clinometer_android;

import android.app.Fragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

/**
 * @author Gustaf Nilstadius
 * @date 2016.02-18
 */
public class OrientationListener implements SensorEventListener {
    /**
     * Constants for the sensor values
     */
    private static final int PITCH = 2;
    private static final int ROLL = 1;

    private Fragment pitch = null;
    private Fragment roll = null;


    /**
     *
     * @param pitch fragment for updating the pitch
     * @param roll fragment for updating the roll
     */
    OrientationListener(Fragment pitch, Fragment roll){
        this.pitch = pitch;
        this.roll = roll;
    }

    /**
     * Notified when sensor is changed
     * @param sensorEvent Sensor event containing values of mRotation sensor
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
            //TODO update view (text for now)
            //TODO Handel accuracy


            //TODO test conversion
            float[] rotationValues = new float[3];
            float[] mRotationMatrix = new float[16];
            SensorManager.getRotationMatrixFromVector(mRotationMatrix, sensorEvent.values);
            SensorManager
                    .remapCoordinateSystem(mRotationMatrix,
                            SensorManager.AXIS_X, SensorManager.AXIS_Z,
                            mRotationMatrix);
            SensorManager.getOrientation(mRotationMatrix, rotationValues);

            rotationValues[0] = (float) Math.toDegrees(rotationValues[0]);
            rotationValues[1] = (float) Math.toDegrees(rotationValues[1]);
            rotationValues[2] = (float) Math.toDegrees(rotationValues[2]);

            //TODO replace with fragments
            TextView pitch = (TextView) findViewById(R.id.pitch);
            int pitch_int = (int) rotationValues[PITCH];
            pitch.setText(pitch_int + "");

            TextView roll = (TextView) findViewById(R.id.roll);
            int roll_int = (int) rotationValues[ROLL];
            roll.setText(roll_int + "");


            Log.d("SensorChanged", rotationValues[0] + "," + rotationValues[1] + "," + rotationValues[2]);
        }

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

package com.byagowi.persiancalendar;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.byagowi.persiancalendar.utils.QiblaDirectionCalculator;
import com.byagowi.persiancalendar.utils.Utils;
import com.github.praytimes.Coordinate;

/**
 * Compass/Qibla activity
 * 
 * @author ebraminio
 */
public class CompassActivity extends Activity {
	private Utils utils = Utils.getInstance();

	public CompassView compassView;
	public TextView degree;
	
	private SensorManager sensorManager;
	private Sensor sensor;
	private SensorEventListener compassListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// utils.setTheme(this);
		super.onCreate(savedInstanceState);
		compassListener = new CompassListener(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.compass);
		compassView = (CompassView) findViewById(R.id.compass_view);
		degree = (TextView) findViewById(R.id.degree);

		Coordinate coordinate = utils.getCoordinate(this);
		if (coordinate != null) {
			compassView.setQibla(QiblaDirectionCalculator
					.getQiblaDirectionFromNorth(coordinate.getLatitude(),
							coordinate.getLongitude()));
		}

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		if (sensor != null) {
			sensorManager.registerListener(compassListener, sensor,
					SensorManager.SENSOR_DELAY_FASTEST);
		} else {
			utils.quickToast(getString(R.string.compass_not_found), this);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (sensor != null) {
			sensorManager.unregisterListener(compassListener);
		}
	}
}

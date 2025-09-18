package de.timklge.karoomocklocation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.location.LocationProvider
import android.location.provider.ProviderProperties
import android.os.SystemClock
import android.util.Log

class MockLocationReceiver : BroadcastReceiver() {
    companion object {
        const val TAG = "MockLocationReceiver"
        const val ACTION_SET_MOCK_LOCATION = "de.timklge.karoomocklocation.SET_MOCK_LOCATION"
        const val EXTRA_LATITUDE = "latitude"
        const val EXTRA_LONGITUDE = "longitude"
        const val EXTRA_ACCURACY = "accuracy"
        const val EXTRA_SPEED = "speed"
        const val EXTRA_BEARING = "bearing"
        const val EXTRA_ALTITUDE = "altitude"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != ACTION_SET_MOCK_LOCATION) {
            return
        }

        val latitude = intent.getFloatExtra(EXTRA_LATITUDE, 0.0f)
        val longitude = intent.getFloatExtra(EXTRA_LONGITUDE, 0.0f)
        val accuracy = intent.getFloatExtra(EXTRA_ACCURACY, 5.0f)
        val speed = intent.getFloatExtra(EXTRA_SPEED, 0.0f)
        val bearing = intent.getFloatExtra(EXTRA_BEARING, 0.0f)
        val altitude = intent.getFloatExtra(EXTRA_ALTITUDE, 0.0f)

        Log.i(TAG, "Received mock location request: lat=$latitude, lon=$longitude, accuracy=$accuracy")

        if (latitude == 0.0f && longitude == 0.0f) {
            Log.w(TAG, "Invalid coordinates received, ignoring")
            return
        }

        try {
            setMockLocation(context, latitude, longitude, accuracy, speed, bearing, altitude)
            Log.i(TAG, "Successfully set mock location")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to set mock location", e)
        }
    }

    private fun setMockLocation(
        context: Context?,
        latitude: Float,
        longitude: Float,
        accuracy: Float,
        speed: Float,
        bearing: Float,
        altitude: Float
    ) {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
            ?: throw IllegalStateException("LocationManager not available")

        // Add test provider if not already added
        try {
            locationManager.addTestProvider(
                LocationManager.GPS_PROVIDER,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                ProviderProperties.POWER_USAGE_LOW,
                ProviderProperties.ACCURACY_FINE
            )
        } catch (e: IllegalArgumentException) {
            // Provider already exists, ignore
        }

        val location = Location(LocationManager.GPS_PROVIDER).apply {
            this.latitude = latitude.toDouble()
            this.longitude = longitude.toDouble()
            this.accuracy = accuracy
            this.speed = speed
            this.bearing = bearing
            this.altitude = altitude.toDouble()
            this.time = System.currentTimeMillis()
            this.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()
        }

        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true)
        locationManager.setTestProviderStatus(
            LocationManager.GPS_PROVIDER,
            LocationProvider.AVAILABLE,
            null,
            System.currentTimeMillis()
        )
        locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, location)
    }
}

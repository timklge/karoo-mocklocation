package de.timklge.karoomocklocation

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.util.Log
import io.hammerhead.karooext.KarooSystemService
import io.hammerhead.karooext.extension.DataTypeImpl
import io.hammerhead.karooext.extension.KarooExtension


class KarooMockLocationExtension : KarooExtension("karoo-mocklocation", BuildConfig.VERSION_NAME) {
    companion object {
        const val TAG = "karoo-mocklocation"
    }

    private lateinit var karooSystem: KarooSystemService
    private lateinit var mockLocationReceiver: MockLocationReceiver

    override val types: List<DataTypeImpl> by lazy {
        listOf()
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreate() {
        super.onCreate()

        karooSystem = KarooSystemService(applicationContext)

        // Register broadcast receiver
        mockLocationReceiver = MockLocationReceiver()
        val filter = IntentFilter(MockLocationReceiver.ACTION_SET_MOCK_LOCATION)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(mockLocationReceiver, filter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(mockLocationReceiver, filter)
        }

        Log.i(TAG, "Registered mock location broadcast receiver")
    }

    override fun onDestroy() {
        // Unregister broadcast receiver
        try {
            unregisterReceiver(mockLocationReceiver)
            Log.i(TAG, "Unregistered mock location broadcast receiver")
        } catch (e: Exception) {
            Log.w(TAG, "Failed to unregister broadcast receiver", e)
        }

        karooSystem.disconnect()
        super.onDestroy()
    }
}

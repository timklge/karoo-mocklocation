package de.timklge.karoomocklocation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import de.timklge.karoomocklocation.screens.MainScreen
import de.timklge.karoomocklocation.theme.AppTheme

class MainActivity : ComponentActivity() {
    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            // Handle permission result if needed
            val coarseGranted = permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
            val fineGranted = permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            if (!coarseGranted || !fineGranted) {
                Log.e(KarooMockLocationExtension.TAG, "Location permissions not granted")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                MainScreen() {
                    finish()
                }
            }
        }
    }
}


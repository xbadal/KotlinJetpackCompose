package com.badal.locationapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.badal.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: LocationViewModel = viewModel()

            LocationAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MyApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun MyApp(viewModel: LocationViewModel) {
    val context = LocalContext.current
    val locationUtil = LocationUtils(context)

    LocationDisplay(locationUtil = locationUtil, context = context, viewModel)
}

@Composable
fun LocationDisplay(
    locationUtil: LocationUtils, context: Context, viewModel: LocationViewModel
) {

    var location = viewModel.location.value
    val address = location?.let {
        locationUtil.reverseGeocodeLocation(location)
    }


    val requestPermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { permissions ->
                if (permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true && permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                    // I have Location permission

                    locationUtil.requestLocationUpdates(viewModel)

                } else {
                    // Ask for permission
                    val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                        context as MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) || ActivityCompat.shouldShowRequestPermissionRationale(
                        context, android.Manifest.permission.ACCESS_COARSE_LOCATION
                    )

                    if (rationaleRequired) {
                        Toast.makeText(
                            context,
                            "Location permission is required feature to work",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Location permission is required, Please enable it in Android Settings",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }

            })


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (location != null) {
            Text(text = "Location: ${location.latitude},  ${location.longitude}")

            Text(text = "Address: $address")
        } else {
            Text(text = "Location not available")
        }



        Button(onClick = {
            if (locationUtil.hasLocationPermission(context)) {
                // Permission already granted update the location
                locationUtil.requestLocationUpdates(viewModel)
            } else {
                // Request Location Permission
                requestPermissionLauncher.launch(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }

        }) {
            Text(text = "Get Location")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LocationAppTheme {}
}
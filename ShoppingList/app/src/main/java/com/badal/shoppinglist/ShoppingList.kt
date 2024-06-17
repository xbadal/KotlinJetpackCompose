package com.badal.shoppinglist

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController


data class ShoppingItem(
    val id: Int,
    var name: String,
    var quality: Int,
    var isEditing: Boolean = false,
    var address: String = ""
)

@Composable
fun ShoppingListApp(
    locationUtil: LocationUtils,
    viewModel: LocationViewModel,
    navController: NavController,
    context: Context,
    address: String
) {

    var sItems by remember {
        mutableStateOf(listOf<ShoppingItem>())
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    var itemName by remember {
        mutableStateOf("")
    }

    var itemQuantity by remember {
        mutableStateOf("")
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
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
    ) {

        Button(
            onClick = {
                showDialog = true
            }, modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Add Items")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(sItems) {
                ShoppingListItem(it, onEditing = {}, onDeleteClicked = {})
            }
        }
    }


    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false }, confirmButton = {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(onClick = {

                    if (itemName.isNotBlank() && itemQuantity.isNotBlank()) {
                        val item = ShoppingItem(
                            id = sItems.size + 1,
                            name = itemName,
                            quality = itemQuantity.toInt(),
                            address = address
                        )
                        sItems = sItems + item;
                        showDialog = false
                        println("================= ${sItems.size}")
                        itemName = ""
                        itemQuantity = ""
                    }

                }) {
                    Text(text = "Add")
                }

                OutlinedButton(onClick = { showDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        }, title = { Text(text = "Add Shopping Item") }, text = {
            Column {
                OutlinedTextField(
                    value = itemName, onValueChange = {
                        itemName = it;
                    }, singleLine = true, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )


                OutlinedTextField(
                    value = itemQuantity, onValueChange = {
                        itemQuantity = it;
                    }, singleLine = true, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Button(onClick = {

                    if (locationUtil.hasLocationPermission(context)) {
                        locationUtil.requestLocationUpdates(viewModel)
                        navController.navigate("locationscreen") {
                            this.launchSingleTop
                        }
                    } else {
                        requestPermissionLauncher.launch(
                            arrayOf(
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            )
                        )
                    }


                }) {
                    Text(text = "Address")
                }
            }
        })
    }

}

@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onEditing: () -> Unit,
    onDeleteClicked: () -> Unit
) {

    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
            .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(10)),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            Row {
                Text(text = "Name: ${item.name}")
                Text(text = "Quantity: ${item.quality}")
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                Text(text = item.address)
            }

        }
        Row {
            IconButton(onClick = onEditing) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = onDeleteClicked) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Item")
            }
        }

    }
}
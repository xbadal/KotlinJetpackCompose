package com.badal.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.badal.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UnitConverter(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun UnitConverter(modifier: Modifier = Modifier) {

    var inputValue by remember {
        mutableStateOf("")
    }

    var outputValue by remember {
        mutableStateOf("")
    }

    var inputUnitValue by remember {
        mutableStateOf("Meters")
    }
    var outputUnitValue by remember {
        mutableStateOf("Meters")
    }

    var inputDropDownValue by remember {
        mutableStateOf(false)
    }

    var outputDropDownValue by remember {
        mutableStateOf(false)
    }

    var conversionFactorValue by remember {
        mutableStateOf(1.0)
    }

    var oConversionFactorValue by remember {
        mutableStateOf(1.0)
    }

    fun convertUnit() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result =
            (inputValueDouble * conversionFactorValue * 100.0 / oConversionFactorValue).roundToInt() / 100.0
        outputValue = result.toString()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Here All the UI element will Be stacked below Each other

        Text(
            text = "Unit Converter",
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertUnit()
            },
            label = { Text(text = "Enter Value") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Box {
                Button(onClick = { inputDropDownValue = true }) {
                    Text(text = inputUnitValue)
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "")
                }
                DropdownMenu(expanded = inputDropDownValue, onDismissRequest = {

                    inputDropDownValue = false
                }) {
                    DropdownMenuItem(text = { Text(text = "Meters") }, onClick = {
                        inputDropDownValue = false
                        inputUnitValue = "Meters"
                        conversionFactorValue = 1.0
                        convertUnit()
                    })
                    DropdownMenuItem(text = { Text(text = "Feet") }, onClick = {
                        inputDropDownValue = false
                        inputUnitValue = "Feet"
                        conversionFactorValue = 0.3048
                        convertUnit()
                    })
                    DropdownMenuItem(text = { Text(text = "Centimeters") }, onClick = {
                        inputDropDownValue = false
                        inputUnitValue = "Centimeters"
                        conversionFactorValue = 0.01
                        convertUnit()
                    })
                    DropdownMenuItem(text = { Text(text = "Millimeters") }, onClick = {
                        inputDropDownValue = false
                        inputUnitValue = "Millimeters"
                        conversionFactorValue = 0.001
                        convertUnit()
                    })
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = { outputDropDownValue = true }) {
                    Text(text = outputUnitValue)
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "")
                }
                DropdownMenu(
                    expanded = outputDropDownValue,
                    onDismissRequest = { outputDropDownValue = false }) {
                    DropdownMenuItem(text = { Text(text = "Meters") }, onClick = {
                        outputDropDownValue = false
                        outputUnitValue = "Meters"
                        oConversionFactorValue = 1.0
                        convertUnit()
                    })
                    DropdownMenuItem(text = { Text(text = "Feet") }, onClick = {
                        outputDropDownValue = false
                        outputUnitValue = "Feet"
                        oConversionFactorValue = 0.3048
                        convertUnit()
                    })
                    DropdownMenuItem(text = { Text(text = "Centimeters") }, onClick = {
                        outputDropDownValue = false
                        outputUnitValue = "Centimeters"
                        oConversionFactorValue = 0.01
                        convertUnit()
                    })
                    DropdownMenuItem(text = { Text(text = "Millimeters") }, onClick = {
                        outputDropDownValue = false
                        outputUnitValue = "Millimeters"
                        oConversionFactorValue = 0.001
                        convertUnit()
                    })
                }
            }


        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Result: $outputValue",
            style = TextStyle(fontFamily = FontFamily.SansSerif)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UnitConverterTheme {
        UnitConverter()
    }
}
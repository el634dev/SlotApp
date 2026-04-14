package com.example.slotapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slotapp.ui.theme.SlotAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SlotAppTheme {
                SlotAppScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlotAppScreen(modifier: Modifier = Modifier) {
    val coroutine = rememberCoroutineScope()
    var count by remember { mutableIntStateOf(0) }
    var count2 by remember { mutableIntStateOf(0) }

    var count3 by remember { mutableIntStateOf(0) }
    var speed by remember { mutableFloatStateOf(1000f) }
    var isCounting by remember { mutableStateOf(false) }

    var countJob: Job? by remember { mutableStateOf(null) }
    val countToThree = (count + 1) % 4
    val countToThree2 = (count2 + 1) % 4

    val countToThree3 = (count3 + 1) % 4
    var countJob2: Job? by remember { mutableStateOf(null) }
    var countJob3: Job? by remember { mutableStateOf(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "To-Do List") },
                actions = {
                    IconButton(
                        onClick = { countJob?.cancel() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "What app does"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxSize().padding(innerPadding)
        ) {
            Text(
                text = "Slot Machine",
                fontSize = 60.sp,
                modifier = modifier.padding(top = 40.dp)
            )
            Text(
                text = "$countToThree",
                fontSize = 120.sp
            )
            Text(
                text = "$countToThree2",
                fontSize = 120.sp
            )
            Text(
                text = "$countToThree3",
                fontSize = 120.sp
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Slider(
                    value = speed,
                    onValueChange = { speed = it },
                    valueRange = 100f..2000f,
                    modifier = Modifier.padding(horizontal = 80.dp)
                )
                Text(
                    text = "Speed: ${speed.toInt()}"
                )
            }
            Button(
                onClick = {
                    // Can be null and not call the function
                   if (isCounting) {
                       countJob?.cancel(); countJob2?.cancel(); countJob3?.cancel()
                   } else {
                       countJob = coroutine.launch {
                           while (true) {
                               //  Delay for a 1000
                               delay(timeMillis = speed.toLong())
                               count++
                           }
                       }
                      // -----------------* COROUTINE 2 *---------------------
                       countJob2 = coroutine.launch {
                           while (true) {
                               //  Delay for a 1000
                               delay(timeMillis = speed.toLong() + 150)
                               count2++
                           }
                       }
                       // -----------------* COROUTINE 3 *---------------------
                       countJob3 = coroutine.launch {
                           while (true) {
                               //  Delay for a 1000
                               delay(timeMillis = speed.toLong() + 300)
                               count3++
                           }
                       }
                   }
                    isCounting = !isCounting

                },
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                Text(text = if(isCounting) "Stop" else "Start")
            }
            Text(text = if(count == count2 && count2 == count3) "You won!" else "Keep spinning!")
        }
    }
}

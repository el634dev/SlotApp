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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slotapp.ui.theme.SlotAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SlotAppTheme {
                SlotMachineScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlotMachineScreen(modifier: Modifier = Modifier) {
    val coroutine = rememberCoroutineScope()
    val count = remember { mutableIntStateOf(0) }
    val count2 = remember { mutableIntStateOf(0) }

    val count3 = remember { mutableIntStateOf(0) }
    var job: Job? by remember { mutableStateOf(null) }
    var job2: Job? by remember { mutableStateOf(null) }

    var job3: Job? by remember { mutableStateOf(null) }
    var countSpeed by remember { mutableFloatStateOf(100f) }
    var isRunning  by remember { mutableStateOf(false) }

    var message by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Slot Machine") },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Click Start to spin"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Slot Machine",
                fontSize = 60.sp,
                modifier = Modifier.padding(top = 20.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
            ) {
                SlotImage(count = count.intValue)
                SlotImage(count = count2.intValue)
                SlotImage(count = count3.intValue)
            }
            // --------------------------------------------
            // Speed Slider
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Slider(
                    value = countSpeed,
                    onValueChange = { countSpeed = it },
                    valueRange = 100f..2000f,
                    modifier = Modifier.padding(horizontal = 80.dp)
                )
                Text(
                    text = "Speed: ${countSpeed.toInt()} ms"
                )
            }
            // --------------------------------------------
            // Button
            Button(
                onClick = {
                    if (isRunning) {
                        // Stop all coroutines
                        job?.cancel()
                        job2?.cancel()
                        job3?.cancel()
                        // Determine if user won (all three match)
                        message = if (count.intValue == count2.intValue && count2.intValue == count3.intValue) {
                            "You won!"
                        } else {
                            "Keep spinning!"
                        }
                        isRunning = false
                    } else {
                        // Start coroutines
                        job = coroutine.launch {
                            while (true) {
                                delay(countSpeed.toLong())
                                count.intValue = (count.intValue + 1) % 4
                            }
                        }
                        job2 = coroutine.launch {
                            delay(150)
                            while (true) {
                                delay(countSpeed.toLong())
                                count2.intValue = (count2.intValue + 1) % 4
                            }
                        }
                        job3 = coroutine.launch {
                            delay(200)
                            while (true) {
                                delay(countSpeed.toLong())
                                count3.intValue = (count3.intValue + 1) % 4
                            }
                        }
                        message = ""
                        isRunning = true
                    }
                },
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text(text = if (isRunning) "Stop" else "Start")
            }
            // Display message
            Text(
                text = message,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }
}

@Composable
fun SlotImage(count: Int){
    when(count) {
        0 -> {
            Image(
                painter = painterResource(R.drawable.cherry),
                contentDescription = "Cherry",
                modifier = Modifier.size(100.dp)
            )
        }
        //   ---------------------------------------
        1 -> {
            Image(
                painter = painterResource(R.drawable.grape),
                contentDescription = "Grape",
                modifier = Modifier.size(100.dp)
            )
        }
        //   ---------------------------------------
        2 -> {
            Image(
                painter = painterResource(R.drawable.pear),
                contentDescription = "Pear",
                modifier = Modifier.size(100.dp)
            )
        }
        //   ---------------------------------------
        3 -> {
            Image(
                painter = painterResource(R.drawable.strawberry),
                contentDescription = "Strawberry",
                modifier = Modifier.size(100.dp)
            )
        } else -> {
            //  Fallback
            Image(
                painter = painterResource(R.drawable.cherry),
                contentDescription = "Cherry",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}
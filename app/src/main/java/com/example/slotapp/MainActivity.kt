package com.example.slotapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
    var speed by remember { mutableFloatStateOf(1000f) }
    var isCounting by remember { mutableStateOf(false) }
    var countJob: Job? by remember { mutableStateOf(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Click Start") },
                actions = {
                    IconButton(
                        onClick = { countJob?.cancel() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear to-do list"
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
                text = "$count",
                fontSize = 120.sp
            )
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//
//            }
            if(isCounting) {
                Button(
                    onClick = {
                        isCounting = false
                        // Can be null and not call the function
                        countJob?.cancel()
                    },
                    modifier = Modifier.padding(bottom = 40.dp)
                ) {
                    Text(text = "Stop")
                }
            } else {
                Button(
                    onClick = {
                        isCounting = true
                        //  Runs counter in parallel with running the app
                        countJob = coroutine.launch(context = Dispatchers.Default) {
                            while(count <= 3) {
                                //  Delay for a 1000
                                delay(timeMillis = speed.toLong())
                                count++
                            }
                        }
                    },
                    modifier = Modifier.padding(bottom = 40.dp)
                ) {
                    Text(text = "Start!")
                }
            }
        }
    }
}

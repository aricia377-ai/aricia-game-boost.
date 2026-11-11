package com.aricia.gameboost

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rikka.shizuku.Shizuku

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        requestPermissions()

        setContent {
            AriciaTheme {
                MainScreen(
                    onShizukuRequest = { requestShizukuPermission() },
                    onCommandExecuted = { cmd -> executeShizukuCommand(cmd) }
                )
            }
        }
    }

    private fun requestPermissions() {
        if (!Settings.canDrawOverlays(this)) {
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")))
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (!notificationManager.isNotificationPolicyAccessGranted) {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS))
        }
    }

    private fun requestShizukuPermission() {
        if (Shizuku.checkSelfPermission() != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            Shizuku.requestPermission(1001)
        } else {
            Toast.makeText(this, "Shizuku is ready!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun executeShizukuCommand(command: String) {
        if (Shizuku.checkSelfPermission() != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Shizuku not authorized!", Toast.LENGTH_SHORT).show()
            return
        }
        Thread {
            try {
                val process = Shizuku.newProcess(arrayOf("sh", "-c", command), null, null)
                process.waitFor()
                runOnUiThread { Toast.makeText(this, "Optimization Applied", Toast.LENGTH_SHORT).show() }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}

@Composable
fun AriciaTheme(content: @Composable () -> Unit) {
    val darkGray = Color(0xFF181818)
    val lightGray = Color(0xFF282828)
    val accentGray = Color(0xFF606060)
    val textColor = Color(0xFFEEEEEE)

    MaterialTheme(
        colorScheme = darkColorScheme(
            background = darkGray, surface = lightGray, primary = accentGray,
            onPrimary = textColor, onBackground = textColor, onSurface = textColor
        ),
        content = content
    )
}

@Composable
fun MainScreen(onShizukuRequest: () -> Unit, onCommandExecuted: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text("ARICIA Game Boost", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(bottom = 8.dp, top = 24.dp))
            Button(onClick = onShizukuRequest, modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) { Text("Authorize Shizuku") }
        }

        item { SectionTitle("Display Refresh Rate") }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                listOf(60, 90, 120, 144).forEach { rate ->
                    OutlinedButton(onClick = { onCommandExecuted("settings put system min_refresh_rate $rate; settings put system peak_refresh_rate $rate") }) { Text("${rate}Hz") }
                }
            }
        }

        item { SectionTitle("Resolution Scale") }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { onCommandExecuted("wm size 720x1600; wm density 300") }) { Text("720p") }
                Button(onClick = { onCommandExecuted("wm size 1080x2400; wm density 420") }) { Text("1080p") }
                Button(onClick = { onCommandExecuted("wm size reset; wm density reset") }) { Text("Default") }
            }
        }

        item { SectionTitle("Touch Sensitivity") }
        item {
            var sliderPos by remember { mutableStateOf(1f) }
            Slider(value = sliderPos, onValueChange = { sliderPos = it }, valueRange = 1f..5f, steps = 3, onValueChangeFinished = { onCommandExecuted("settings put system pointer_speed ${sliderPos.toInt()}") })
            Text("Acceleration: ${sliderPos.toInt()}x", color = Color.Gray)
        }

        item { SectionTitle("CPU & Network") }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { onCommandExecuted("settings put global low_power 1") }) { Text("Saver") }
                Button(onClick = { onCommandExecuted("settings put global low_power 0; settings put global adaptive_battery_management_enabled 0") }) { Text("Max Perf") }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onCommandExecuted("settings put global private_dns_mode hostname; settings put global private_dns_specifier 1dot1dot1dot1.cloudflare-dns.com") }, modifier = Modifier.fillMaxWidth()) { Text("Max Network Speed (Cloudflare)") }
        }

        item { SectionTitle("RAM Boost") }
        item {
            Button(onClick = { onCommandExecuted("am kill-all; pm trim-caches 999999999") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A4A4A))) { Text("MAX BOOST & Clean RAM") }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(text = title, fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFAAAAAA), modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp))
}



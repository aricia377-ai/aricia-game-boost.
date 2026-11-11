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
            MaterialTheme(
                colorScheme = darkColorScheme(
                    background = Color(0xFF181818),
                    surface = Color(0xFF282828),
                    primary = Color(0xFF606060)
                )
            ) {
                MainScreen(
                    onShizukuRequest = { requestShizuku() },
                    onCommand = { cmd -> runCommand(cmd) }
                )
            }
        }
    }

    private fun requestPermissions() {
        if (!Settings.canDrawOverlays(this)) {
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")))
        }
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (!nm.isNotificationPolicyAccessGranted) {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS))
        }
    }

    private fun requestShizuku() {
        if (Shizuku.checkSelfPermission() != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            Shizuku.requestPermission(1001)
        } else {
            Toast.makeText(this, "Shizuku Ready!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun runCommand(cmd: String) {
        if (Shizuku.checkSelfPermission() != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Authorize Shizuku first!", Toast.LENGTH_SHORT).show()
            return
        }
        Thread {
            try {
                Shizuku.newProcess(arrayOf("sh", "-c", cmd), null, null).waitFor()
                runOnUiThread { Toast.makeText(this, "Applied!", Toast.LENGTH_SHORT).show() }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}

@Composable
fun MainScreen(onShizukuRequest: () -> Unit, onCommand: (String) -> Unit) {
    LazyColumn(
        Modifier.fillMaxSize().background(Color(0xFF181818)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text("ARICIA Game Boost", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(Modifier.height(16.dp))
            Button(onClick = onShizukuRequest, Modifier.fillMaxWidth()) { Text("Authorize Shizuku") }
            Spacer(Modifier.height(24.dp))
        }
        item { Text("Refresh Rate", color = Color.Gray) }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                listOf(60, 90, 120, 144).forEach { r ->
                    Button(onClick = { onCommand("settings put system min_refresh_rate $r; settings put system peak_refresh_rate $r") }) { Text("${r}Hz") }
                }
            }
        }
        item { Spacer(Modifier.height(16.dp)); Text("Resolution", color = Color.Gray) }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { onCommand("wm size 720x1600; wm density 300") }) { Text("720p") }
                Button(onClick = { onCommand("wm size reset; wm density reset") }) { Text("Default") }
            }
        }
        item { Spacer(Modifier.height(16.dp)); Text("Performance", color = Color.Gray) }
        item {
            Button(onClick = { onCommand("am kill-all; pm trim-caches 999G") }, Modifier.fillMaxWidth()) { Text("MAX RAM BOOST") }
            Spacer(Modifier.height(8.dp))
            Button(onClick = { onCommand("settings put global private_dns_mode hostname; settings put global private_dns_specifier 1dot1dot1dot1.cloudflare-dns.com") }, Modifier.fillMaxWidth()) { Text("Network Boost") }
        }
    }
}

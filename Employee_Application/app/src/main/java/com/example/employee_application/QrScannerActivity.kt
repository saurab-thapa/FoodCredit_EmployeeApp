package com.example.employee_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.employee_application.ui.theme.Employee_ApplicationTheme

class QrScannerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Employee_ApplicationTheme {
                Box(
                    modifier = Modifier.Companion.fillMaxSize(),
                    contentAlignment = Alignment.Companion.Center
                ) {
                    Text(text = "QR Scanner Screen")
                }
            }
        }
    }
}
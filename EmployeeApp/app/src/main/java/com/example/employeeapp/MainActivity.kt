package com.example.employeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.employeeapp.ui.screens.EmployeeLoginScreen
import com.example.employeeapp.ui.screens.SplashScreen
import com.example.employeeapp.ui.screens.QRScannerScreen
import com.example.employeeapp.ui.screens.UpdateMachineStockScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "splash"
                    )
                    {
                        composable("splash") {
                            SplashScreen(navController)
                        }
                        composable("employeeLogin") {
                            EmployeeLoginScreen(navController)
                        }
                        composable("qrScanner") {
                            QRScannerScreen(navController)
                        }
                        composable("updateMachine") {
                            UpdateMachineStockScreen()
                        }

                    }
                }
            }
        }
    }
}

package com.example.employeeapp.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults // Import ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

@androidx.annotation.OptIn(ExperimentalGetImage::class)
@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnsafeOptInUsageCamerax")
@Composable
fun QRScannerScreen(navController: NavController) {
    val context = LocalContext.current
    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)
    var qrCodeResult by remember { mutableStateOf<String?>(null) }

    // Define a light green color
    val LightGreen = Color(0xFF8BC34A) // A sample light green, you can adjust this hex code

    LaunchedEffect(Unit) {
        cameraPermission.launchPermissionRequest()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (cameraPermission.status.isGranted) {
            // Camera Preview View
            AndroidView(factory = { ctx ->
                val previewView = PreviewView(ctx).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }

                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = androidx.camera.core.Preview.Builder().build().apply {
                        setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val scanner = BarcodeScanning.getClient()
                    val analysis = ImageAnalysis.Builder().build().apply {
                        setAnalyzer(ContextCompat.getMainExecutor(ctx)) { imageProxy ->
                            val mediaImage = imageProxy.image
                            if (mediaImage != null) {
                                val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                                scanner.process(inputImage)
                                    .addOnSuccessListener { barcodes ->
                                        for (barcode in barcodes) {
                                            qrCodeResult = barcode.rawValue
                                            Log.d("QRScanner", "Scanned: $qrCodeResult")
                                            navController.popBackStack() // go back
                                        }
                                    }
                                    .addOnCompleteListener { imageProxy.close() }
                            } else {
                                imageProxy.close()
                            }
                        }
                    }

                    cameraProvider.bindToLifecycle(
                        context as androidx.lifecycle.LifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        analysis
                    )
                }, ContextCompat.getMainExecutor(ctx))

                previewView
            })
        } else {
            // Permission denied UI
            Text(
                "Camera permission is required to scan QR codes",
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Overlay UI from the screenshot
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Scan Machine QR",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

            // Instruction Text
            Text(
                text = "Position the QR code within the frame",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            // The QR code frame overlay with corners
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val frameSize = size.width * 0.7f
                    val topPadding = (size.height - frameSize) / 2f
                    val leftPadding = (size.width - frameSize) / 2f
                    val cornerLength = 50f
                    val strokeWidth = 8.dp.toPx()
                    val cornerColor = Color.White

                    // Top-left corner
                    drawLine(
                        color = cornerColor,
                        start = Offset(leftPadding, topPadding),
                        end = Offset(leftPadding + cornerLength, topPadding),
                        strokeWidth = strokeWidth
                    )
                    drawLine(
                        color = cornerColor,
                        start = Offset(leftPadding, topPadding),
                        end = Offset(leftPadding, topPadding + cornerLength),
                        strokeWidth = strokeWidth
                    )

                    // Top-right corner
                    drawLine(
                        color = cornerColor,
                        start = Offset(leftPadding + frameSize, topPadding),
                        end = Offset(leftPadding + frameSize - cornerLength, topPadding),
                        strokeWidth = strokeWidth
                    )
                    drawLine(
                        color = cornerColor,
                        start = Offset(leftPadding + frameSize, topPadding),
                        end = Offset(leftPadding + frameSize, topPadding + cornerLength),
                        strokeWidth = strokeWidth
                    )

                    // Bottom-left corner
                    drawLine(
                        color = cornerColor,
                        start = Offset(leftPadding, topPadding + frameSize),
                        end = Offset(leftPadding + cornerLength, topPadding + frameSize),
                        strokeWidth = strokeWidth
                    )
                    drawLine(
                        color = cornerColor,
                        start = Offset(leftPadding, topPadding + frameSize),
                        end = Offset(leftPadding, topPadding + frameSize - cornerLength),
                        strokeWidth = strokeWidth
                    )

                    // Bottom-right corner
                    drawLine(
                        color = cornerColor,
                        start = Offset(leftPadding + frameSize, topPadding + frameSize),
                        end = Offset(leftPadding + frameSize - cornerLength, topPadding + frameSize),
                        strokeWidth = strokeWidth
                    )
                    drawLine(
                        color = cornerColor,
                        start = Offset(leftPadding + frameSize, topPadding + frameSize),
                        end = Offset(leftPadding + frameSize, topPadding + frameSize - cornerLength),
                        strokeWidth = strokeWidth
                    )
                }
            }

            // Button and bottom text
            Button(
                onClick = { navController.navigate("updateMachine")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = LightGreen) // Set button color
            ) {
                Text(text = "Scan Machine QR")
            }
            Text(
                text = "Point your camera at the machine's QR code",
                color = LightGreen, // Set bottom text color to light green
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
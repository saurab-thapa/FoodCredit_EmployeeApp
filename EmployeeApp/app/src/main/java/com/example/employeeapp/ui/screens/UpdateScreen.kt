package com.example.employeeapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.employeeapp.R // Make sure this points to your R file

// A data class to represent a single stock item
data class StockItem(
    val id: String,
    val name: String,
    val quantity: Int,
    val imageUrl: Int, // Using an Int for a local drawable resource
    val price: Double
)

// Dummy data for the preview
val dummyStockItems = listOf(
    StockItem("A1", "Chocolate Bar", 15, R.drawable.chocolate_bar, 1.50),
    StockItem("A2", "Potato Chips", 12, R.drawable.potato_chips, 1.50),
    StockItem("B1", "Pepsi Cola", 20, R.drawable.pepsi, 1.00),
    StockItem("B2", "Water Bottle", 10, R.drawable.water_bottle, 1.25),
    StockItem("C1", "Sandwich", 5, R.drawable.sandwich, 3.50),
    StockItem("C2", "Energy Drink", 8, R.drawable.energy_drink, 2.75)
)

@Composable
fun UpdateMachineStockScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Top app bar with title and back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO: Handle back navigation */ }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Update Machine Stock",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Machine ID: MACHINE-001",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        // LazyVerticalGrid for the stock items
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(dummyStockItems) { item ->
                StockItemCard(item = item)
            }
        }

        // Save Updates button at the bottom
        Button(
            onClick = { /* TODO: Implement save logic */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8BC34A)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Save Updates", color = Color.White)
        }
    }
}

@Composable
fun StockItemCard(item: StockItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            // Product image
            Image(
                painter = painterResource(id = item.imageUrl),
                contentDescription = item.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                contentScale = ContentScale.Crop
            )

            // Content below the image
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                // Item ID (A1, A2, etc.)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .size(36.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item.id, color = Color.White, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                // Qty input
                var quantity by remember { mutableStateOf(item.quantity.toString()) }
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Qty") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Total Value and loyalty points
                Text(text = "Total Value")
                Text(text = "₹${item.price}", fontWeight = FontWeight.Bold)

                // Loyalty points
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // You'll need to create drawables or icons for Ruby, Silver, and Gold
                    Text("Ruby") // Placeholder
                    Text("Silver")
                    Text("Gold")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUpdateMachineStockScreen() {
    UpdateMachineStockScreen()
}
package com.example.apinote.app.ui.screens.parcelable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apinote.app.ui.navigation.ReceivingDataInfo
import com.example.apinote.app.ui.viewmodels.parcelable.ReceivingDataViewModel

@Composable
fun ReceivingDataScreen(
    receivingDataInfo: ReceivingDataInfo,
    receivingDataViewModel: ReceivingDataViewModel = hiltViewModel()
) {

    //It is executed when receivingDataInfo changes
    LaunchedEffect(receivingDataInfo) {
        receivingDataViewModel.setReceivingData(receivingDataInfo)
    }

    //.value because it is a State
    val data = receivingDataViewModel.receivingData.value ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF4EDCCE),
                        Color(0xFF2C5C7E)
                    )
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Data Screen",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                letterSpacing = 2.sp
            ),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(10.dp, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(16.dp)
            ) {
                DataRow(label = "Name", value = data.name)
                DataRow(label = "Surname", value = data.surname)
                DataRow(label = "Age", value = receivingDataViewModel.formatAge(data.age))
                DataRow(label = "Address", value = data.address)
                DataRow(label = "Identity Document", value = data.identityDocument)
                DataRow(label = "Email Address", value = data.emailAddress)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun DataRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = value,
            fontSize = 20.sp,
            color = if (value.isNotEmpty()) Color(0xFF0288D1) else Color.Transparent,
            modifier = Modifier
                .background(
                    color = if (value.isNotEmpty()) Color(0xFF81D4FA) else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(10.dp)
        )
    }
}

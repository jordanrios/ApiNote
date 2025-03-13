package com.example.apinote.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apinote.R
import com.example.apinote.app.ui.viewmodels.MenuViewModel

@Composable
fun MenuScreen(
    navigateToListScreen: () -> Unit,
    navigateToNotesScreen: () -> Unit,
    navigateToCharacterParcelableScreen: () -> Unit,
    menuViewModel: MenuViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        MenuSection(
            imageRes = R.drawable.api,
            text = "CharactersAPI",
            icon = Icons.Default.Person,
            onClick = navigateToListScreen
        )
        MenuSection(
            imageRes = R.drawable.notes,
            text = "Notes",
            icon = Icons.Default.Edit,
            onClick = navigateToNotesScreen
        )
        MenuSection(
            imageRes = R.drawable.parcelable,
            text = "CharacterParcelableScreen",
            icon = Icons.Default.Settings,
            onClick = navigateToCharacterParcelableScreen
        )
    }
}

@Composable
fun MenuSection(imageRes: Int, text: String, icon: ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f)),  // Darken background
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}
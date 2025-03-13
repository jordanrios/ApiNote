package com.example.apinote.app.ui.screens.api

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import com.example.apinote.app.core.utils.Constants
import com.example.apinote.app.domain.models.CharacterModel
import com.example.apinote.app.ui.viewmodels.api.DetailUiState
import com.example.apinote.app.ui.viewmodels.api.DetailViewModel

@Composable
fun DetailScreen(characterId: Int, detailViewModel: DetailViewModel = hiltViewModel()) {
    val uiState by detailViewModel.uiState.collectAsState()

    // Only recomposes when the ID changes
    LaunchedEffect(characterId) {
        detailViewModel.loadCharacter(characterId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val characterImageUrl = when (val state = uiState) {
            is DetailUiState.Success -> state.character.image
            else -> ""
        }

        if (characterImageUrl.isNotEmpty()) {
            AsyncImage(
                model = characterImageUrl,
                contentDescription = "Character background image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            )
        }
        // Diffuse layer
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(listOf(Color.Transparent, Color(0x99000000))),
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }

    when (uiState) {
        DetailUiState.Loading -> {
            LoadingScreen()
        }

        is DetailUiState.Error -> {
            ErrorScreen((uiState as DetailUiState.Error).throwable)
        }

        is DetailUiState.Success -> {
            CharacterContent((uiState as DetailUiState.Success).character)
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun ErrorScreen(error: Throwable) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Error: ${error.localizedMessage}",
            color = Color.Red,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun CharacterContent(character: CharacterModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .shadow(10.dp, shape = RoundedCornerShape(16.dp))
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = "Character image",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(4.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                ),
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Species: ${character.species}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Status: ${if (character.isAlive) "Alive" else "Dead"}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        fontSize = 16.sp
                    ),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = Constants.LOREM_IPSUM,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
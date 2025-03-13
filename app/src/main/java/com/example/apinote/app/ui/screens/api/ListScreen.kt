package com.example.apinote.app.ui.screens.api

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.apinote.app.domain.models.CharacterModel
import com.example.apinote.app.ui.viewmodels.api.ListViewModel
import com.example.apinote.R

@Composable
fun ListScreen(listViewModel: ListViewModel = hiltViewModel(), navigateToDetailScreen: (Int) -> Unit) {
    val characters = listViewModel.characters.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage()
        ContentManager(characters, navigateToDetailScreen)
    }
}

@Composable
fun BackgroundImage() {
    Image(
        painter = painterResource(id = R.drawable.stars),
        contentDescription = "stars",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ContentManager(characters: LazyPagingItems<CharacterModel>, navigateToDetailScreen: (Int) -> Unit) {
    when {
        characters.loadState.refresh is LoadState.Loading && characters.itemCount == 0 -> {
            LoadingScreen()
        }

        characters.loadState.refresh is LoadState.NotLoading && characters.itemCount == 0 -> {
            EmptyStateMessage()
        }

        characters.loadState.refresh is LoadState.Error -> {
            ErrorScreen((characters.loadState.refresh as LoadState.Error).error)
        }

        else -> {
            CharactersList(characters, navigateToDetailScreen)
            if (characters.loadState.append is LoadState.Loading) {
                LoadingMoreIndicator()
            }
        }
    }
}

@Composable
fun CharactersList(characters: LazyPagingItems<CharacterModel>, navigateToDetailScreen: (Int) -> Unit) {
    LazyColumn {
        items(characters.itemCount) {
            characters[it]?.let { characterModel ->
                ItemList(characterModel, onClick = navigateToDetailScreen)
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(64.dp), color = Color.White)
    }
}

@Composable
fun EmptyStateMessage() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Todavía no hay personajes", color = Color.White, fontSize = 18.sp)
    }
}

@Composable
private fun ErrorScreen(error: Throwable) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Error: ${error.localizedMessage}", color = Color.White, fontSize = 18.sp)
    }
}

@Composable
fun LoadingMoreIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(64.dp), color = Color.White)
    }
}

@Composable
fun ItemList(characterModel: CharacterModel, onClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .padding(24.dp)
            .clip(RoundedCornerShape(24))
            .border(2.dp, Color.Green, shape = RoundedCornerShape(0, 24, 0, 24))
            .fillMaxWidth()
            .height(250.dp)
            .clickable { onClick(characterModel.id) },
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = characterModel.image,
            contentDescription = "character image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0f),
                            Color.Black.copy(alpha = 0.6f),
                            Color.Black.copy(alpha = 1f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = characterModel.name, color = Color.White, fontSize = 18.sp)
        }
    }
}


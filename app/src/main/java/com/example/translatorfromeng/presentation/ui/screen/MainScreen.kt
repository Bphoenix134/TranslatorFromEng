package com.example.translatorfromeng.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.translatorfromeng.R
import com.example.translatorfromeng.presentation.ui.screen.component.TranslationItem
import com.example.translatorfromeng.presentation.viewmodel.MainViewModel
import com.example.translatorfromeng.presentation.viewmodel.state.UiState

@Composable
fun MainScreen(navController: NavHostController) {
    val viewModel: MainViewModel = hiltViewModel()
    val history by viewModel.history.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        var word by remember { mutableStateOf("") }
        TextField(value = word, onValueChange = { word = it }, label = { Text(stringResource(R.string.enter_word)) })
        Button(onClick = { viewModel.translate(word) }) { Text(stringResource(R.string.translate)) }

        when (uiState) {
            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            is UiState.Success -> Text(
                text = "${stringResource(R.string.translation)} ${(uiState as UiState.Success).translation.russianTranslation}",
                style = MaterialTheme.typography.bodyLarge
            )
            is UiState.Error -> Text(
                text = "${stringResource(R.string.error)} ${(uiState as UiState.Error).message}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
            else -> {}
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.history),
            style = MaterialTheme.typography.titleMedium
        )
        LazyColumn {
            items(history) { item ->
                TranslationItem(
                    translation = item,
                    onDelete = { viewModel.delete(item) },
                    onToggleFavorite = { viewModel.toggleFavorite(item) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("favorites") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.go_to_favorites))
        }
    }
}
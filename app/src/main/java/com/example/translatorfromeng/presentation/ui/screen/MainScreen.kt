package com.example.translatorfromeng.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.translatorfromeng.R
import com.example.translatorfromeng.presentation.ui.component.EmptyCardState
import com.example.translatorfromeng.presentation.ui.component.ErrorCard
import com.example.translatorfromeng.presentation.ui.component.ResultCard
import com.example.translatorfromeng.presentation.ui.component.TranslationItem
import com.example.translatorfromeng.presentation.viewmodel.MainViewModel
import com.example.translatorfromeng.presentation.viewmodel.state.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val viewModel: MainViewModel = hiltViewModel()
    val history by viewModel.history.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    var word by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.translator)) },
                actions = {
                    IconButton(onClick = { navController.navigate("favorites") }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_favorite),
                            contentDescription = stringResource(R.string.favorites)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = word,
                onValueChange = { word = it },
                label = { Text(
                    text = stringResource(R.string.enter_word),
                    style = MaterialTheme.typography.labelSmall
                    ) },
                trailingIcon = {
                    IconButton(onClick = {
                        if (word.isNotBlank()) viewModel.translate(word)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = stringResource(R.string.translate)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))
                when (uiState) {
                    is UiState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(
                            Alignment.CenterHorizontally
                        )
                    )

                    is UiState.Success -> ResultCard(
                        word,
                        (uiState as UiState.Success).translation.russianTranslation
                    )

                    is UiState.Error -> ErrorCard((uiState as UiState.Error).message)
                    else -> {}
                }

                Spacer(Modifier.height(16.dp))

            if (history.isEmpty()) {
                EmptyCardState(modifier = Modifier.padding(padding), R.string.no_history)
            } else {
                Text(
                    text = stringResource(R.string.history),
                    style = MaterialTheme.typography.bodyLarge
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                ) {
                    items(history) { item ->
                        TranslationItem(
                            translation = item,
                            onDelete = { viewModel.delete(item) },
                            onToggleFavorite = { viewModel.toggleFavorite(item) }
                        )
                    }
                }
            }
        }
    }
}
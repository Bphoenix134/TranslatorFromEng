package com.example.translatorfromeng.presentation.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.translatorfromeng.R
import com.example.translatorfromeng.domain.model.Translation

@Composable
fun TranslationItem(
    translation: Translation,
    onDelete: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${translation.englishWord} - ${translation.russianTranslation}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.add) + " " + java.text.SimpleDateFormat("dd/MM/yyyy").format(java.util.Date(translation.timestamp)),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row {
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        painter = painterResource(R.drawable.ic_favorite),
                        contentDescription = stringResource(R.string.remove_from_favorites)
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = stringResource(R.string.delete_translation)
                    )
                }
            }
        }
    }
}
package com.example.translatorfromeng.presentation.ui.component

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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TranslationItem(
    translation: Translation,
    onDelete: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                    text = "${translation.englishWord} → ${translation.russianTranslation}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        .format(Date(translation.timestamp)),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row {
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        painter = if (translation.isFavorite) {
                            painterResource(R.drawable.ic_favorite)
                        } else {
                            painterResource(R.drawable.ic_no_favorite)
                        },
                        contentDescription = if (translation.isFavorite) {
                            stringResource(R.string.remove_from_favorites)
                        } else {
                            stringResource(R.string.add_to_favorites)
                        },
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = stringResource(R.string.delete_translation),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
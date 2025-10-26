package com.sueta.main.presentation.ui.components.bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sueta.main.data.mapper.toStringAddress
import ru.dgis.sdk.directory.DirectoryObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointDetailsBottomSheet(
    point: DirectoryObject?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = point?.title ?: "Место",
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, "Закрыть")
                }
            }
            if (!point?.subtitle.isNullOrEmpty()) {
//                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = point.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (point != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Адрес",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = point.toStringAddress(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Text(
                text = "Коротко о месте",
                style = MaterialTheme.typography.bodyLarge,
//                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            val description =
                if (point?.description.isNullOrEmpty()) "Описание отстуствует"
                else point.description
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))


            // Рейтинг и отзывы (если есть)
//            place.metadata?.let { metadata ->
//                Spacer(modifier = Modifier.height(16.dp))
//                when (metadata) {
//                    is OrganizationMetadata -> {
//                        OrganizationDetails(metadata)
//                    }
//                    is AddressMetadata -> {
//                        AddressDetails(metadata)
//                    }
//                    // Добавь другие типы метаданных по необходимости
//                }
//            }


            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

//@Composable
//private fun OrganizationDetails(metadata: OrganizationMetadata) {
//    Column {
//        // Рейтинг
//        if (metadata.rating != null) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    imageVector = Icons.Default.Star,
//                    contentDescription = "Рейтинг",
//                    tint = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier.size(16.dp)
//                )
//                Spacer(modifier = Modifier.width(4.dp))
//                Text(
//                    text = "%.1f".format(metadata.rating),
//                    style = MaterialTheme.typography.bodyMedium
//                )
//            }
//        }
//
//        // Время работы
//        metadata.schedule?.let { schedule ->
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    imageVector = Icons.Default.Schedule,
//                    contentDescription = "Время работы",
//                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
//                    modifier = Modifier.size(16.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = getScheduleText(schedule),
//                    style = MaterialTheme.typography.bodySmall,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//            }
//        }
//
//        // Телефон
//        metadata.contactGroups?.firstOrNull()?.contacts?.firstOrNull()?.value?.let { phone ->
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    imageVector = Icons.Default.Phone,
//                    contentDescription = "Телефон",
//                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
//                    modifier = Modifier.size(16.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = phone,
//                    style = MaterialTheme.typography.bodySmall,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//            }
//        }
//    }
//}

//@Composable
//private fun AddressDetails(metadata: AddressMetadata) {
//    Text(
//        text = "Адрес",
//        style = MaterialTheme.typography.bodyMedium,
//        color = MaterialTheme.colorScheme.onSurfaceVariant
//    )
//}
//
//private fun getScheduleText(schedule: Schedule): String {
//    return when {
//        schedule.isAlwaysOpen -> "Круглосуточно"
//        schedule.isOpenNow -> "Открыто сейчас"
//        else -> "Закрыто"
//    }
//}
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sueta.main.presentation.MainContract
import com.sueta.main.presentation.ui.components.LabeledRadioButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailsBottomSheet(
    state: MainContract.State,
    onEventSent: (event: MainContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onEventSent(MainContract.Event.OnDismissHistoryBottomSheet) },
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
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "История",
                    style = MaterialTheme.typography.headlineSmall,
//                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                LabeledRadioButton(
                    selected = !state.historyBottomSheetState.isTopTen,
                    onSelect = { select ->
                        onEventSent(
                            MainContract.Event.HistoryEvent.ChangeHistoryTypeButtonClicked(
                                select
                            )
                        )
                    },
                    selectedText = "История",
                    unselectedText = "Популярные",
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Адрес",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = place?.address ?: "",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
            }

            LazyColumn(Modifier.fillMaxWidth()) {
                itemsIndexed(state.historyBottomSheetState.routes) { index, item ->
                    RouteItem(
                        item, "Маршрут" + (index + 1).toString(),
                        onClick = { onEventSent(MainContract.Event.HistoryEvent.OnHistoryItemClicked(item)) },
                    )
                }
            }



            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
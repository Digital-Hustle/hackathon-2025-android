package com.sueta.main.presentation.ui.components.bottom_sheet

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheetScaffold(
    scaffoldState: BottomSheetScaffoldState,
    sheetContent: @Composable (ColumnScope.() -> Unit),
    content: @Composable ((PaddingValues) -> Unit)

) {
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = sheetContent,
        sheetPeekHeight = 100.dp,
        sheetShadowElevation = 8.dp,
        sheetDragHandle = {},
        sheetTonalElevation = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = Color.Transparent,
        content = content
    )
}
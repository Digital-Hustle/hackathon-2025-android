package com.example.chat.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chats_holder.data.local.entities.MessageEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MessageItem(message: MessageEntity) {
    val cornerDp = 16.dp
    val cornerShape = if (message.isMyMessage) {
        RoundedCornerShape(cornerDp, cornerDp, 0.dp, cornerDp)
    } else {
        RoundedCornerShape(cornerDp, cornerDp, cornerDp, 0.dp)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = if (message.isMyMessage) Arrangement.End else Arrangement.Start
    ) {

        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.8f),
                    shape = cornerShape
                )
                .padding(12.dp)
                .widthIn(max = 280.dp)
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Spacer(Modifier.width(4.dp))
                Text(text = message.content, fontSize = 20.sp,softWrap = true,
                    overflow = TextOverflow.Clip,)
                Spacer(Modifier.width(16.dp))

                Text(
                    text = formatTime(message.timestamp),
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                     lineHeight = 10.sp, modifier =  Modifier.width(50.dp)
                )
                Spacer(Modifier.width(4.dp))


            }
        }


    }
}

fun formatTime(timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(date)
}
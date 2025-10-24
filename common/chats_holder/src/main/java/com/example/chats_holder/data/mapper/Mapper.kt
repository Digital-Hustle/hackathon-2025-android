package com.example.chats_holder.data.mapper

import com.example.chats_holder.data.local.entities.ChatEntity
import com.example.chats_holder.data.local.entities.MessageEntity
import com.example.chats_holder.domain.model.chats.ChatResponse
import com.example.chats_holder.domain.model.messages.MessageResponse
import com.example.core.mLog
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.Locale

fun ChatResponse.toChatEntity(): ChatEntity =
    ChatEntity(
        id = id,
        title = name ?: "",
//    lastMessage = TODO(),
//    timestamp = TODO()
        photo = photo
    )


fun List<ChatResponse>.toChatEntities(): List<ChatEntity> = this.map { it.toChatEntity() }


fun MessageResponse.toMessageEntity(chatId: Int, selfUserName: String): MessageEntity {
    val time = parseIsoDateToUnixMillis(timestamp)
    return MessageEntity(
        id = id.toLong(),
        chatId = chatId,
        senderName = senderName,
        content = text,
        timestamp = time,
        isMyMessage = selfUserName == senderName
    )
}
fun List<MessageResponse>.toMessageEntities(chatId: Int, selfUserName: String) =
    this.map { it.toMessageEntity(chatId, selfUserName) }


fun parseIsoDateToUnixMillis(dateTimeStr: String): Long {
    val formatter = DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
        .optionalStart()
        .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
        .optionalEnd()
        .toFormatter(Locale.getDefault())

    val localDateTime = LocalDateTime.parse(dateTimeStr, formatter)
    return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
}
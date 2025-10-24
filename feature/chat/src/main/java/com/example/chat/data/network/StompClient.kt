package com.example.chat.data.network

import com.example.chats_holder.data.local.dao.MessageDao
import com.example.chats_holder.data.mapper.toMessageEntity
import com.example.chats_holder.domain.model.messages.MessageResponse
import com.example.core.BuildConfig
import com.example.core.mLog
import com.example.network.storage.TokenManager
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import javax.inject.Inject

class StompClient @Inject constructor(
    private val client: OkHttpClient,
    private val tokenManager: TokenManager,
//    private val onMessageSent:(MessageResponse)->Unit,
    private val messageDao: MessageDao
) {

//    private val client = OkHttpClient.Builder()
//        .readTimeout(0, TimeUnit.MILLISECONDS)
//        .build()

    private var token: String? = null

    init {
        CoroutineScope(Dispatchers.IO).launch {
            tokenManager.getToken().collect { value ->
                token = value
            }

        }
    }

    private var chatId: Int? = null
    private var selfUserName: String? = null

    fun setChatId(id: Int) {
        chatId = id
    }

    fun setUserName(userName: String) {
        selfUserName = userName
    }


    private lateinit var webSocket: WebSocket

    fun connect() {
        val baseUrl = BuildConfig.SOCKET_BACKEND_URL
        val request = Request.Builder()
            .url(baseUrl)
//            .tag("no-auth")
            .build()
        webSocket = client.newWebSocket(request, StompWebSocketListener())
    }

    fun subscribeToChat(token: String) {
        val subscribeFrame = StompFrameManager.getSubscribeFrame(
            "sub-1",
            chatId = chatId ?: 0,
            token = token
        )
        sendStompMessage(subscribeFrame)
    }

    fun sendMessage(message: String) {
        token?.let {
            val sendFrame = StompFrameManager.getSendFrame(
                chatId = chatId ?: 0,
                message = message,
                senderName = selfUserName ?: "s",
                token = it
            )
            sendStompMessage(sendFrame)
        }

    }

    fun unsubscribeToChat() {
        token?.let {
            val subscribeFrame = StompFrameManager.getUnsubscribeFrame(
                it, "sub-1",

//            chatId = chatId?:0,
//            token = token
            )
            sendStompMessage(subscribeFrame)
        }

    }

    private fun sendStompMessage(message: String) {
        println(">> Отправка STOMP-сообщения:")
        println(message)
        webSocket.send(message)
    }


    private fun extractStompJsonBody(message: String): String {
        val splitIndex = message.indexOf("\n\n")
        if (splitIndex == -1) return ""

        val body = message.substring(splitIndex + 2)
        return body.trimEnd('\u0000') // удалим null-терминатор
    }

    fun disconnect() {
        unsubscribeToChat()
        val connectFrame = StompFrameManager.getDisconnectFrame()
        sendStompMessage(connectFrame)
        webSocket.close(
            1000,
            reason = "Всё четко"
        )
    }

    private inner class StompWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            println("✅ WebSocket открыт")
            val connectFrame = StompFrameManager.getConnectFrame()
            sendStompMessage(connectFrame)

        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            println("Получено сообщение:\n$text")

            val command = StompFrameManager.StompMessageType.getByValue(
                text
                    .lineSequence()
                    .firstOrNull()
                    ?.trim()
                    ?.uppercase()
            )
            when (command) {
                StompFrameManager.StompMessageType.CONNECTED -> token?.let { subscribeToChat(token = it) }
                StompFrameManager.StompMessageType.MESSAGE -> {
                    val json = extractStompJsonBody(text)
                    try {
                        val message = Gson().fromJson(json, MessageResponse::class.java)
                        println("✅ Распарсено сообщение: $message")

                        if (chatId != null && selfUserName != null) {
                            CoroutineScope(Dispatchers.IO).launch {
                                mLog("R", selfUserName.toString() + "sada")
                                messageDao.insertMessage(
                                    message.toMessageEntity(
                                        chatId!!,
                                        selfUserName!!
                                    )
                                )
                            }
                        }
                    } catch (e: Exception) {
                        println("Ошибка парсинга JSON: ${e.message}")
                    }
                }

                StompFrameManager.StompMessageType.ERROR -> mLog("mLogSOCKERRRR!!!", text)
                StompFrameManager.StompMessageType.UNKNOWN -> mLog("mLogSOCKUNONWN!!!", text)
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            println(" Получены байты:\n${bytes.hex()}")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            println(" WebSocket закрывается: $code / $reason")
            webSocket.close(1000, null)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            println(" Ошибка WebSocket: ${t.message}")
            t.printStackTrace()
        }
    }
}

object StompFrameManager {

    fun getConnectFrame() = buildString {
        append("CONNECT\n")
        append("accept-version:1.1,1.2\n")
        append("host:localhost\n")
        append("\n")
        append('\u0000')
    }

    fun getSubscribeFrame(subscriptionId: String, chatId: Int, token: String) = buildString {
        append("SUBSCRIBE\n")
        append("id:$subscriptionId\n")
        append("destination:/chat/$chatId\n")
        append("Authorization:Bearer $token")
        append("\n\n")
        append('\u0000')
    }

    fun getSendFrame(chatId: Int, message: String, senderName: String, token: String) =
        buildString {
            append("SEND\n")
            append("destination:/app/sendMessage\n")
            append("content-type:application/json\n")
            append("Authorization:Bearer $token")
            append("\n\n")
            append(
                "{\n" +
                        "  \"chatId\": $chatId,\n" +
                        "  \"messageText\": \"$message\",\n" +
                        "  \"senderName\": \"$senderName\"\n" +
                        "}"
            )
            append('\u0000')
        }

    fun getUnsubscribeFrame(token: String, subscriptionId: String) = buildString {
        append("UNSUBSCRIBE\n")
        append("id:$subscriptionId\n")
        append("Authorization:Bearer $token")
        append("\n")
        append('\u0000')
    }

    fun getDisconnectFrame() = buildString {
        append("DISCONNECT\n")
        append("\n")
        append('\u0000')
    }

    fun getAckFrame(messageId: String, subscriptionId: String) = buildString {
        append("ACK\n")
        append("id:$messageId\n")
        append("subscription:$subscriptionId\n")
        append("\n")
        append('\u0000')
    }

    fun getNackFrame(messageId: String, subscriptionId: String) = buildString {
        append("NACK\n")
        append("id:$messageId\n")
        append("subscription:$subscriptionId\n")
        append("\n")
        append('\u0000')
    }

    enum class StompMessageType(val value: String) {
        //        CONNECT("CONNECT"),
        CONNECTED("CONNECTED"),

        //        DISCONNECT("DISCONNECT"),
        MESSAGE("MESSAGE"),

        //        SUBSCRIBE("SUBSCRIBE"),
//        UNSUBSCRIBE("UNSUBSCRIBE"),
//        SEND("SEND"),
        ERROR("ERROR"),
        UNKNOWN("UNKNOWN");

        companion object {
            fun getByValue(value: String?): StompMessageType {
                return values().firstOrNull { it.value.equals(value, ignoreCase = true) }
                    ?: UNKNOWN
            }
        }

    }

}
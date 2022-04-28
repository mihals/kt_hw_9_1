package ru.netology

data class Chater(val id: Int, val name: String)

data class Message(
    val id: Int,
    val chatId: Int,
    val senderId: Int,
    val recipientId: Int,
    val message: String,
    var isReaded: Boolean = false,
    var isDeleted: Boolean = false
)

data class Chat(
    val id: Int,
    val ownerId: Int,
    val recipientId: Int,
    var messagList: MutableList<Message>,
    var isDeleted: Boolean = false,
)

class MessagerService {
    private var chaterList: List<Chater> = mutableListOf()
    private var chatList: MutableList<Chat> = mutableListOf()
    private var globalChaterId = 0
    private var globalChatId = 0
    private var globalMessageId = 0

    init {
        chaterList += Chater(++globalChaterId, "Ann")
        chaterList += Chater(++globalChaterId, "Bob")
        chaterList += Chater(++globalChaterId, "Cat")


        for (i in 1..3) {
            addMessage(
                chaterList[0].id, chaterList[1].id,
                "${chaterList[0].name} sent message number $i to ${chaterList[1].name} "
            )
            addMessage(
                chaterList[1].id, chaterList[0].id,
                "${chaterList[1].name} sent message number $i to ${chaterList[0].name} "
            )
        }
    }

    private fun addChat(ownerId: Int, recipientId: Int): Chat {
        val chat = Chat(
            ++globalChatId,
            ownerId = ownerId,
            recipientId = recipientId,
            messagList = mutableListOf()
        )

        chatList += chat
        return chat
    }

    fun deleteChatById(chatId: Int) {
        chatList.removeAll(fun(chat: Chat) = chat.id == chatId)
    }

    fun getChats(ownerId: Int): List<Chat> {
        val list: List<Chat> = chatList.filter {
            it.messagList.any(fun(message: Message) =
                (message.senderId != ownerId) && !message.isReaded)
        }
        if (list.isEmpty())
            println("Нет сообщений.")
        return list
    }

    fun getUnreadChatsCount(ownerId: Int): Int {
        return chatList.filter(fun(chat: Chat) =
            chat.messagList.any(fun(message: Message) = !message.isReaded &&
                    message.recipientId == ownerId)).size
    }

    fun getUnreadMessages(ownerId: Int, chatId: Int, fromIndex: Int = 0, count: Int = 3): List<Message>? {
        var indexList: MutableList<Int> = mutableListOf()
        val predik = fun(index: Int, message: Message): Boolean {
            if (message.recipientId == ownerId &&
                message.isReaded == false
            ) {
                indexList += index
                return true
            }
            return false
        }

        var chatIndex: Int = chatList.indexOf(
            chatList.find(fun(chat: Chat) = chat.id == chatId))


        val list = chatList[chatIndex].messagList?.filterIndexed(predik)

        if (fromIndex > indexList.lastIndex) return null
        val toIndex = if (fromIndex + count - 1 > indexList.lastIndex) {
            indexList.lastIndex + 1
        } else {
            fromIndex + count
        }

        indexList.subList(fromIndex, toIndex).forEach {
            chatList[chatIndex].messagList[it].isReaded = true
        }

        return list.subList(fromIndex, toIndex)
    }

    fun addMessage(senderId: Int, recipientId: Int, message: String) {
        val predik = fun(chat: Chat) =
            ((chat.ownerId == senderId && chat.recipientId == recipientId) ||
                    (chat.recipientId == senderId && chat.ownerId == recipientId))

        val chat: Chat = if (chatList.find(predik) != null) {
            chatList.find(predik)!!
        } else {
            addChat(senderId, recipientId)
        }
        chat.messagList += Message(++globalMessageId, chat.id, senderId, recipientId, message)
    }

    fun deleteMessage(messageId: Int) {
        var message: Message? = null
        var selectedChat: Chat? = null
        chatList.find { it ->
            it.messagList.any {
                message = it
                it.id == messageId
            }
        }?.apply {
            selectedChat = this
            messagList?.remove(message)
        }

        if (selectedChat?.messagList?.isEmpty() == true)
            chatList.remove(selectedChat)
    }
}

fun main() {
}
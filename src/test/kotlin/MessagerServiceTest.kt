import org.junit.Assert.*
import ru.netology.Chat
import ru.netology.Message
import ru.netology.MessagerService

class MessagerServiceTest {

    @org.junit.Test
    fun getChats_before_and_after_reading() {
        val service = MessagerService()
        val numChatsBefore = service.getChats(1).size
        service.getUnreadMessages(1, 1, 0, 10)
        val numChatsAfter = service.getChats(1).size
        assertTrue(numChatsBefore == 1 && numChatsAfter == 0)
    }

    @org.junit.Test
    fun getChats_before_and_after_reading_by_recipient() {
        val service = MessagerService()
        val сhatsBefore = service.getChats(1)
        service.getUnreadMessages(2, 1, 0, 10)
        val сhatsAfter = service.getChats(1)
        assertTrue(сhatsBefore.equals(сhatsAfter))
    }

    @org.junit.Test
    fun getUnreadChatsCount_if_not_exist() {
        val service = MessagerService()
        val numChat = service.getUnreadChatsCount(3)
        assertEquals(0, numChat)
    }

    @org.junit.Test
    fun getUnreadChatsCount_before_and_after_reading() {
        val service = MessagerService()
        val numChatBefore = service.getUnreadChatsCount(1)
        service.getUnreadMessages(1, 1, 0, 10)
        val numChatsAfter = service.getChats(1).size
        assertTrue(numChatBefore == 1 && numChatsAfter == 0)
    }

    @org.junit.Test
    fun getUnreadChatsCount_before_and_after_reading_by_recipient() {
        val service = MessagerService()
        val numChatBefore = service.getUnreadChatsCount(1)
        service.getUnreadMessages(2, 1, 0, 10)
        val numChatsAfter = service.getChats(1).size
        assertTrue(numChatBefore == numChatsAfter)
    }


    @org.junit.Test
    fun getUnreadMessages_after_recipient() {
        val service = MessagerService()
        val numMsgBefore = service.getUnreadMessages(1, 1, 0, 10)?.size
        val numMsgAfter = service.getUnreadMessages(1, 1, 0, 10)?.size
        assertTrue(numMsgBefore == 3 && numMsgAfter == null)
    }

    @org.junit.Test
    fun getUnreadMessages_with_bad_startInd() {
        val service = MessagerService()
        val list: List<Message>? = service.getUnreadMessages(1, 1, 10, 20)
        assertNull(list)
    }

    @org.junit.Test
    fun getUnreadMessages_with_bad_lastInd() {
        val service = MessagerService()
        val list: List<Message>? = service.getUnreadMessages(1, 1, 1, 20)
        assertEquals(2, list?.size)
    }

    @org.junit.Test
    fun getUnreadMessages_with_correct_params() {
        val service = MessagerService()
        val list: List<Message>? = service.getUnreadMessages(1, 1, 1, 2)
        assertEquals(2, list?.size)
    }

    @org.junit.Test
    fun addMessage_to_unexisting_chat() {
        val service = MessagerService()
        val numChatsBefore = service.getUnreadChatsCount(3)
        service.addMessage(1, 3, " Test message from chater 1 to 3")
        val numChatsAfter = service.getUnreadChatsCount(3)
        assertTrue(numChatsBefore == 0 && numChatsAfter == 1)
    }

    @org.junit.Test
    fun addMessage_to_existing_chat() {
        val service = MessagerService()
        val numChatsBefore = service.getUnreadChatsCount(2)
        service.addMessage(1, 2, " Test message from chater 1 to 2")
        val numChatsAfter = service.getUnreadChatsCount(2)
        assertTrue(numChatsBefore == numChatsAfter)
    }

    @org.junit.Test
    fun deleteMessage() {
        val service = MessagerService()
        service.deleteMessage(2)
        val numMessage = service.getUnreadMessages(1, 1, 0, 10)?.size
        assertEquals(2, numMessage)
    }

    @org.junit.Test
    fun delete_last_message_with_chat() {
        val service = MessagerService()
        service.addMessage(3, 2, "Test message")
        val numChatsBefore = service.getUnreadChatsCount(2)
        service.deleteMessage(7)
        val numChatsAfter = service.getUnreadChatsCount(2)
        assertTrue(numChatsBefore == 2 && numChatsAfter == 1)
    }


    @org.junit.Test
    fun deleteChatById() {
        val service = MessagerService()
        service.deleteChatById(1)
        val numChats = service.getUnreadChatsCount(1)
        assertEquals(0,numChats)
    }

}
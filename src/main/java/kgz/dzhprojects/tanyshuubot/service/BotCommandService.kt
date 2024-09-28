package kgz.dzhprojects.tanyshuubot.service

import kgz.dzhprojects.tanyshuubot.bot.TelegramBot
import kgz.dzhprojects.tanyshuubot.enum.BotCommandsEnum
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

private val logger = KotlinLogging.logger {}

@Service
class BotCommandService {

    private lateinit var bot: TelegramBot

    fun setBot(bot: TelegramBot) {
        this.bot = bot
    }

    fun initCommandMenu() {
        val listOfCommands = buildListOfCommands()
        try {
            bot.execute(SetMyCommands(listOfCommands, BotCommandScopeDefault(), null))
        } catch (e: TelegramApiException) {
            logger.error("Не получилось установить команды: " + e.message)
        }
    }

    fun handleCommand(update: Update) {
        if (isValidMessage(update)) {
            val message = update.message
            val messageText: String = message.text
            val chat: Chat = message.chat
            when (messageText) {
                BotCommandsEnum.START.command -> start(chat)

                else -> unknownCommand(chat)
            }
        } else {
            logger.warn("невалидный ввод")
        }
    }

    private fun isValidMessage(update: Update) = update.let {
        it.hasMessage() && it.message.hasText()
    }

    private fun buildListOfCommands() = BotCommandsEnum.values().map {
        BotCommand(it.command, it.description)
    }

    private fun unknownCommand(chat: Chat) {
        val chatId = chat.id
        val userName = chat.userName
        val responseText = "Неизвестная команда"
        sendMessage(chatId, responseText)
    }

    private fun start(chat: Chat) {
        val chatId = chat.id
        val userName = chat.firstName
        val responseText = """
            Привет, $userName!
            Этот бот поможет тебе найти людей, которые совместимы с тобой на основании твоих пользовательских данных.
            """.trimIndent()
        sendMessage(chatId, responseText)
    }

    private fun sendMessage(chatId: Long, responseText: String) {
        val responseMessage = SendMessage(chatId.toString(), responseText)
        try {
            bot.execute(responseMessage)
        } catch (e: TelegramApiException) {
            System.err.println("Возникла ошибка при отправке сообщения пользователю: " + e.message)
        }
    }
}
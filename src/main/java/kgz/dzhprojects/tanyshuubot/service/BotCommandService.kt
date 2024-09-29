package kgz.dzhprojects.tanyshuubot.service

import kgz.dzhprojects.tanyshuubot.bot.TelegramBot
import kgz.dzhprojects.tanyshuubot.enum.BotCommandEnum
import kgz.dzhprojects.tanyshuubot.enum.InterestCallbackEnum
import kgz.dzhprojects.tanyshuubot.model.User
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

private val logger = KotlinLogging.logger {}

private const val YES_READY = "YES_READY"
private const val YES = "YES"
private const val NO = "NO"

@Service
class BotCommandService(
    private val userService: UserService,
) {

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
                BotCommandEnum.START.command -> start(chat)

                BotCommandEnum.ADD_OR_EDIT_MY_DATA.command -> addOrEditMyData(chat)

                BotCommandEnum.SHOW_MY_DATA.command -> showMyData(chat)

                BotCommandEnum.DELETE_MY_DATA.command -> deleteMyData(chat)

                BotCommandEnum.SHOW_COMPATIBLE_USERS.command -> showCompatibleUsers(chat)

                BotCommandEnum.HELP.command -> help(chat)

                else -> unknownCommand(chat)
            }
        } else {
            if (update.hasCallbackQuery()) {
                val callbackData = update.callbackQuery.data
                val messageId = update.callbackQuery.message.messageId.toLong()
                val chatId = update.callbackQuery.message.chatId
                val user = userService.getUser(chatId)!!

                when (callbackData) {
                    YES_READY -> showQuestion(chatId, messageId, InterestCallbackEnum.SPORT)
                    InterestCallbackEnum.SPORT.yes -> {
                        userService.saveUser(user.id, user.name, true)
                        showQuestion(chatId, messageId, InterestCallbackEnum.READING)
                    }
                    InterestCallbackEnum.SPORT.no -> {
                        userService.saveUser(user.id, user.name, false)
                        showQuestion(chatId, messageId, InterestCallbackEnum.READING)
                    }
                    InterestCallbackEnum.READING.yes -> {
                        userService.saveUser(user.id, user.name, user.isLikesSport, true)
                        showQuestion(chatId, messageId, InterestCallbackEnum.ACTIVE_REST)
                    }
                    InterestCallbackEnum.READING.no -> {
                        userService.saveUser(user.id, user.name, user.isLikesSport, false)
                        showQuestion(chatId, messageId, InterestCallbackEnum.ACTIVE_REST)
                    }
                    InterestCallbackEnum.ACTIVE_REST.yes -> {
                        userService.saveUser(user.id, user.name, user.isLikesSport, user.isLikesReading, true)
                        showQuestion(chatId, messageId, InterestCallbackEnum.ANIMALS)
                    }
                    InterestCallbackEnum.ACTIVE_REST.no -> {
                        userService.saveUser(user.id, user.name, user.isLikesSport, user.isLikesReading, false)
                        showQuestion(chatId, messageId, InterestCallbackEnum.ANIMALS)
                    }
                    InterestCallbackEnum.ANIMALS.yes -> {
                        userService.saveUser(
                            user.id,
                            user.name,
                            user.isLikesSport,
                            user.isLikesReading,
                            user.isLikesActiveRest,
                            true
                        )
                        showQuestion(chatId, messageId, InterestCallbackEnum.MOVIES_AND_SERIES)
                    }
                    InterestCallbackEnum.ANIMALS.no -> {
                        userService.saveUser(
                            user.id,
                            user.name,
                            user.isLikesSport,
                            user.isLikesReading,
                            user.isLikesActiveRest,
                            false
                        )
                        showQuestion(chatId, messageId, InterestCallbackEnum.MOVIES_AND_SERIES)
                    }
                    InterestCallbackEnum.MOVIES_AND_SERIES.yes -> {
                        userService.saveUser(
                            user.id,
                            user.name,
                            user.isLikesSport,
                            user.isLikesReading,
                            user.isLikesActiveRest,
                            user.isLikesAnimals,
                            true
                        )
                        showQuestion(chatId, messageId, InterestCallbackEnum.NEWS_AND_POLITICS)
                    }
                    InterestCallbackEnum.MOVIES_AND_SERIES.no -> {
                        userService.saveUser(
                            user.id,
                            user.name,
                            user.isLikesSport,
                            user.isLikesReading,
                            user.isLikesActiveRest,
                            user.isLikesAnimals,
                            false
                        )
                        showQuestion(chatId, messageId, InterestCallbackEnum.NEWS_AND_POLITICS)
                    }
                    InterestCallbackEnum.NEWS_AND_POLITICS.yes -> {
                        userService.saveUser(
                            user.id,
                            user.name,
                            user.isLikesSport,
                            user.isLikesReading,
                            user.isLikesActiveRest,
                            user.isLikesAnimals,
                            user.isLikesMoviesSeries,
                            true
                        )
                        showQuestion(chatId, messageId, InterestCallbackEnum.STUDY)
                    }
                    InterestCallbackEnum.NEWS_AND_POLITICS.no -> {
                        userService.saveUser(
                            user.id,
                            user.name,
                            user.isLikesSport,
                            user.isLikesReading,
                            user.isLikesActiveRest,
                            user.isLikesAnimals,
                            user.isLikesMoviesSeries,
                            false
                        )
                        showQuestion(chatId, messageId, InterestCallbackEnum.STUDY)
                    }
                    InterestCallbackEnum.STUDY.yes -> {
                        userService.saveUser(
                            user.id,
                            user.name,
                            user.isLikesSport,
                            user.isLikesReading,
                            user.isLikesActiveRest,
                            user.isLikesAnimals,
                            user.isLikesMoviesSeries,
                            user.isLikesNewsPolitics,
                            true
                        )
                        showQuestion(chatId, messageId, InterestCallbackEnum.WALKING)
                    }
                    InterestCallbackEnum.STUDY.no -> {
                        userService.saveUser(
                            user.id,
                            user.name,
                            user.isLikesSport,
                            user.isLikesReading,
                            user.isLikesActiveRest,
                            user.isLikesAnimals,
                            user.isLikesMoviesSeries,
                            user.isLikesNewsPolitics,
                            false
                        )
                        showQuestion(chatId, messageId, InterestCallbackEnum.WALKING)
                    }
                    InterestCallbackEnum.WALKING.yes -> {
                        userService.saveUser(
                            user.id,
                            user.name,
                            user.isLikesSport,
                            user.isLikesReading,
                            user.isLikesActiveRest,
                            user.isLikesAnimals,
                            user.isLikesMoviesSeries,
                            user.isLikesNewsPolitics,
                            user.isLikesStudy,
                            true
                        )
                        showQuestion(chatId, messageId, InterestCallbackEnum.EAT)
                    }
                    InterestCallbackEnum.WALKING.no -> {
                        userService.saveUser(
                            user.id,
                            user.name,
                            user.isLikesSport,
                            user.isLikesReading,
                            user.isLikesActiveRest,
                            user.isLikesAnimals,
                            user.isLikesMoviesSeries,
                            user.isLikesNewsPolitics,
                            user.isLikesStudy,
                            false
                        )
                        showQuestion(chatId, messageId, InterestCallbackEnum.EAT)
                    }
                    InterestCallbackEnum.EAT.yes -> {
                        userService.saveUser(
                            user.id,
                            user.name,
                            user.isLikesSport,
                            user.isLikesReading,
                            user.isLikesActiveRest,
                            user.isLikesAnimals,
                            user.isLikesMoviesSeries,
                            user.isLikesNewsPolitics,
                            user.isLikesStudy,
                            user.isLikesWalking,
                            true
                        )
                        showQuestion(chatId, messageId, InterestCallbackEnum.COOK)
                    }
                    InterestCallbackEnum.EAT.no -> {
                        userService.saveUser(
                            user.id,
                            user.name,
                            user.isLikesSport,
                            user.isLikesReading,
                            user.isLikesActiveRest,
                            user.isLikesAnimals,
                            user.isLikesMoviesSeries,
                            user.isLikesNewsPolitics,
                            user.isLikesStudy,
                            user.isLikesWalking,
                            false
                        )
                        showQuestion(chatId, messageId, InterestCallbackEnum.COOK)
                    }
                    InterestCallbackEnum.COOK.yes -> {
                        userService.saveUser(
                            user.id,
                            user.name,
                            user.isLikesSport,
                            user.isLikesReading,
                            user.isLikesActiveRest,
                            user.isLikesAnimals,
                            user.isLikesMoviesSeries,
                            user.isLikesNewsPolitics,
                            user.isLikesStudy,
                            user.isLikesWalking,
                            user.isLikesToEat,
                            true
                        )
                        user.isLikesToCook = true
                        showDataSettingResult(chatId, messageId, user = user)
                    }
                    InterestCallbackEnum.COOK.no -> {
                        userService.saveUser(
                            user.id,
                            user.name,
                            user.isLikesSport,
                            user.isLikesReading,
                            user.isLikesActiveRest,
                            user.isLikesAnimals,
                            user.isLikesMoviesSeries,
                            user.isLikesNewsPolitics,
                            user.isLikesStudy,
                            user.isLikesWalking,
                            user.isLikesToEat,
                            false
                        )
                        user.isLikesToCook = false
                        showDataSettingResult(chatId, messageId, user = user)
                    }
                }
            } else {
                logger.warn("Невалидный ввод")
            }
        }
    }

    private fun start(chat: Chat) {
        val chatId = chat.id
        val firstName = chat.firstName
        val responseText = """
            Приветствую, <b>$firstName</b>!
            
            Я помогу найти людей в сервисе, которые совместимы с Вами на основании Ваших пользовательских данных
            
            Для анализа совместимости мне требуются Ваши данные
            
            Чтобы их добавить, воспользуйтесь командой <b>/add_or_edit_my_data</b>
            
            А также можете ознакомиться со подробным описанием всех команд с помощью <b>/help</b>
            """.trimIndent()
        executeSendMessage(chatId, responseText)
    }

    private fun addOrEditMyData(chat: Chat) {
        val chatId = chat.id
        val firstName = chat.firstName
        val userName = chat.userName
        userService.saveUser(chatId, userName)
        val firstResponseText = """
            Отлично, <b>$firstName</b>!
            
            Готовы добавить данные, чтобы можно было проанализировать совместимость с другими пользователями?
            """.trimIndent()

        val responseMessage = SendMessage(chatId.toString(), firstResponseText)
        val markupLine = InlineKeyboardMarkup()
        val rowsInline: MutableList<MutableList<InlineKeyboardButton>> = mutableListOf()
        val rowInline: MutableList<InlineKeyboardButton> = mutableListOf()
        val yesButton = InlineKeyboardButton().apply {
            this.text = YES_READY
            this.callbackData = YES_READY
        }
        rowInline.add(yesButton)
        rowsInline.add(rowInline)
        markupLine.keyboard = rowsInline
        responseMessage.replyMarkup = markupLine
        try {
            bot.execute(responseMessage)
        } catch (e: TelegramApiException) {
            logger.error("Возникла ошибка при отправке сообщения пользователю: " + e.message)
        }
    }

    private fun showMyData(chat: Chat) {
        val chatId = chat.id
        val user = userService.getUser(chatId)
        val userNotFoundMessage = """
            Ваших данных нет в сервисе
            
            Чтобы их добавить, воспользуйтесь командой <b>/add_or_edit_my_data</b>
        """.trimIndent()
        user?.let {
            executeSendMessage(chatId, user.toString())
        } ?: executeSendMessage(chatId, userNotFoundMessage)
    }

    private fun deleteMyData(chat: Chat) {
        val chatId = chat.id
        val userName = chat.userName
        val user = userService.getUser(chatId)
        var messageToSend = ""
        if (user != null) {
            userService.removeUser(chatId)
            messageToSend = "<b>$userName</b>, Ваши пользовательские данные  успешно удалены"
            executeSendMessage(chatId, messageToSend)
        } else {
            messageToSend = "Ваших данных итак нет в сервисе"
            executeSendMessage(chatId, messageToSend)
        }
    }

    private fun showCompatibleUsers(chat: Chat) {
        val chatId = chat.id
        val user = userService.getUser(chatId)
        var messageToSend = ""
        if (user == null) {
            messageToSend = """
                У Вас отсутствуют данные для поиска совместимых пользователей
                
                Чтобы их добавить, воспользуйтесь командой <b>/add_or_edit_my_data</b>
            """.trimIndent()
            executeSendMessage(chatId, messageToSend)
            return
        }

        messageToSend = "<b>По спортивному интересу:\n</b>"
        var usersByInterest = getUsersNames(user.id, user.isLikesSport, InterestCallbackEnum.SPORT)
        usersByInterest.forEach {
            messageToSend += it + "\n"
        }

        messageToSend += "<b>\nПо читательскому интересу:\n</b>"
        usersByInterest = getUsersNames(user.id, user.isLikesReading, InterestCallbackEnum.READING)
        usersByInterest.forEach {
            messageToSend += it + "\n"
        }

        messageToSend += "<b>\nПо активному отдыху:\n</b>"
        usersByInterest = getUsersNames(user.id, user.isLikesActiveRest, InterestCallbackEnum.ACTIVE_REST)
        usersByInterest.forEach {
            messageToSend += it + "\n"
        }

        messageToSend += "<b>\nПо любви к животным:\n</b>"
        usersByInterest = getUsersNames(user.id, user.isLikesAnimals, InterestCallbackEnum.ANIMALS)
        usersByInterest.forEach {
            messageToSend += it + "\n"
        }

        messageToSend += "<b>\nПо любви к сериалам и фильмам:\n</b>"
        usersByInterest = getUsersNames(user.id, user.isLikesMoviesSeries, InterestCallbackEnum.MOVIES_AND_SERIES)
        usersByInterest.forEach {
            messageToSend += it + "\n"
        }

        messageToSend += "<b>\nПо интересу к новостям и политике:\n</b>"
        usersByInterest = getUsersNames(user.id, user.isLikesNewsPolitics, InterestCallbackEnum.NEWS_AND_POLITICS)
        usersByInterest.forEach {
            messageToSend += it + "\n"
        }

        messageToSend += "<b>\nПо желанию у учебе и саморазвитию:\n</b>"
        usersByInterest = getUsersNames(user.id, user.isLikesStudy, InterestCallbackEnum.STUDY)
        usersByInterest.forEach {
            messageToSend += it + "\n"
        }

        messageToSend += "<b>\nПо интересу к легким прогулкам:\n</b>"
        usersByInterest = getUsersNames(user.id, user.isLikesWalking, InterestCallbackEnum.WALKING)
        usersByInterest.forEach {
            messageToSend += it + "\n"
        }

        messageToSend += "<b>\nПо любви вкусно покушать:\n</b>"
        usersByInterest = getUsersNames(user.id, user.isLikesToEat, InterestCallbackEnum.EAT)
        usersByInterest.forEach {
            messageToSend += it + "\n"
        }

        messageToSend += "<b>\nПо интересу готовки:\n</b>"
        usersByInterest = getUsersNames(user.id, user.isLikesToCook, InterestCallbackEnum.COOK)
        usersByInterest.forEach {
            messageToSend += it + "\n"
        }

        executeSendMessage(chatId, messageToSend)
    }

    private fun help(chat: Chat) {
        val chatId = chat.id
        val responseText = """
            Подробное описание каждой команды:
            
            <b>/start</b> - приветствие и описание бота
                
            <b>/add_or_edit_my_data</b> - добавить ваши пользовательские данные, чтобы можно было найти совместимых пользователей.
            если данные уже есть, то при желании вы их также можете обновить

            <b>/show_my_data</b> - посмотреть свои пользовательские данные. если данных нет, то их надо бы завести
                
            <b>/delete_my_data</b> - удалить свои пользовательские данные. если данные не были заведены, то команда ни к чему не приведет
                
            <b>/show_compatible_users</b> - показать совместимых пользователей на основании ваших данных
                
            <b>/help</b> - подробная информация по командам 
            """.trimIndent()
        executeSendMessage(chatId, responseText)
    }

    private fun isValidMessage(update: Update) = update.let {
        it.hasMessage() && it.message.hasText()
    }

    private fun buildListOfCommands() = BotCommandEnum.values().map {
        BotCommand(it.command, it.description)
    }

    private fun showDataSettingResult(
        chatId: Long,
        messageId: Long,
        user: User? = null,
    ) {
        executeDeleteMessage(chatId, messageId)
        user?.isLikesToCook
        val text =
            """
                Успешно добавлены данные для пользователя <b>${user?.name}</b>:
                
            ${user.toString()}
            """.trimIndent()

        executeSendMessage(chatId, text)
    }

    private fun getUsersNames(userId: Long, likes: Boolean, interestCallbackEnum: InterestCallbackEnum) = if (likes) {
        userService.getUsersByInterest(likes, interestCallbackEnum).map {
            if (userId != it.id) {
                "- @" + it.name
            } else {
                ""
            }
        }.filter {
            it.isNotEmpty()
        }.ifEmpty {
            listOf("<i>Никто не нашелся</i>")
        }
    } else {
        listOf("<i>Вы этим не интересуетесь</i>")
    }

    private fun showQuestion(
        chatId: Long,
        messageId: Long,
        interestCallbackEnum: InterestCallbackEnum,
    ) {

        val text = interestCallbackEnum.question
        executeEditMessageText(text, chatId, messageId, interestCallbackEnum)
    }

    private fun executeDeleteMessage(chatId: Long, messageId: Long) {
        val messageToDelete = DeleteMessage()
        messageToDelete.chatId = chatId.toString()
        messageToDelete.messageId = messageId.toInt()
        bot.execute(messageToDelete)
    }

    private fun executeEditMessageText(
        text: String,
        chatId: Long,
        messageId: Long,
        interestCallback: InterestCallbackEnum
    ) {
        val message = EditMessageText()
        message.chatId = chatId.toString()
        message.text = text
        message.messageId = messageId.toInt()
        val markupLine = InlineKeyboardMarkup()
        val rowsInline: MutableList<MutableList<InlineKeyboardButton>> = mutableListOf()
        val rowInline: MutableList<InlineKeyboardButton> = mutableListOf()
        val yesButton = InlineKeyboardButton().apply {
            this.text = YES
            this.callbackData = interestCallback.yes
        }
        val noButton = InlineKeyboardButton().apply {
            this.text = NO
            this.callbackData = interestCallback.no
        }
        rowInline.add(yesButton)
        rowInline.add(noButton)
        rowsInline.add(rowInline)
        markupLine.keyboard = rowsInline
        message.replyMarkup = markupLine
        try {
            bot.execute(message)
        } catch (e: TelegramApiException) {
            logger.error("Ошибка при изменении сообщения" + e.message)
        }
    }

    private fun executeSendMessage(chatId: Long, responseText: String) {
        val responseMessage = SendMessage(chatId.toString(), responseText)
        responseMessage.parseMode = ParseMode.HTML
        try {
            bot.execute(responseMessage)
        } catch (e: TelegramApiException) {
            logger.error("Возникла ошибка при отправке сообщения пользователю: " + e.message)
        }
    }

    private fun unknownCommand(chat: Chat) {
        val chatId = chat.id
        val responseText = "Неизвестная команда"
        executeSendMessage(chatId, responseText)
    }
}
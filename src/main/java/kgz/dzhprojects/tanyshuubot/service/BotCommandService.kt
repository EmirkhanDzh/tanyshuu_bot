package kgz.dzhprojects.tanyshuubot.service

import kgz.dzhprojects.tanyshuubot.bot.TelegramBot
import kgz.dzhprojects.tanyshuubot.enum.BotCommandEnum
import kgz.dzhprojects.tanyshuubot.enum.InterestCallbackEnum
import kgz.dzhprojects.tanyshuubot.model.User
import mu.KotlinLogging
import org.springframework.stereotype.Service
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

                BotCommandEnum.ADD_MY_DATA.command -> addMyData(chat)

                BotCommandEnum.HELP.command -> help(chat)

                else -> unknownCommand(chat)
            }
        } else {
            if (update.hasCallbackQuery()) {
                System.err.println("call back handler section begin")
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
                            user.isLikesMoviesAndSeries,
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
                            user.isLikesMoviesAndSeries,
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
                            user.isLikesMoviesAndSeries,
                            user.isLikesNewsAndPolitics,
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
                            user.isLikesMoviesAndSeries,
                            user.isLikesNewsAndPolitics,
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
                            user.isLikesMoviesAndSeries,
                            user.isLikesNewsAndPolitics,
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
                            user.isLikesMoviesAndSeries,
                            user.isLikesNewsAndPolitics,
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
                            user.isLikesMoviesAndSeries,
                            user.isLikesNewsAndPolitics,
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
                            user.isLikesMoviesAndSeries,
                            user.isLikesNewsAndPolitics,
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
                            user.isLikesMoviesAndSeries,
                            user.isLikesNewsAndPolitics,
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
                            user.isLikesMoviesAndSeries,
                            user.isLikesNewsAndPolitics,
                            user.isLikesStudy,
                            user.isLikesWalking,
                            user.isLikesToEat,
                            false
                        )
                        user.isLikesToCook = false
                        showDataSettingResult(chatId, messageId, user = user)
                    }
                }

                System.err.println("call back handler section end")
            } else {
                logger.warn("невалидный ввод")
            }
        }
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
                Успешно добавлены данные для пользователя ${user?.name} :
                
            ${user.toString()}
            """.trimIndent()

        executeSendMessage(chatId, text)
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

    private fun addMyData(chat: Chat) {
        val chatId = chat.id
        val firstName = chat.firstName
        val userName = chat.userName
        userService.saveUser(chatId, userName)
        val firstResponseText = """
            Отлично, $firstName! Добавим твои данные для того, чтобы мы могли показать совместимых с тобою пользователей?
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

/*        logger.info { "$firstName начал добавление своих данных в сервис" }
        // sendMessage(chatId, firstResponseText)
        val user = User(chatId, userName)

        InterestCallbackEnum.values().forEach {
            System.err.println("loop for ${it.name}")
            System.err.println("chatId = ${userName}")
            val responseMessage = SendMessage(chatId.toString(), it.question)
            val markupLine = InlineKeyboardMarkup()
            val rowsInline: MutableList<MutableList<InlineKeyboardButton>> = mutableListOf()
            val rowInline: MutableList<InlineKeyboardButton> = mutableListOf()
            val yesButton = InlineKeyboardButton().apply {
                this.text = YES
                this.callbackData = it.yes
            }
            val noButton = InlineKeyboardButton().apply {
                this.text = NO
                this.callbackData = it.no
            }
            rowInline.add(yesButton)
            rowInline.add(noButton)
            rowsInline.add(rowInline)
            markupLine.keyboard = rowsInline
            responseMessage.replyMarkup = markupLine
            try {
                bot.execute(responseMessage)
            } catch (e: TelegramApiException) {
                logger.error("Возникла ошибка при отправке сообщения пользователю: " + e.message)
            }
            System.err.println("end of loop for ${it.name}")
        }

        //userService.saveUser(777777, "Pasha", true)

        logger.info { "$firstName закончил добавление своих данных в сервис" }*/
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

    private fun isValidMessage(update: Update) = update.let {
        it.hasMessage() && it.message.hasText()
    }

    private fun buildListOfCommands() = BotCommandEnum.values().map {
        BotCommand(it.command, it.description)
    }

    private fun start(chat: Chat) {
        val chatId = chat.id
        val firstName = chat.firstName
        val responseText = """
            Привет, $firstName!
            Этот бот поможет тебе найти людей, которые совместимы с тобой на основании твоих пользовательских данных.
            """.trimIndent()
        System.err.println("chatId :: $chatId")
        executeSendMessage(chatId, responseText)
    }

    private fun help(chat: Chat) {
        val chatId = chat.id
        val responseText = """
            Подробное описание каждой команды:
            
            /start - посмотреть описание бота
                
            /add_my_data - добавить ваши пользовательские данные, чтобы бот мог найти совместимых пользователей
                
            /get_my_data - посмотреть ваши пользовательские данные
                
            /update_my_data - поменять ваши пользовательские данные
                
            /delete_my_data - удалить ваши пользовательские данные
                
            /show_compatible_users - показать совместимых пользователей на основании ваших данных
                
            /help - узнать подробности про каждую команду
            """.trimIndent()
        executeSendMessage(chatId, responseText)
    }

    private fun executeSendMessage(chatId: Long, responseText: String) {
        val responseMessage = SendMessage(chatId.toString(), responseText)
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
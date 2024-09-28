package kgz.dzhprojects.TanyshuuBot.service;

import kgz.dzhprojects.TanyshuuBot.config.BotConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfiguration botConfig;

    public TelegramBot(BotConfiguration botConfig) {
        super(botConfig.getToken());
        this.botConfig = botConfig;
        initBotMenu();
    }

    private void initBotMenu() {
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "посмотреть описание бота"));
        listOfCommands.add(new BotCommand("/add_my_data", "добавить ваши пользовательские данные"));
        listOfCommands.add(new BotCommand("/get_my_data", "посмотреть ваши пользовательские данные"));
        listOfCommands.add(new BotCommand("/update_my_data", "поменять ваши пользовательские данные"));
        listOfCommands.add(new BotCommand("/delete_my_data", "удалить ваши пользовательские данные"));
        listOfCommands.add(new BotCommand("/show_compatible_users", "показать совместимых пользователей"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var messageText = update.getMessage().getText();
            var chat = update.getMessage().getChat();

            switch (messageText) {
                case "/start":
                    start(chat);
                    break;
                default:
                    unknownCommand(chat);
                    break;
            }
        }
    }

    private void unknownCommand(Chat chat) {
        var chatId = chat.getId();
        var userName = chat.getUserName();

        String responseText = "Неизвестная команда";

        sendMessage(chatId, responseText);
    }

    private void start(Chat chat) {
        var chatId = chat.getId();
        var userName = chat.getFirstName();

        String responseText = "Привет, " + userName + "!\n" +
                "Этот бот поможет тебе найти людей, которые совместимы с тобой на основании твоих пользовательских данных.";

        sendMessage(chatId, responseText);
    }

    private void sendMessage(Long chatId, String responseText) {
        SendMessage responseMessage = new SendMessage(chatId.toString(), responseText);

        try {
            execute(responseMessage);
        } catch (TelegramApiException e) {
            System.err.println("Возникла ошибка при отправке сообщения пользователю: " + e.getMessage());
        }
    }
}

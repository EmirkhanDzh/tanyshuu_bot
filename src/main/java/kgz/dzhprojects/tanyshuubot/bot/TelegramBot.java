package kgz.dzhprojects.tanyshuubot.bot;

import kgz.dzhprojects.tanyshuubot.config.BotConfiguration;
import kgz.dzhprojects.tanyshuubot.service.BotCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Декларация сущности бота
 * */
@Slf4j
@Service
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfiguration botConfig;

    final BotCommandService commandService;

    /**
     * Конструктор для создания сущности бота путем автоинжекта всех бинов из спринг контекста
     */
    public TelegramBot(BotConfiguration botConfig, BotCommandService commandService) {
        super(botConfig.getToken());
        this.botConfig = botConfig;
        this.commandService = commandService;
        init();
    }

    private void init() {
        commandService.setBot(this);
        commandService.initCommandMenu();
    }

    /**
     * Переопределенный метод для инициализации userName бота
     * */
    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }
    /**
     * Аналог контроллера в контексте ботов, который принимает запросы(команды) от пользователя
     * */
    @Override
    public void onUpdateReceived(Update update) {
        commandService.handleCommand(update);
    }
}

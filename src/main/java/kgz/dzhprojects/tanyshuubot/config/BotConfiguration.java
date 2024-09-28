package kgz.dzhprojects.tanyshuubot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
@Data
public class BotConfiguration {

    @Value("${bot.username}")
    String botName;

    @Value("${bot.token}")
    String token;
}

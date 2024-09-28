package kgz.dzhprojects.tanyshuubot.enum

enum class BotCommandsEnum(val command: String, val description: String) {
    START("/start", "посмотреть описание бота"),
    ADD_MY_DATE("/add_my_data", "добавить ваши пользовательские данные"),
    GET_MY_DATA("/get_my_data", "посмотреть ваши пользовательские данные"),
    UPDATE_MY_DATA("/update_my_data", "поменять ваши пользовательские данные"),
    DELETE_MY_DATA("/delete_my_data", "удалить ваши пользовательские данные"),
    SHOW_COMPATIBLE_USERS("/show_compatible_users", "показать совместимых пользователей"),
    HELP("/help", "узнать подробности про каждую команду"),
}
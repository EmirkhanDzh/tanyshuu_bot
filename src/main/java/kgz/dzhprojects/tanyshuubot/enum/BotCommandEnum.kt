package kgz.dzhprojects.tanyshuubot.enum

enum class BotCommandEnum(
    val command: String,
    val description: String,
) {
    START("/start", "посмотреть описание бота"),
    ADD_OR_EDIT_MY_DATA("/add_or_edit_my_data", "добавить или изменить ваши пользовательские данные"),
    SHOW_MY_DATA("/show_my_data", "посмотреть ваши пользовательские данные"),
    DELETE_MY_DATA("/delete_my_data", "удалить ваши пользовательские данные"),
    SHOW_COMPATIBLE_USERS("/show_compatible_users", "показать совместимых пользователей"),
    HELP("/help", "узнать подробности про каждую команду"),
}
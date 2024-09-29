package kgz.dzhprojects.tanyshuubot.enum

enum class InterestCallbackEnum(
    val question: String,
    val yes: String,
    val no: String,
) {
    SPORT("Нравится ли тебе спорт ?", "SPORT_YES", "SPORT_NO"),
    READING("Нравится ли тебе чтение ?", "READING_YES", "READING_NO"),
    ACTIVE_REST("Нравится ли тебе активный отдых ?", "ACTIVE_REST_YES", "ACTIVE_REST_NO"),
    ANIMALS("Нравятся ли тебе животные ?", "ANIMALS_YES", "ANIMALS_NO"),
    MOVIES_AND_SERIES("Нравится ли тебе проводить время за просмотром кино и сериалов ?", "MOVIES_AND_SERIES_YES", "MOVIES_AND_SERIES_NO"),
    NEWS_AND_POLITICS("Нравится ли тебе наблюдать за новостями и политикой ?", "NEWS_AND_POLITICS_YES", "NEWS_AND_POLITICS_NO"),
    STUDY("Нравится ли тебе учиться или саморазвиваться ?", "STUDY_YES", "STUDY_NO"),
    WALKING("Нравится ли тебе выходить на спокойную прогулку ?", "WALKING_YES", "WALKING_NO"),
    EAT("Являешься ли ты гурманом, любишь ты вкусно поесть ?", "EAT_YES", "EAT_NO"),
    COOK("Нравится ли тебе готовить ?", "COOK_YES", "COOK_NO"),
}
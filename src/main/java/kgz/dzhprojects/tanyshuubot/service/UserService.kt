package kgz.dzhprojects.tanyshuubot.service

import kgz.dzhprojects.tanyshuubot.model.User
import kgz.dzhprojects.tanyshuubot.repository.UserRepository
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun saveUser(
        chatId: Long,
        firstName: String,
        likesSport: Boolean = false,
        likesReading: Boolean = false,
        likesActiveRest: Boolean = false,
        likesAnimals: Boolean = false,
        likesMoviesAndSeries: Boolean = false,
        likesNewsAndPolitics: Boolean = false,
        likesStudy: Boolean = false,
        likesWalking: Boolean = false,
        likesToEat: Boolean = false,
        likesToCook: Boolean = false,
    ) {
        val user = User(
            chatId,
            firstName,
            likesSport,
            likesReading,
            likesActiveRest,
            likesAnimals,
            likesMoviesAndSeries,
            likesNewsAndPolitics,
            likesStudy,
            likesWalking,
            likesToEat,
            likesToCook,
        )
        logger.info(
            "saving new user $firstName with id $chatId"
        )
        userRepository.save(user)
    }

    fun getUser(chatId: Long): User? {
        logger.info(
            "try to retrieve user with id $chatId"
        )
        return userRepository.findByIdOrNull(chatId)
    }
}
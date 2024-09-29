package kgz.dzhprojects.tanyshuubot.service

import kgz.dzhprojects.tanyshuubot.enum.InterestCallbackEnum
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
        logger.info(
            "saving new user $firstName with id $chatId"
        )
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
        userRepository.save(user)
    }

    fun getUser(chatId: Long): User? {
        logger.info(
            "try to retrieve user with id $chatId"
        )
        return userRepository.findByIdOrNull(chatId)
    }

    fun removeUser(chatId: Long) {
        logger.info(
            "try to delete user with id $chatId"
        )
        return userRepository.deleteById(chatId)
    }

    fun getUsersByInterest(likes: Boolean, interestCallbackEnum: InterestCallbackEnum) = when (interestCallbackEnum) {
        InterestCallbackEnum.SPORT -> getUsersSportInterest(likes)
        InterestCallbackEnum.READING -> getUsersByReadingInterest(likes)
        InterestCallbackEnum.ACTIVE_REST -> getUsersByActiveRestInterest(likes)
        InterestCallbackEnum.ANIMALS -> getUsersByAnimalsInterest(likes)
        InterestCallbackEnum.MOVIES_AND_SERIES -> getUsersByMoviesAndSeriesInterest(likes)
        InterestCallbackEnum.NEWS_AND_POLITICS -> getUsersByNewsAndPoliticsInterest(likes)
        InterestCallbackEnum.STUDY -> getUsersByStudyInterest(likes)
        InterestCallbackEnum.WALKING -> getUsersByWalkingInterest(likes)
        InterestCallbackEnum.EAT -> getUsersByToEatInterest(likes)
        InterestCallbackEnum.COOK -> getUsersByCookInterest(likes)
    }

    fun getUsersSportInterest(likes: Boolean): List<User> {
        logger.info {
            "find all compatible users by sport interest"
        }
        return userRepository.findAllByLikesSport(likes)
    }

    fun getUsersByReadingInterest(likes: Boolean): List<User> {
        logger.info {
            "find all compatible users by sport interest"
        }
        return userRepository.findAllByLikesReading(likes)
    }

    fun getUsersByActiveRestInterest(likes: Boolean): List<User> {
        logger.info {
            "find all compatible users by active sport interest"
        }
        return userRepository.findAllByLikesActiveRest(likes)
    }

    fun getUsersByAnimalsInterest(likes: Boolean): List<User> {
        logger.info {
            "find all compatible users by animals interest"
        }
        return userRepository.findAllByLikesAnimals(likes)
    }

    fun getUsersByMoviesAndSeriesInterest(likes: Boolean): List<User> {
        logger.info {
            "find all compatible users by movies and series interest"
        }
        return userRepository.findAllByLikesMoviesSeries(likes)
    }

    fun getUsersByNewsAndPoliticsInterest(likes: Boolean): List<User> {
        logger.info {
            "find all compatible users by news and politics interest"
        }
        return userRepository.findAllByLikesNewsPolitics(likes)
    }

    fun getUsersByStudyInterest(likes: Boolean): List<User> {
        logger.info {
            "find all compatible users by study interest"
        }
        return userRepository.findAllByLikesStudy(likes)
    }

    fun getUsersByWalkingInterest(likes: Boolean): List<User> {
        logger.info {
            "find all compatible users by sport walking"
        }
        return userRepository.findAllByLikesWalking(likes)
    }

    fun getUsersByToEatInterest(likes: Boolean): List<User> {
        logger.info {
            "find all compatible users by eating interest"
        }
        return userRepository.findAllByLikesToEat(likes)
    }

    fun getUsersByCookInterest(likes: Boolean): List<User> {
        logger.info {
            "find all compatible users by cook interest"
        }
        return userRepository.findAllByLikesToCook(likes)
    }
}
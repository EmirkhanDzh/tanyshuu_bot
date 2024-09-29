package kgz.dzhprojects.tanyshuubot.repository;

import kgz.dzhprojects.tanyshuubot.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAllByLikesSport(boolean likes);

    List<User> findAllByLikesReading(boolean likes);

    List<User> findAllByLikesActiveRest(boolean likes);

    List<User> findAllByLikesAnimals(boolean likes);

    List<User> findAllByLikesMoviesSeries(boolean likes);

    List<User> findAllByLikesNewsPolitics(boolean likes);

    List<User> findAllByLikesStudy(boolean likes);

    List<User> findAllByLikesWalking(boolean likes);

    List<User> findAllByLikesToEat(boolean likes);

    List<User> findAllByLikesToCook(boolean likes);
}

package kgz.dzhprojects.tanyshuubot.repository;

import kgz.dzhprojects.tanyshuubot.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {}

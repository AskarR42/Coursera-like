package teta.mts.coursera.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teta.mts.coursera.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    List<User> findUsersByUsernameLike(String username);

    Optional<User> findUserByUsername(String username);
}

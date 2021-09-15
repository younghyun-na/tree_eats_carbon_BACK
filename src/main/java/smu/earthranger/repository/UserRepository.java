package smu.earthranger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.earthranger.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
        User findUserByEmail(String email);
}


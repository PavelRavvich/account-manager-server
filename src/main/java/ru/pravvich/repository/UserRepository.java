package ru.pravvich.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pravvich.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(@NonNull String username);
}

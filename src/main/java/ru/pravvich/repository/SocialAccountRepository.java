package ru.pravvich.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pravvich.domain.SocialAccount;

import java.util.Collection;
import java.util.List;

@Repository
public interface SocialAccountRepository extends CrudRepository<SocialAccount, Integer> {
}

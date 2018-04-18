package ru.pravvich.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pravvich.domain.SocialAccount;

@Repository
public interface SocialAccountRepository extends CrudRepository<SocialAccount, Integer> {
}

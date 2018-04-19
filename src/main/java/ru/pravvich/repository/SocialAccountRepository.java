package ru.pravvich.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pravvich.domain.SocialAccount;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Integer>, CrudRepository<SocialAccount, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE SocialAccount acc set acc.phoneId = ?1 WHERE acc.phoneId = ?2")
    void setPhone(@NonNull Integer arg, @NonNull Integer old);

    @Modifying
    @Transactional
    @Query("UPDATE SocialAccount acc set acc.vdsId = ?1 WHERE acc.vdsId = ?2")
    void setVds(@NonNull Integer arg, @NonNull Integer old);

}

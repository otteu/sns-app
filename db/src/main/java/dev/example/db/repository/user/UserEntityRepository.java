package dev.example.db.repository.user;

import dev.example.db.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);


    // @Query("UPDATE LikeEntity entity SET entity.deletedAt = NOW() WHERE entity.post = :post")
    // @Query("SELECT UserEntity FROM UserEntity entity WHERE entity.username =: username")
//    @Query("SELECT UserEntity FROM UserEntity entity WHERE entity.username = :username")
//    UserEntity findByUsernameCaches(@Param("username") String username);

}

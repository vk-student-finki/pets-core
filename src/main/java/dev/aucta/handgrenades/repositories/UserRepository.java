package dev.aucta.handgrenades.repositories;

import dev.aucta.handgrenades.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query(value =
            "SELECT * FROM APP_PRIVILEGES p WHERE p.ID in (" +
                    "SELECT DISTINCT(pm.PRIVILEGE_ID) FROM GROUPS_PRIVILEGES pm where pm.GROUP_ID in (" +
                    "        SELECT mem.GROUP_ID FROM USERS_GROUPS mem" +
                    "        WHERE mem.USER_ID = :userId))", nativeQuery = true)
    List<Object[]> getPrivilegesByUser(@Param("userId") Long userId);
}

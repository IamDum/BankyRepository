package storage;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import storage.entity.BankUser;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<BankUser, Long> {

    Optional<BankUser> findByName(String name);

    @Transactional
    void findByAdmin(String name);


}

package am.petstore.PetStore.user.dao;

import am.petstore.PetStore.user.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Repository
@Transactional
public interface UserDao extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);

    UserModel findByPhone(String phone);

    UserModel findByEmail(String email);

    @Modifying
    @Query("update UserModel user set user.fullname = ?2, user.password = ?3, user.email = ?4, user.birthday = ?5,user.gender = ?6, user.firebase_id = ?7,user.updatedAt = ?8,user.photo = ?9 where user.id = ?1")
    void updateUserInfo(Long id,
                        String fullname,
                        String password,
                        String email,
                        String birthday,
                        String gender,
                        String firebase_id,
                        Date updated_at,
                        String photo
    );

    @Modifying
    @Query("update UserModel user set user.phone = ?2, user.updatedAt = ?3 where user.id = ?1")
    void updateUserPhone(Long id,
                         String phone,
                         Date updated_at);

    boolean existsById(Long id);
}

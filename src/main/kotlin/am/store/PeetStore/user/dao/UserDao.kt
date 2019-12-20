package am.store.PeetStore.user.dao

import am.store.PeetStore.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Repository
@Transactional
@Service
interface UserDao : JpaRepository<UserEntity, Long> {

    fun findByPhone(phone: String?): UserEntity?

    fun findByEmail(email: String?): UserEntity?

    @Modifying
    @Query("update UserEntity user set user.fullname = ?2, user.mPassword = ?3, user.email = ?4, user.firebase_id = ?5,user.updatedAt = ?6where user.id = ?1")
    fun updateUserInfo(id: Long?,
                       fullname: String?,
                       password: String?,
                       email: String?,
                       firebase_id: String?,
                       updated_at: Date?
    )

    @Modifying
    @Query("update UserEntity user set user.phone = ?2, user.updatedAt = ?3 where user.id = ?1")
    fun updateUserPhone(id: Long?,
                        phone: String?,
                        updated_at: Date?)

    fun existsById(id: Long?): Boolean
}
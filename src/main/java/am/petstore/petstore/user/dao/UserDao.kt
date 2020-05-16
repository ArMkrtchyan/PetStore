package am.petstore.petstore.user.dao

import am.petstore.petstore.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Repository
@Transactional
interface UserDao : JpaRepository<UserEntity?, Long?> {
    fun findByUsername(username: String?): UserEntity?
    fun findByPhone(phone: String?): UserEntity?
    fun findByEmail(email: String?): UserEntity?

    @Modifying
    @Query("update UserEntity user set user.fullname = ?2, user.password = ?3, user.email = ?4, user.firebase_id = ?5,user.updatedAt = ?6,user.photo = ?7 where user.id = ?1")
    fun updateUserInfo(id: Long?,
                       fullname: String?,
                       password: String?,
                       email: String?,
                       firebase_id: String?,
                       updated_at: Date?,
                       photo: String?
    )

    @Modifying
    @Query("update UserEntity user set user.phone = ?2, user.updatedAt = ?3 where user.id = ?1")
    fun updateUserPhone(id: Long?,
                        phone: String?,
                        updated_at: Date?)

    override fun existsById(id: Long): Boolean
}
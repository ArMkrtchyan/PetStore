package am.petstore.petstore.user.dao

import am.petstore.petstore.user.entity.UserModel
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
interface UserDao : JpaRepository<UserModel?, Long?> {
    fun findByUsername(username: String?): UserModel?
    fun findByPhone(phone: String?): UserModel?
    fun findByEmail(email: String?): UserModel?
    @Modifying
    @Query("update UserModel user set user.fullname = ?2, user.password = ?3, user.email = ?4, user.birthday = ?5,user.gender = ?6, user.firebase_id = ?7,user.updatedAt = ?8,user.photo = ?9 where user.id = ?1")
    fun updateUserInfo(id: Long?,
                       fullname: String?,
                       password: String?,
                       email: String?,
                       birthday: String?,
                       gender: String?,
                       firebase_id: String?,
                       updated_at: Date?,
                       photo: String?
    )

    @Modifying
    @Query("update UserModel user set user.phone = ?2, user.updatedAt = ?3 where user.id = ?1")
    fun updateUserPhone(id: Long?,
                        phone: String?,
                        updated_at: Date?)

    override fun existsById(id: Long): Boolean
}
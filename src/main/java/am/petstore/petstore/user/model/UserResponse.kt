package am.petstore.petstore.user.model

import am.petstore.petstore.user.entity.UserEntity
import java.util.*

class UserResponse(user: UserEntity) {

    var id: Long? = null
    var email: String? = null
    var fullname: String? = null
    var birthday: String? = null
    var photo: String? = null
    var gender: String? = null
    var phone: String? = null
    var createdAt: Date? = null
    var updatedAt: Date? = null

    init {
        id = user.id
        email = user.email
        fullname = user.fullname
        birthday = user.birthday
        photo = user.photo
        gender = user.gender
        phone = user.phone
        createdAt = user.createdAt
        updatedAt = user.updatedAt
    }
}
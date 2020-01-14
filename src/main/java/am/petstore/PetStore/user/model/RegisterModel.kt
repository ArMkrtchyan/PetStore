package am.petstore.PetStore.user.model

class RegisterModel {
    var fullname: String? = null
    var email: String? = null
    var password: String? = null
    var phone: String? = null
    var birthday: String? = null

    override fun toString(): String {
        return "RegisterModel{" +
                "fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday='" + birthday + '\'' +
                '}'
    }
}
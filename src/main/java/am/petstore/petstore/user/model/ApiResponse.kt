package am.petstore.petstore.user.model

class ApiResponse {
    var status: Int
    var message: String
    var result: Any? = null

    constructor(status: Int, message: String, result: Any?) {
        this.status = status
        this.message = message
        this.result = result
    }

    constructor(status: Int, message: String) {
        this.status = status
        this.message = message
    }

    override fun toString(): String {
        return "ApiResponse [statusCode=$status, message=$message]"
    }
}
package am.petstore.petstore.order.model

class Order {
    var id: Long? = null
    var name: String? = null

    constructor()
    constructor(id: Long?, name: String?) {
        this.id = id
        this.name = name
    }

    override fun toString(): String {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}'
    }
}
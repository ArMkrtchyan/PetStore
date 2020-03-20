package am.petstore.petstore.pets.model

class Product {
    var id: Long? = null
    var name: String? = null

    constructor() {}
    constructor(id: Long?, name: String?) {
        this.id = id
        this.name = name
    }

    override fun toString(): String {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}'
    }
}
package am.petstore.petstore.pets.model

class Store {
    var id: Long? = null
    var name: String? = null

    constructor()
    constructor(id: Long?, name: String?) {
        this.id = id
        this.name = name
    }

    override fun toString(): String {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}'
    }
}
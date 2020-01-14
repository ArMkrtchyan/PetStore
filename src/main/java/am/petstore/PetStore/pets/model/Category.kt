package am.petstore.PetStore.pets.model

class Category {
    var id: Long? = null
    var name: String? = null

    constructor() {}
    constructor(id: Long?, name: String?) {
        this.id = id
        this.name = name
    }

    override fun toString(): String {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}'
    }
}
package am.petstore.PetStore.pets.model

import am.petstore.PetStore.pets.entity.PetEntity

class Pet {
    var id: Long? = null
    var title: String? = null
    var photo: String? = null

    constructor() {}
    constructor(id: Long?, name: String?, photo: String?) {
        this.id = id
        title = name
        this.photo = photo
    }

    constructor(petEntity: PetEntity) {
        id = petEntity.id
        title = petEntity.title
        photo = petEntity.photo
    }

    override fun toString(): String {
        return "Pet{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", photo='" + photo + '\'' +
                '}'
    }
}
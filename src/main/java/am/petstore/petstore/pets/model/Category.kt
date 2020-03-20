package am.petstore.petstore.pets.model

import am.petstore.petstore.pets.entity.CategoryEntity

class Category {
    var id: Long? = null
    var categoryId: Long? = null
    var petId: Long? = null
    var title: String? = null
    var photo: String? = null

    constructor() {}
    constructor(categoryEntity: CategoryEntity) {
        this.id = categoryEntity.id
        this.title = categoryEntity.title
        this.photo = categoryEntity.photo
        this.categoryId = categoryEntity.categoryId
        this.petId = categoryEntity.petId
    }

    constructor(id: Long?, name: String?, photo: String?,categoryId:Long,petId:Long) {
        this.id = id
        this.title = name
        this.photo = photo
        this.categoryId = categoryId
        this.petId = petId
    }

    override fun toString(): String {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", photo='" + photo + '\'' +
                ", categoryId='" + categoryId + '\'' +", petId='" + petId + '\'' +
                '}'
    }
}
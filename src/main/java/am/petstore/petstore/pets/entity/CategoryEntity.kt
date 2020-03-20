package am.petstore.petstore.pets.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "categories")
class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    var createdAt: Date? = null
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    var updatedAt: Date? = null
    @Column
    var title: String? = null
    @Column(name = "photo")
    var photo: String? = null
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    var deletedAt: Date? = null
    @Column
    var categoryId: Long? = null
    @Column
    var petId: Long? = null

    constructor() {}
    constructor(id: Long?, createdAt: Date?, updatedAt: Date?, title: String?, photo: String?, deletedAt: Date?, categoryId: Long?, petId: Long?) {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.title = title
        this.photo = photo
        this.deletedAt = deletedAt
        this.petId = petId
        this.categoryId = categoryId
    }

    constructor(createdAt: Date?, updatedAt: Date?, title: String?, photo: String?, categoryId: Long?, petId: Long?) {
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.title = title
        this.photo = photo
        this.petId = petId
        this.categoryId = categoryId
    }

    override fun toString(): String {
        return "CategoryEntity(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, title=$title, photo=$photo, deletedAt=$deletedAt, categoryId=$categoryId, petId=$petId)"
    }


}
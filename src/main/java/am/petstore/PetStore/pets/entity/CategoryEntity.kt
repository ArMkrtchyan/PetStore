package am.petstore.PetStore.pets.entity

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
    var name: String? = null
    @Column(name = "photo")
    var photo: String? = null
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    var deletedAt: Date? = null

    constructor() {}
    constructor(createdAt: Date?, updatedAt: Date?, name: String?, photo: String?) {
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.name = name
        this.photo = photo
    }

    override fun toString(): String {
        return "CategoryEntity{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                '}'
    }

}
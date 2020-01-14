package am.petstore.PetStore.pets.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "pets")
class PetEntity {
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

    constructor() {}
    constructor(createdAt: Date?, updatedAt: Date?, title: String?, photo: String?) {
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.title = title
        this.photo = photo
    }

    override fun toString(): String {
        return "PetEntity{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                ", title='" + title + '\'' +
                ", photo='" + photo + '\'' +
                '}'
    }

}
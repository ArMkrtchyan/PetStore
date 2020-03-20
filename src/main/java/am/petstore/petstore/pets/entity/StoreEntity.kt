package am.petstore.petstore.pets.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "store")
@EntityListeners(AuditingEntityListener::class)
class StoreEntity : Serializable {
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
        return "StoreEntity{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                '}'
    }

}
package am.petstore.petstore.pets.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tasties")
class TastyEntity {
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    var deletedAt: Date? = null

    @Column(name = "tasty")
    var tasty: String? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var weights: MutableSet<WeightEntity>? = null

    constructor()
    constructor(id: Long?, createdAt: Date?, updatedAt: Date?, deletedAt: Date?, tasty: String?, weights: MutableSet<WeightEntity>?) {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.deletedAt = deletedAt
        this.tasty = tasty
        this.weights = weights
    }

    override fun toString(): String {
        return "TastyEntity(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, tasty=$tasty, weights=$weights)"
    }


}
package am.petstore.petstore.pets.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "titles")
class CapacityEntity {
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

    @Column(name = "capacity")
    var capacity: String? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var options: MutableSet<OptionsEntity>? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var colors: MutableSet<ColorEntity>? = null

    constructor()
    constructor(id: Long?, createdAt: Date?, updatedAt: Date?, deletedAt: Date?, capacity: String?, options: MutableSet<OptionsEntity>?, colors: MutableSet<ColorEntity>?) {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.deletedAt = deletedAt
        this.capacity = capacity
        this.options = options
        this.colors = colors
    }

    override fun toString(): String {
        return "CapacityEntity(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, capacity=$capacity, options=$options, colors=$colors)"
    }


}
package am.petstore.petstore.pets.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "volumes")
class VolumeEntity {
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

    @Column(name = "volume")
    var volume: Double? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var options: MutableSet<OptionsEntity>? = null

    constructor()
    constructor(id: Long?, createdAt: Date?, updatedAt: Date?, deletedAt: Date?, volume: Double?, options: MutableSet<OptionsEntity>?) {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.deletedAt = deletedAt
        this.volume = volume
        this.options = options
    }

    override fun toString(): String {
        return "VolumeEntity(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, volume=$volume, options=$options)"
    }


}
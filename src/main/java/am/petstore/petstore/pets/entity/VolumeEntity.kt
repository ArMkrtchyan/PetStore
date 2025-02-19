package am.petstore.petstore.pets.entity

import com.fasterxml.jackson.annotation.JsonIgnore
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

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "options_id", referencedColumnName = "id")
    @OrderBy("id ASC")
    var options: OptionsEntity? = null

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    var product: ProductEntity? = null

    constructor()
    constructor(id: Long?, createdAt: Date?, updatedAt: Date?, deletedAt: Date?, volume: Double?, options: OptionsEntity?, product: ProductEntity?) {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.deletedAt = deletedAt
        this.volume = volume
        this.options = options
        this.product = product
    }

    override fun toString(): String {
        return "VolumeEntity(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, volume=$volume, options=$options, product=$product)"
    }


}
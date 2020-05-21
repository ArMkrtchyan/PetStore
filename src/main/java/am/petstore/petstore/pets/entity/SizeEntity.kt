package am.petstore.petstore.pets.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "sizes")
class SizeEntity {
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

    @Column(name = "length")
    var length: Double? = null

    @Column(name = "width")
    var width: Double? = null

    @Column(name = "height")
    var height: Double? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "size_id")
    @OrderBy("id ASC")
    var capacities: MutableSet<CapacityEntity>? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "size_id")
    @OrderBy("id ASC")
    var colors: MutableSet<ColorEntity>? = null

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    var product: ProductEntity? = null

    constructor()
    constructor(id: Long?, createdAt: Date?, updatedAt: Date?, deletedAt: Date?, length: Double?, width: Double?, height: Double?, capacities: MutableSet<CapacityEntity>?, colors: MutableSet<ColorEntity>?, product: ProductEntity?) {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.deletedAt = deletedAt
        this.length = length
        this.width = width
        this.height = height
        this.capacities = capacities
        this.colors = colors
        this.product = product
    }

    override fun toString(): String {
        return "SizeEntity(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, length=$length, width=$width, height=$height, capacities=$capacities, colors=$colors, product=$product)"
    }


}
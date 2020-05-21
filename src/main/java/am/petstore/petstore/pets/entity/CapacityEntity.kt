package am.petstore.petstore.pets.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "capacities")
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

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "options_id", referencedColumnName = "id")
    @OrderBy("id ASC")
    var options: OptionsEntity? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "capacity_id")
    @OrderBy("id ASC")
    var colors: MutableSet<ColorEntity>? = null

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    var product: ProductEntity? = null

    @ManyToOne
    @JoinColumn(name = "size_id")
    @JsonIgnore
    var size: SizeEntity? = null

    constructor()
    constructor(id: Long?, createdAt: Date?, updatedAt: Date?, deletedAt: Date?, capacity: String?, options: OptionsEntity?, colors: MutableSet<ColorEntity>?, product: ProductEntity?, size: SizeEntity?) {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.deletedAt = deletedAt
        this.capacity = capacity
        this.options = options
        this.colors = colors
        this.product = product
        this.size = size
    }

    override fun toString(): String {
        return "CapacityEntity(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, capacity=$capacity, options=$options, colors=$colors, product=$product, size=$size)"
    }


}
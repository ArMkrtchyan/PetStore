package am.petstore.petstore.pets.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "options")
class OptionsEntity {
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

    @Column(name = "title")
    var title: String? = null

    @Column(name = "description")
    var description: String? = null

    @Column(name = "price")
    var price: Int? = null

    @Column(name = "discount")
    var discount: Int? = null

    @Column(name = "old_price")
    var oldPrice: Int? = null

    @Column(name = "in_stock")
    var inStock: Boolean? = null

    @ElementCollection(targetClass = String::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "photos", joinColumns = [JoinColumn(name = "options_id")])
    var photos: MutableSet<String?>? = null

    @OneToOne(mappedBy = "options")
    @JsonIgnore
    var color: ColorEntity? = null

    @OneToOne(mappedBy = "options")
    @JsonIgnore
    var capacities: CapacityEntity? = null

    @OneToOne(mappedBy = "options")
    @JsonIgnore
    var volome: VolumeEntity? = null

    @OneToOne(mappedBy = "options")
    @JsonIgnore
    var weight: WeightEntity? = null

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    var product: ProductEntity? = null


    constructor() {}
    constructor(id: Long?, createdAt: Date?, updatedAt: Date?, deletedAt: Date?, title: String?, description: String?, price: Int?, discount: Int?, oldPrice: Int?, inStock: Boolean?, photos: MutableSet<String?>?, color: ColorEntity?, capacities: CapacityEntity?, volome: VolumeEntity?, weight: WeightEntity?) {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.deletedAt = deletedAt
        this.title = title
        this.description = description
        this.price = price
        this.discount = discount
        this.oldPrice = oldPrice
        this.photos = photos
        this.color = color
        this.capacities = capacities
        this.volome = volome
        this.weight = weight
        this.inStock = inStock
    }

    override fun toString(): String {
        return "OptionsEntity(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, title=$title, description=$description, price=$price, discount=$discount, oldPrice=$oldPrice,inStock=$inStock, photos=$photos, color=$color, capacities=$capacities, volome=$volome, weight=$weight)"
    }


}
package am.petstore.PetStore.pets.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "products")
class ProductEntity {
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

    @Column
    var title: String? = null

    @Column(name = "photo")
    var photo: String? = null

    @Column(name = "producer_name")
    var producerName: String? = null

    @Column(name = "producer_photo")
    var producerPhoto: String? = null

    @Column(name = "color")
    var color: String? = null

    @Column(name = "description")
    var description: String? = null

    @Column(name = "weight")
    var weight: Double? = null

    @Column(name = "length")
    var length: Double? = null

    @Column(name = "category_id")
    var categoryId: Int? = null

    @Column(name = "pet_id")
    var petId: Int? = null

    @Column(name = "price")
    var price: Int? = null

    @Column(name = "discount")
    var discount: Int? = null

    @Column(name = "old_price")
    var oldPrice: Int? = null

    constructor() {}
    constructor(createdAt: Date?, updatedAt: Date?, deletedAt: Date?, title: String?, photo: String?, producerName: String?, producerPhoto: String?, color: String?, description: String?, weight: Double?, length: Double?, categoryId: Int?, petId: Int?, price: Int?, discount: Int?, oldPrice: Int?) {
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.deletedAt = deletedAt
        this.title = title
        this.photo = photo
        this.producerName = producerName
        this.producerPhoto = producerPhoto
        this.color = color
        this.description = description
        this.weight = weight
        this.length = length
        this.categoryId = categoryId
        this.petId = petId
        this.price = price
        this.discount = discount
        this.oldPrice = oldPrice
    }

    override fun toString(): String {
        return "ProductEntity(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, title=$title, photo=$photo, producerName=$producerName, producerPhoto=$producerPhoto, color=$color, description=$description, weight=$weight, length=$length, categoryId=$categoryId, petId=$petId, price=$price, discount=$discount, oldPrice=$oldPrice)"
    }


}
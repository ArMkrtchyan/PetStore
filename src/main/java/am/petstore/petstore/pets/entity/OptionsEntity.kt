package am.petstore.petstore.pets.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "other_options")
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

    @ElementCollection(targetClass = String::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "photos", joinColumns = [JoinColumn(name = "options_id")])
    var installDates: MutableSet<String?>? = null

    constructor() {}
    constructor(id: Long?, createdAt: Date?, updatedAt: Date?, deletedAt: Date?, title: String?, description: String?, price: Int?, discount: Int?, oldPrice: Int?, installDates: MutableSet<String?>?) {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.deletedAt = deletedAt
        this.title = title
        this.description = description
        this.price = price
        this.discount = discount
        this.oldPrice = oldPrice
        this.installDates = installDates
    }

    override fun toString(): String {
        return "OptionsEntity(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, title=$title, description=$description, price=$price, discount=$discount, oldPrice=$oldPrice, installDates=$installDates)"
    }


}
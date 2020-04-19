package am.petstore.petstore.pets.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "weights")
class WeightEntity {
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

    @Column(name = "weight")
    var weight: Double? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var photos: MutableSet<PhotoEntity>? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var prices: MutableSet<PriceEntity>? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var descriptions: MutableSet<DescriptionEntity>? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var titles: MutableSet<TitleEntity>? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var colors: MutableSet<ColorEntity>? = null

    constructor()
    constructor(id: Long?, createdAt: Date?, updatedAt: Date?, deletedAt: Date?, weight: Double?, photos: MutableSet<PhotoEntity>?, prices: MutableSet<PriceEntity>?, descriptions: MutableSet<DescriptionEntity>?, titles: MutableSet<TitleEntity>?, colors: MutableSet<ColorEntity>?) {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.deletedAt = deletedAt
        this.weight = weight
        this.photos = photos
        this.prices = prices
        this.descriptions = descriptions
        this.titles = titles
        this.colors = colors
    }

    override fun toString(): String {
        return "WeightEntity(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, weight=$weight, photos=$photos, prices=$prices, descriptions=$descriptions, titles=$titles, colors=$colors)"
    }

}
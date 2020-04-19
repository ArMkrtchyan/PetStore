package am.petstore.petstore.pets.entity

import am.petstore.petstore.user.entity.UserEntity
import com.fasterxml.jackson.annotation.JsonIgnore
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

    @Column(name = "producer_name")
    var producerName: String? = null

    @Column(name = "producer_photo")
    var producerPhoto: String? = null

    @Column(name = "category_id")
    var categoryId: Int? = null

    @Column(name = "pet_id")
    var petId: Int? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var colors: MutableSet<ColorEntity>? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var titles: MutableSet<TitleEntity>? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var descriptions: MutableSet<DescriptionEntity>? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var sizes: MutableSet<SizeEntity>? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var tasties: MutableSet<TastyEntity>? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var volumes: MutableSet<VolumeEntity>? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var weights: MutableSet<WeightEntity>? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var photos: MutableSet<PhotoEntity>? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var prices: MutableSet<PriceEntity>? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JsonIgnore
    var users: MutableSet<UserEntity>? = null

    constructor()
    constructor(id: Long?, createdAt: Date?, updatedAt: Date?, deletedAt: Date?, producerName: String?, producerPhoto: String?, categoryId: Int?, petId: Int?, colors: MutableSet<ColorEntity>?, titles: MutableSet<TitleEntity>?, descriptions: MutableSet<DescriptionEntity>?, sizes: MutableSet<SizeEntity>?, tasties: MutableSet<TastyEntity>?, volumes: MutableSet<VolumeEntity>?, weights: MutableSet<WeightEntity>?, photos: MutableSet<PhotoEntity>?, prices: MutableSet<PriceEntity>?, users: MutableSet<UserEntity>?) {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.deletedAt = deletedAt
        this.producerName = producerName
        this.producerPhoto = producerPhoto
        this.categoryId = categoryId
        this.petId = petId
        this.colors = colors
        this.titles = titles
        this.descriptions = descriptions
        this.sizes = sizes
        this.tasties = tasties
        this.volumes = volumes
        this.weights = weights
        this.photos = photos
        this.prices = prices
        this.users = users
    }

    override fun toString(): String {
        return "ProductEntity(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, producerName=$producerName, producerPhoto=$producerPhoto, categoryId=$categoryId, petId=$petId, colors=$colors, titles=$titles, descriptions=$descriptions, sizes=$sizes, tasties=$tasties, volumes=$volumes, weights=$weights, photos=$photos, prices=$prices, users=$users)"
    }

}
package am.petstore.petstore.pets.entity

import am.petstore.petstore.user.entity.UserEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
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

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @OrderBy("id ASC")
    var sizes: MutableSet<SizeEntity>? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @OrderBy("id ASC")
    var tasties: MutableSet<TastyEntity>? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @OrderBy("id ASC")
    var volumes: MutableSet<VolumeEntity>? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @OrderBy("id ASC")
    var capacities: MutableSet<CapacityEntity>? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    var options: MutableSet<OptionsEntity>? = null

    @ManyToMany(mappedBy = "favorites", fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = ["favorites", "devices", "password", "email", "fullname", "photo", "firebase_id", "phone", "createdAt", "updatedAt", "orders"])
    var users: MutableSet<UserEntity>? = null

    constructor()
    constructor(id: Long?, createdAt: Date?, updatedAt: Date?, deletedAt: Date?, producerName: String?, producerPhoto: String?, categoryId: Int?, petId: Int?, sizes: MutableSet<SizeEntity>?, tasties: MutableSet<TastyEntity>?, volumes: MutableSet<VolumeEntity>?, capacities: MutableSet<CapacityEntity>?, options: MutableSet<OptionsEntity>?, users: MutableSet<UserEntity>?) {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.deletedAt = deletedAt
        this.producerName = producerName
        this.producerPhoto = producerPhoto
        this.categoryId = categoryId
        this.petId = petId
        this.sizes = sizes
        this.tasties = tasties
        this.volumes = volumes
        this.capacities = capacities
        this.options = options
        this.users = users
    }

    override fun toString(): String {
        return "ProductEntity(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, producerName=$producerName, producerPhoto=$producerPhoto, categoryId=$categoryId, petId=$petId, sizes=$sizes, tasties=$tasties, volumes=$volumes, capacities=$capacities, options=$options, users=$users)"
    }


}
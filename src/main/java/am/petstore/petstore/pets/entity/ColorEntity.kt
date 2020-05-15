package am.petstore.petstore.pets.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "colors")
class ColorEntity {
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

    @Column(name = "color")
    var color: String? = null

    @Column(name = "hex")
    var hex: String? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var options: MutableSet<OptionsEntity>? = null

    constructor()
    constructor(id: Long?, createdAt: Date?, updatedAt: Date?, deletedAt: Date?, color: String?, hex: String?, options: MutableSet<OptionsEntity>?) {
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.deletedAt = deletedAt
        this.color = color
        this.hex = hex
        this.options = options
    }

    override fun toString(): String {
        return "ColorEntity(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt, color=$color, hex=$hex, options=$options)"
    }


}
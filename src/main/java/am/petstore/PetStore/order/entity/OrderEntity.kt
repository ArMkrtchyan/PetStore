package am.petstore.PetStore.order.entity

import am.petstore.PetStore.user.entity.UserModel
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "orders")
class OrderEntity {
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
    @Column
    var name: String? = null
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    var user: UserModel? = null

    constructor() {}
    constructor(createdAt: Date?, updatedAt: Date?, name: String?) {
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.name = name
    }

    override fun toString(): String {
        return "OrderEntity{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", name='" + name + '\'' +
                '}'
    }
}
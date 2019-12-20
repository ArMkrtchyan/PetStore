package am.store.PeetStore.user.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "device")
class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    var createdAt: Date? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    var updatedAt: Date? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    var user: UserEntity? = null

    @ElementCollection(targetClass = String::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "device_installs", joinColumns = [JoinColumn(name = "device_id")])
    var installDates: Set<String>? = null

    @Column
    var firebaseToken: String? = null

    @Column
    var uid: @NotNull String? = null

    @Column
    var language: String? = null

    @Column
    var model: String? = null

    @Column
    var platform: String? = null

    @Column
    var sdkVersion: String? = null

    @Column
    var appVersion: String? = null

    constructor()

    constructor(createdAt: Date?, updatedAt: Date?, user: UserEntity?, install_dates: Set<String>?, firebase_token: String?, device_id: @NotNull String?, language: String?, model: String?, platform: String?, sdk_version: String?, app_version: String?) {
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.user = user
        this.installDates = install_dates
        this.firebaseToken = firebase_token
        this.uid = device_id
        this.language = language
        this.model = model
        this.platform = platform
        this.sdkVersion = sdk_version
        this.appVersion = app_version
    }

}
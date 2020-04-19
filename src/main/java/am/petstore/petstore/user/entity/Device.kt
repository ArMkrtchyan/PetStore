package am.petstore.petstore.user.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "device")
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(value = ["createdAt", "updatedAt"], allowGetters = true)
class Device : Serializable {
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    var user: UserEntity? = null

    @ElementCollection(targetClass = String::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "device_installs", joinColumns = [JoinColumn(name = "device_id")])
    var installDates: MutableSet<String?>? = null

    @Column
    var firstInstallDate: String? = null

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
    constructor(createdAt: Date?, updatedAt: Date?, user: UserEntity?, install_dates: MutableSet<String?>?, first_install_date: String?, firebase_token: String?, device_id: @NotNull String?, language: String?, model: String?, platform: String?, sdk_version: String?, app_version: String?) {
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.user = user
        this.installDates = install_dates
        this.firstInstallDate = first_install_date
        this.firebaseToken = firebase_token
        uid = device_id
        this.language = language
        this.model = model
        this.platform = platform
        this.sdkVersion = sdk_version
        this.appVersion = app_version
    }

    override fun toString(): String {
        return "Device{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", install_dates=" + installDates +
                ", first_install_date='" + firstInstallDate + '\'' +
                ", firebase_token='" + firebaseToken + '\'' +
                ", uid='" + uid + '\'' +
                ", language='" + language + '\'' +
                ", model='" + model + '\'' +
                ", platform='" + platform + '\'' +
                ", sdk_version='" + sdkVersion + '\'' +
                ", app_version='" + appVersion + '\'' +
                '}'
    }
}
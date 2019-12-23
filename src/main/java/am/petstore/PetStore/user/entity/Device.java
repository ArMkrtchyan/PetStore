package am.petstore.PetStore.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "device")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
public class Device implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel user;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "device_installs", joinColumns = @JoinColumn(name = "device_id"))
    private Set<String> install_dates;

    @Column
    private String first_install_date;

    @Column
    private String firebase_token;

    @Column
    @NotNull
    private String uid;

    @Column
    private String language;

    @Column
    private String model;

    @Column
    private String platform;

    @Column
    private String sdk_version;

    @Column
    private String app_version;

    public Device() {
    }

    public Device(Date createdAt, Date updatedAt, UserModel user, Set<String> install_dates, String first_install_date, String firebase_token, @NotNull String device_id, String language, String model, String platform, String sdk_version, String app_version) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.install_dates = install_dates;
        this.first_install_date = first_install_date;
        this.firebase_token = firebase_token;
        this.uid = device_id;
        this.language = language;
        this.model = model;
        this.platform = platform;
        this.sdk_version = sdk_version;
        this.app_version = app_version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFirst_install_date() {
        return first_install_date;
    }

    public void setFirst_install_date(String first_install_date) {
        this.first_install_date = first_install_date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }


    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }


    public Set<String> getInstall_dates() {
        return install_dates;
    }

    public void setInstall_dates(Set<String> install_dates) {
        this.install_dates = install_dates;
    }

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", install_dates=" + install_dates +
                ", first_install_date='" + first_install_date + '\'' +
                ", firebase_token='" + firebase_token + '\'' +
                ", uid='" + uid + '\'' +
                ", language='" + language + '\'' +
                ", model='" + model + '\'' +
                ", platform='" + platform + '\'' +
                ", sdk_version='" + sdk_version + '\'' +
                ", app_version='" + app_version + '\'' +
                '}';
    }
}

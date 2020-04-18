package am.petstore.petstore.user.dao

import am.petstore.petstore.user.entity.Device
import am.petstore.petstore.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
@Service
@Repository
@Transactional
interface DeviceDao : JpaRepository<Device?, Long?> {

    fun findByUid(device_uid: String?): Device?
    fun existsByUid(device_uid: String?): Boolean
    fun findAllByUser(user: UserEntity?): List<Device?>?

    @Modifying
    @Query("update Device device set device.appVersion = ?2, device.firstInstallDate = ?3, device.language = ?4, device.model = ?5,device.platform = ?6, device.sdkVersion = ?7, device.firebaseToken = ?8,device.updatedAt = ?9 where device.uid = ?1")
    fun updateDeviceByUid(uid: String?,
                          app_version: String?,
                          first_install_date: String?,
                          language: String?,
                          model: String?,
                          platform: String?,
                          sdk_version: String?,
                          firebase_token: String?,
                          updatedAt: Date?
    )

    @Modifying
    @Query("update Device device set device.firebaseToken = ?2 where device.uid = ?1")
    fun updateFirebaseToken(uid: String?,
                            firebase_token: String?)
}
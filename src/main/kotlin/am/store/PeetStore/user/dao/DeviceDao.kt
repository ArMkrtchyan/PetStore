package am.store.PeetStore.user.dao

import am.store.PeetStore.user.entity.DeviceEntity
import am.store.PeetStore.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Repository
@Transactional
@Service
interface DeviceDao : JpaRepository<DeviceEntity, Long> {

    fun findByUid(device_uid: String?): DeviceEntity?

    fun existsByUid(device_uid: String?): Boolean

    fun findAllByUser(user: UserEntity?): List<DeviceEntity?>?

    @Modifying
    @Query("update DeviceEntity device set device.appVersion = ?2,  device.language = ?3, device.model = ?4,device.platform = ?5, device.sdkVersion = ?6, device.firebaseToken = ?7,device.updatedAt = ?8 where device.uid = ?1")
    fun updateDeviceByUid(uid: String?,
                          app_version: String?,
                          language: String?,
                          model: String?,
                          platform: String?,
                          sdk_version: String?,
                          firebase_token: String?,
                          updateed_at: Date?
    )

    @Modifying
    @Query("update DeviceEntity device set device.firebaseToken = ?2 where device.uid = ?1")
    fun updateFirebaseToken(uid: String?,
                            firebase_token: String?)

}
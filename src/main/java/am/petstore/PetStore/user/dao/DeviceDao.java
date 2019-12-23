package am.petstore.PetStore.user.dao;

import am.petstore.PetStore.user.entity.Device;
import am.petstore.PetStore.user.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Service
@Repository
@Transactional
public interface DeviceDao extends JpaRepository<Device, Long> {

    Device findByUid(String device_uid);

    boolean existsByUid(String device_uid);

    List<Device> findAllByUser(UserModel user);

    @Modifying
    @Query("update Device device set device.app_version = ?2, device.first_install_date = ?3, device.language = ?4, device.model = ?5,device.platform = ?6, device.sdk_version = ?7, device.firebase_token = ?8,device.updatedAt = ?9 where device.uid = ?1")
    void updateDeviceByUid(String uid,
                           String app_version,
                           String first_install_date,
                           String language,
                           String model,
                           String platform,
                           String sdk_version,
                           String firebase_token,
                           Date updateed_at
    );

    @Modifying
    @Query("update Device device set device.firebase_token = ?2 where device.uid = ?1")
    void updateFirebaseToken(String uid,
                             String firebase_token);

}

package am.petstore.PetStore.pets.dao;

import am.petstore.PetStore.pets.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Repository
@Transactional
public interface PetDao extends JpaRepository<PetEntity, Long> {

    PetEntity findByTitle(String title);

    boolean existsByTitle(String title);

    @Modifying(clearAutomatically=true)
    @Query("update PetEntity pet set pet.deletedAt = ?2 where pet.id = ?1")
    void delete(Long id, Date deletedAt);


    @Modifying(clearAutomatically=true)
    @Query("update PetEntity pet set pet.updatedAt = ?2,pet.photo = ?3,pet.title = ?4,pet.deletedAt = ?5 where pet.id = ?1")
    void update(Long id, Date updatedAt,
                String photo,
                String title,
                Date deletedAt);
}

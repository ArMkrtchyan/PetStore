package am.petstore.PetStore.pets.dao;

import am.petstore.PetStore.pets.entity.PetEntity;
import am.petstore.PetStore.pets.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreDao extends JpaRepository<StoreEntity, Long> {
}

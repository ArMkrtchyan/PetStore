package am.petstore.PetStore.pets.dao;

import am.petstore.PetStore.pets.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetDao extends JpaRepository<PetEntity, Long> {
}

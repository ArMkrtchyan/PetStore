package am.petstore.PetStore.pets.dao;

import am.petstore.PetStore.pets.entity.ProductEntity;
import am.petstore.PetStore.pets.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<ProductEntity,Long > {
}

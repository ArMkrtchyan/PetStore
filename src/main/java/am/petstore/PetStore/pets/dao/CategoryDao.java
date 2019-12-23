package am.petstore.PetStore.pets.dao;

import am.petstore.PetStore.pets.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<CategoryEntity, Long> {
}

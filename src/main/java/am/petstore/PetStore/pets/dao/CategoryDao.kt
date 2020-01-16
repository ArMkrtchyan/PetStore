package am.petstore.PetStore.pets.dao

import am.petstore.PetStore.pets.entity.CategoryEntity
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Repository
@Transactional
interface CategoryDao : JpaRepository<CategoryEntity?, Long?> {
    fun findByTitle(title: String?): CategoryEntity?
    fun findByPetIdAndAndCategoryId(petId: Long, categoryId: Long, by: Sort): MutableList<CategoryEntity?>
    fun findByPetId(petId: Long, by: Sort): MutableList<CategoryEntity?>
    fun existsByTitle(title: String?): Boolean
    @Modifying(clearAutomatically = true)
    @Query("update CategoryEntity category set category.deletedAt = ?2 where category.id = ?1")
    fun delete(id: Long?, deletedAt: Date?)

    @Modifying(clearAutomatically = true)
    @Query("update CategoryEntity category set category.updatedAt = ?2,category.photo = ?3,category.title = ?4,category.deletedAt = ?5,category.petId = ?6, category.categoryId = ?7 where category.id = ?1")
    fun update(id: Long?, updatedAt: Date?,
               photo: String?,
               title: String?,
               deletedAt: Date?,
               petId: Long,
               categoryId: Long?)
}
package am.petstore.petstore.pets.dao

import am.petstore.petstore.pets.entity.ProductEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Repository
@Transactional
interface ProductDao : JpaRepository<ProductEntity?, Long?> {

    fun findAllByCategoryId(categoryId: Int?, by: Sort): List<ProductEntity>
    fun findAllByCategoryId(categoryId: Int?): List<ProductEntity>
    fun findAllByPetId(petId: Int?): List<ProductEntity>
    fun findAllByPetId(petId: Int?, by: Sort): List<ProductEntity>
    fun findByCategoryId(categoryId: Int?, pageRequest: Pageable): Slice<ProductEntity>

    fun findByPetId(petId: Int?, pageRequest: Pageable): Slice<ProductEntity>

}
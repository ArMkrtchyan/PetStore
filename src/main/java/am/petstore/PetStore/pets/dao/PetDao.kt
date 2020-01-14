package am.petstore.PetStore.pets.dao

import am.petstore.PetStore.pets.entity.PetEntity
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
interface PetDao : JpaRepository<PetEntity?, Long?> {
    fun findByTitle(title: String?): PetEntity?
    fun existsByTitle(title: String?): Boolean
    @Modifying(clearAutomatically = true)
    @Query("update PetEntity pet set pet.deletedAt = ?2 where pet.id = ?1")
    fun delete(id: Long?, deletedAt: Date?)

    @Modifying(clearAutomatically = true)
    @Query("update PetEntity pet set pet.updatedAt = ?2,pet.photo = ?3,pet.title = ?4,pet.deletedAt = ?5 where pet.id = ?1")
    fun update(id: Long?, updatedAt: Date?,
               photo: String?,
               title: String?,
               deletedAt: Date?)
}
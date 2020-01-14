package am.petstore.PetStore.pets.dao

import am.petstore.PetStore.pets.entity.StoreEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Repository
@Transactional
interface StoreDao : JpaRepository<StoreEntity?, Long?>
package am.petstore.petstore.pets.dao

import am.petstore.petstore.pets.entity.DescriptionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Repository
@Transactional
interface DescriptionDao : JpaRepository<DescriptionEntity?, Long?>
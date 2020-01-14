package am.petstore.PetStore.order.dao

import am.petstore.PetStore.order.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderDao : JpaRepository<OrderEntity?, Long?>
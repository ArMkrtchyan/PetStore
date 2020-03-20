package am.petstore.petstore.order.dao

import am.petstore.petstore.order.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderDao : JpaRepository<OrderEntity?, Long?>
package am.petstore.PetStore.order.dao;

import am.petstore.PetStore.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<OrderEntity, Long> {
}

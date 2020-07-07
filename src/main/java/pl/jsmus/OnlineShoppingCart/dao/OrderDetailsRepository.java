package pl.jsmus.OnlineShoppingCart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.transaction.annotation.Transactional;
import pl.jsmus.OnlineShoppingCart.entity.OrderDetail;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, String> {

    @Transactional
    void deleteByOrder_id(String theId);

}

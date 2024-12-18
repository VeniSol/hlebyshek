package com.base.new_base.Repositories;

import com.base.new_base.Entity.Order;
import com.base.new_base.Entity.Status;
import com.base.new_base.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Integer> {
    List<Order> findAll();
    Order findById(int id);
    List<Order> findByStatus(Status status);
    List<Order> findByUserAndStatus(User user, Status status);
    List<Order> findByDelivererAndStatus(String deliverer, Status status);
}
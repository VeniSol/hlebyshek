package com.base.new_base.Entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "order_date_time")
    private LocalDate orderDateTime;
    @Column(name = "date_time_of_receipt")
    private LocalDate dateTimeOfReceipt;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "address")
    private String address;
}

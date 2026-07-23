package br.com.fiap.vehiclesales.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity @Table(name="sales")
public class Sale {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @OneToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="vehicle_id",nullable=false,unique=true) private Vehicle vehicle;
 @Column(name="buyer_id",nullable=false,length=120) private String buyerId;
 @Column(name="sale_price",nullable=false,precision=15,scale=2) private BigDecimal salePrice;
 @Column(name="purchased_at",nullable=false) private LocalDateTime purchasedAt;
 protected Sale(){}
 public Sale(Vehicle vehicle,String buyerId){this.vehicle=vehicle;this.buyerId=buyerId;this.salePrice=vehicle.getPrice();this.purchasedAt=LocalDateTime.now();}
 public UUID getId(){return id;} public Vehicle getVehicle(){return vehicle;} public String getBuyerId(){return buyerId;} public BigDecimal getSalePrice(){return salePrice;} public LocalDateTime getPurchasedAt(){return purchasedAt;}
}

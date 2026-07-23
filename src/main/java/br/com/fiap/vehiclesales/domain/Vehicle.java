package br.com.fiap.vehiclesales.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity @Table(name="vehicles")
public class Vehicle {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(nullable=false,length=80) private String brand;
 @Column(nullable=false,length=120) private String model;
 @Column(name="manufacture_year",nullable=false) private Integer year;
 @Column(nullable=false,length=50) private String color;
 @Column(nullable=false,precision=15,scale=2) private BigDecimal price;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private VehicleStatus status;
 @Column(name="created_at",nullable=false,updatable=false) private LocalDateTime createdAt;
 @Column(name="updated_at",nullable=false) private LocalDateTime updatedAt;
 protected Vehicle() {}
 public Vehicle(String brand,String model,Integer year,String color,BigDecimal price){this.brand=brand;this.model=model;this.year=year;this.color=color;this.price=price;this.status=VehicleStatus.AVAILABLE;}
 @PrePersist void prePersist(){createdAt=LocalDateTime.now();updatedAt=createdAt;if(status==null)status=VehicleStatus.AVAILABLE;}
 @PreUpdate void preUpdate(){updatedAt=LocalDateTime.now();}
 public void update(String brand,String model,Integer year,String color,BigDecimal price){this.brand=brand;this.model=model;this.year=year;this.color=color;this.price=price;}
 public void markSold(){this.status=VehicleStatus.SOLD;}
 public UUID getId(){return id;} public String getBrand(){return brand;} public String getModel(){return model;} public Integer getYear(){return year;} public String getColor(){return color;} public BigDecimal getPrice(){return price;} public VehicleStatus getStatus(){return status;} public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
}

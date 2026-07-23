package br.com.fiap.vehiclesales.dto;
import br.com.fiap.vehiclesales.domain.*;
import java.math.BigDecimal; import java.time.LocalDateTime; import java.util.UUID;
public record VehicleResponse(UUID id,String brand,String model,Integer year,String color,BigDecimal price,VehicleStatus status,LocalDateTime createdAt,LocalDateTime updatedAt){
 public static VehicleResponse from(Vehicle v){return new VehicleResponse(v.getId(),v.getBrand(),v.getModel(),v.getYear(),v.getColor(),v.getPrice(),v.getStatus(),v.getCreatedAt(),v.getUpdatedAt());}
}

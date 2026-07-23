package br.com.fiap.vehiclesales.dto;
import br.com.fiap.vehiclesales.domain.Sale;
import java.math.BigDecimal; import java.time.LocalDateTime; import java.util.UUID;
public record SaleResponse(UUID saleId,UUID vehicleId,String buyerId,BigDecimal salePrice,LocalDateTime purchasedAt){public static SaleResponse from(Sale s){return new SaleResponse(s.getId(),s.getVehicle().getId(),s.getBuyerId(),s.getSalePrice(),s.getPurchasedAt());}}

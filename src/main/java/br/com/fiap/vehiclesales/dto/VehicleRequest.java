package br.com.fiap.vehiclesales.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record VehicleRequest(
 @NotBlank @Size(max=80) String brand,
 @NotBlank @Size(max=120) String model,
 @NotNull @Min(1886) @Max(2100) Integer year,
 @NotBlank @Size(max=50) String color,
 @NotNull @DecimalMin(value="0.01") @Digits(integer=13,fraction=2) BigDecimal price) {}

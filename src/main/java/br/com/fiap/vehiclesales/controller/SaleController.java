package br.com.fiap.vehiclesales.controller;

import br.com.fiap.vehiclesales.dto.SaleResponse;
import br.com.fiap.vehiclesales.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Vendas", description = "Compra de veículos e consulta de compras")
@SecurityRequirement(name = "keycloak")
public class SaleController {

    private final SaleService service;

    public SaleController(SaleService service) {
        this.service = service;
    }

    @PostMapping("/vehicles/{vehicleId}/purchase")
    @Operation(summary = "Comprar veículo", description = "O comprador é identificado automaticamente pelo claim sub do JWT.")
    public ResponseEntity<SaleResponse> purchase(
            @PathVariable UUID vehicleId,
            @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.purchase(vehicleId, jwt.getSubject()));
    }

    @GetMapping("/sales/me")
    @Operation(summary = "Listar compras do comprador autenticado")
    public List<SaleResponse> mine(@AuthenticationPrincipal Jwt jwt) {
        return service.mine(jwt.getSubject());
    }
}

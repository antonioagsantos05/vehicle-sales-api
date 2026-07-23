package br.com.fiap.vehiclesales.controller;

import br.com.fiap.vehiclesales.domain.VehicleStatus;
import br.com.fiap.vehiclesales.dto.VehicleRequest;
import br.com.fiap.vehiclesales.dto.VehicleResponse;
import br.com.fiap.vehiclesales.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vehicles")
@Tag(name = "Veículos", description = "Cadastro, edição e consulta de veículos")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cadastrar veículo", security = @SecurityRequirement(name = "keycloak"))
    public ResponseEntity<VehicleResponse> create(@Valid @RequestBody VehicleRequest request) {
        VehicleResponse vehicle = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/vehicles/" + vehicle.id())).body(vehicle);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar veículo", security = @SecurityRequirement(name = "keycloak"))
    public VehicleResponse update(@PathVariable UUID id, @Valid @RequestBody VehicleRequest request) {
        return service.update(id, request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar veículo por ID")
    public VehicleResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping("/available")
    @Operation(summary = "Listar veículos disponíveis por preço crescente")
    public List<VehicleResponse> available() {
        return service.list(VehicleStatus.AVAILABLE);
    }

    @GetMapping("/sold")
    @Operation(summary = "Listar veículos vendidos por preço crescente")
    public List<VehicleResponse> sold() {
        return service.list(VehicleStatus.SOLD);
    }
}

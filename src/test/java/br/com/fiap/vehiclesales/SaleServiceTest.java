package br.com.fiap.vehiclesales;

import br.com.fiap.vehiclesales.domain.Sale;
import br.com.fiap.vehiclesales.domain.Vehicle;
import br.com.fiap.vehiclesales.domain.VehicleStatus;
import br.com.fiap.vehiclesales.dto.SaleResponse;
import br.com.fiap.vehiclesales.exception.BusinessException;
import br.com.fiap.vehiclesales.exception.NotFoundException;
import br.com.fiap.vehiclesales.repository.SaleRepository;
import br.com.fiap.vehiclesales.repository.VehicleRepository;
import br.com.fiap.vehiclesales.service.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private SaleRepository saleRepository;

    private SaleService service;

    @BeforeEach
    void setUp() {
        service = new SaleService(vehicleRepository, saleRepository);
    }

    @Test
    void shouldPurchaseAvailableVehicle() {
        UUID vehicleId = UUID.randomUUID();
        Vehicle vehicle = vehicle();
        Sale sale = new Sale(vehicle, "buyer-123");

        when(vehicleRepository.findByIdForUpdate(vehicleId)).thenReturn(Optional.of(vehicle));
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);

        SaleResponse response = service.purchase(vehicleId, "buyer-123");

        assertEquals("buyer-123", response.buyerId());
        assertEquals(new BigDecimal("85000.00"), response.salePrice());
        assertEquals(VehicleStatus.SOLD, vehicle.getStatus());
        verify(saleRepository).save(any(Sale.class));
    }

    @Test
    void shouldRejectPurchaseWithoutBuyer() {
        UUID vehicleId = UUID.randomUUID();

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.purchase(vehicleId, " "));

        assertEquals("O comprador deve estar identificado.", exception.getMessage());
        verifyNoInteractions(vehicleRepository, saleRepository);
    }

    @Test
    void shouldRejectPurchaseOfSoldVehicle() {
        UUID vehicleId = UUID.randomUUID();
        Vehicle vehicle = vehicle();
        vehicle.markSold();

        when(vehicleRepository.findByIdForUpdate(vehicleId)).thenReturn(Optional.of(vehicle));

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.purchase(vehicleId, "buyer-123"));

        assertEquals("O veículo não está disponível para compra.", exception.getMessage());
        verify(saleRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenVehicleDoesNotExist() {
        UUID vehicleId = UUID.randomUUID();
        when(vehicleRepository.findByIdForUpdate(vehicleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.purchase(vehicleId, "buyer-123"));
    }

    @Test
    void shouldListSalesByBuyer() {
        Vehicle vehicle = vehicle();
        Sale sale = new Sale(vehicle, "buyer-123");

        when(saleRepository.findByBuyerIdOrderByPurchasedAtDesc("buyer-123"))
                .thenReturn(List.of(sale));

        List<SaleResponse> result = service.mine("buyer-123");

        assertEquals(1, result.size());
        assertEquals("buyer-123", result.getFirst().buyerId());
    }

    private Vehicle vehicle() {
        return new Vehicle("Volkswagen", "Polo", 2023, "Azul", new BigDecimal("85000.00"));
    }
}

package br.com.fiap.vehiclesales;

import br.com.fiap.vehiclesales.domain.Vehicle;
import br.com.fiap.vehiclesales.domain.VehicleStatus;
import br.com.fiap.vehiclesales.dto.VehicleRequest;
import br.com.fiap.vehiclesales.dto.VehicleResponse;
import br.com.fiap.vehiclesales.exception.BusinessException;
import br.com.fiap.vehiclesales.exception.NotFoundException;
import br.com.fiap.vehiclesales.repository.VehicleRepository;
import br.com.fiap.vehiclesales.service.VehicleService;
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
class VehicleServiceTest {

    @Mock
    private VehicleRepository repository;

    private VehicleService service;

    @BeforeEach
    void setUp() {
        service = new VehicleService(repository);
    }

    @Test
    void shouldCreateVehicle() {
        VehicleRequest request = request("Toyota", "Corolla", "Preto", "135000.00");
        Vehicle savedVehicle = new Vehicle(
                request.brand(), request.model(), request.year(), request.color(), request.price());

        when(repository.save(any(Vehicle.class))).thenReturn(savedVehicle);

        VehicleResponse response = service.create(request);

        assertEquals("Toyota", response.brand());
        assertEquals("Corolla", response.model());
        assertEquals(new BigDecimal("135000.00"), response.price());
        assertEquals(VehicleStatus.AVAILABLE, response.status());
        verify(repository).save(any(Vehicle.class));
    }

    @Test
    void shouldUpdateAvailableVehicle() {
        UUID id = UUID.randomUUID();
        Vehicle vehicle = new Vehicle("Honda", "Civic", 2022, "Branco", new BigDecimal("120000.00"));
        VehicleRequest request = request("Honda", "Civic Touring", "Cinza", "145000.00");

        when(repository.findById(id)).thenReturn(Optional.of(vehicle));

        VehicleResponse response = service.update(id, request);

        assertEquals("Civic Touring", response.model());
        assertEquals("Cinza", response.color());
        assertEquals(new BigDecimal("145000.00"), response.price());
        verify(repository).findById(id);
    }

    @Test
    void shouldNotUpdateSoldVehicle() {
        UUID id = UUID.randomUUID();
        Vehicle vehicle = new Vehicle("Ford", "Ka", 2020, "Prata", new BigDecimal("55000.00"));
        vehicle.markSold();

        when(repository.findById(id)).thenReturn(Optional.of(vehicle));

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.update(id, request("Ford", "Ka SE", "Prata", "57000.00")));

        assertEquals("Veículo vendido não pode ser editado.", exception.getMessage());
    }

    @Test
    void shouldThrowWhenVehicleDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.get(id));
    }

    @Test
    void shouldListVehiclesByStatus() {
        Vehicle first = new Vehicle("Fiat", "Mobi", 2021, "Vermelho", new BigDecimal("60000.00"));
        Vehicle second = new Vehicle("Toyota", "Yaris", 2022, "Branco", new BigDecimal("90000.00"));

        when(repository.findByStatusOrderByPriceAsc(VehicleStatus.AVAILABLE))
                .thenReturn(List.of(first, second));

        List<VehicleResponse> result = service.list(VehicleStatus.AVAILABLE);

        assertEquals(2, result.size());
        assertEquals(new BigDecimal("60000.00"), result.get(0).price());
        assertEquals(new BigDecimal("90000.00"), result.get(1).price());
    }

    private VehicleRequest request(String brand, String model, String color, String price) {
        return new VehicleRequest(brand, model, 2023, color, new BigDecimal(price));
    }
}

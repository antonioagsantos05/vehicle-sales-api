package br.com.fiap.vehiclesales.repository;
import br.com.fiap.vehiclesales.domain.*; import jakarta.persistence.LockModeType; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param;
import java.util.*;
public interface VehicleRepository extends JpaRepository<Vehicle,UUID>{
 List<Vehicle> findByStatusOrderByPriceAsc(VehicleStatus status);
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select v from Vehicle v where v.id=:id") Optional<Vehicle> findByIdForUpdate(@Param("id") UUID id);
}

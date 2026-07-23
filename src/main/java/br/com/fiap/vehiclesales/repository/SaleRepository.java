package br.com.fiap.vehiclesales.repository;
import br.com.fiap.vehiclesales.domain.Sale; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface SaleRepository extends JpaRepository<Sale,UUID>{List<Sale> findByBuyerIdOrderByPurchasedAtDesc(String buyerId);}

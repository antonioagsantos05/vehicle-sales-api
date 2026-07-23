package br.com.fiap.vehiclesales.service;
import br.com.fiap.vehiclesales.domain.*; import br.com.fiap.vehiclesales.dto.SaleResponse; import br.com.fiap.vehiclesales.exception.*; import br.com.fiap.vehiclesales.repository.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class SaleService {
 private final VehicleRepository vehicles; private final SaleRepository sales; public SaleService(VehicleRepository vehicles,SaleRepository sales){this.vehicles=vehicles;this.sales=sales;}
 @Transactional public SaleResponse purchase(UUID vehicleId,String buyerId){if(buyerId==null||buyerId.isBlank())throw new BusinessException("O comprador deve estar identificado."); Vehicle v=vehicles.findByIdForUpdate(vehicleId).orElseThrow(()->new NotFoundException("Veículo não encontrado: "+vehicleId)); if(v.getStatus()!=VehicleStatus.AVAILABLE)throw new BusinessException("O veículo não está disponível para compra."); v.markSold(); return SaleResponse.from(sales.save(new Sale(v,buyerId)));}
 @Transactional(readOnly=true) public List<SaleResponse> mine(String buyerId){return sales.findByBuyerIdOrderByPurchasedAtDesc(buyerId).stream().map(SaleResponse::from).toList();}
}

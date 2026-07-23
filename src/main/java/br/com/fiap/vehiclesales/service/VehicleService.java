package br.com.fiap.vehiclesales.service;
import br.com.fiap.vehiclesales.domain.*; import br.com.fiap.vehiclesales.dto.*; import br.com.fiap.vehiclesales.exception.*; import br.com.fiap.vehiclesales.repository.VehicleRepository; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class VehicleService {
 private final VehicleRepository repository; public VehicleService(VehicleRepository repository){this.repository=repository;}
 @Transactional public VehicleResponse create(VehicleRequest r){return VehicleResponse.from(repository.save(new Vehicle(r.brand(),r.model(),r.year(),r.color(),r.price())));}
 @Transactional public VehicleResponse update(UUID id,VehicleRequest r){Vehicle v=getEntity(id); if(v.getStatus()==VehicleStatus.SOLD)throw new BusinessException("Veículo vendido não pode ser editado."); v.update(r.brand(),r.model(),r.year(),r.color(),r.price()); return VehicleResponse.from(v);}
 @Transactional(readOnly=true) public VehicleResponse get(UUID id){return VehicleResponse.from(getEntity(id));}
 @Transactional(readOnly=true) public List<VehicleResponse> list(VehicleStatus status){return repository.findByStatusOrderByPriceAsc(status).stream().map(VehicleResponse::from).toList();}
 private Vehicle getEntity(UUID id){return repository.findById(id).orElseThrow(()->new NotFoundException("Veículo não encontrado: "+id));}
}

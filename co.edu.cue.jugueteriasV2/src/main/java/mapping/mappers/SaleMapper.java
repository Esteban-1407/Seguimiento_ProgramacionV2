package mapping.mappers;

import mapping.dtos.SaleDTO;
import model.sale;

public class SaleMapper {
    public static SaleDTO mapFromModel(sale sale){
        return new SaleDTO(sale.getId(),sale.getCliente(),sale.getEmployees());
    }
    public static sale mapFromDTO(SaleDTO dto){
        return sale.builder()
                .id(dto.id())
                .cliente(dto.cliente())
                .employees(dto.employees())
                .build();
    }
}

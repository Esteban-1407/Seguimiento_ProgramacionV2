package mapping.mappers;

import mapping.dtos.ClienteDTO;
import model.cliente;

public class ClienteMapper {
    public static ClienteDTO mapFromModel(cliente cliente){
        return new ClienteDTO(cliente.getId(), cliente.getName(), cliente.getIDnumber(), cliente.getDate_birth());

    }
    public static cliente mapFromDTO(ClienteDTO clienteDTO){
        return cliente.builder()
                .id(clienteDTO.id())
                .name(clienteDTO.name())
                .IDnumber(clienteDTO.iDnumber())
                .date_birth(clienteDTO.date_birth())
                .build();
    }
}

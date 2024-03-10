package mapping.dtos;

import lombok.Builder;

import java.util.Date;
@Builder
public record EmployeesDTO (int id, String user, String password, Date employment_start_date){
}

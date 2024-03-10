package mapping.dtos;

import lombok.*;
import model.category;
@Builder

public record ToyDTO(int id, String name, int price, category category, int stock) {
}

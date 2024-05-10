package az.nicat.hospitalsystem.dto.response;

import az.nicat.hospitalsystem.entity.Authority;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response {
    private Long id;
    String jwt;
    private List<Authority> authorities;
}

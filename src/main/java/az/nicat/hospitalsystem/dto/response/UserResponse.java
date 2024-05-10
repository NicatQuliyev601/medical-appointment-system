package az.nicat.hospitalsystem.dto.response;

import az.nicat.hospitalsystem.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private List<Authority> authorities;
    private boolean passwordChanged;
}

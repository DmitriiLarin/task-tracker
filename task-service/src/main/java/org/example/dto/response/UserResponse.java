package org.example.dto.response;

import org.hibernate.validator.constraints.UUID;

public record UserResponse(String name, String surname, String email, UUID userId) {
}

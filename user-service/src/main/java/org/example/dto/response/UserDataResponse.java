package org.example.dto.response;

import java.util.UUID;

public record UserDataResponse(UUID userId, String email) {
}

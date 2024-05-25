package dev.backend.tutor.repositories.sql.refresh;

import dev.backend.tutor.entities.auth.RefreshToken;

public interface CustomRefreshTokenRepository {

    RefreshToken insert(RefreshToken refreshToken);
}

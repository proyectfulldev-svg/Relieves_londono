package com.relieveslondono.gestion.security;

import java.time.Instant;

public record TokenGenerado(String token, Instant expiraEn) {
}

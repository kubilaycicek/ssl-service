package com.kubilaycicek.sslservice.api.response;

import com.kubilaycicek.sslservice.api.dto.DomainDTO;

public record CheckSSLResponse(boolean verify) {
}

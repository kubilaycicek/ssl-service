package com.kubilaycicek.sslservice.api.request;

import com.kubilaycicek.sslservice.api.dto.DomainDTO;

public record CheckSSLRequest(DomainDTO domainDTO) {
}

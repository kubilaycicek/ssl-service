package com.kubilaycicek.sslservice.service;

import com.kubilaycicek.sslservice.api.dto.DomainDTO;

public interface CertificateService {
    boolean checkSSLCertificate(DomainDTO domainDTO);
}

package com.kubilaycicek.sslservice.api.controller;

import com.kubilaycicek.sslservice.api.request.CheckSSLRequest;
import com.kubilaycicek.sslservice.api.response.CheckSSLResponse;
import com.kubilaycicek.sslservice.service.CertificateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/ssl")
@RestController
public class CertificateController {
    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping("verify")
    public ResponseEntity<CheckSSLResponse> checkSSL(@RequestBody CheckSSLRequest checkSSLRequest) {
        boolean verify = certificateService.checkSSLCertificate(checkSSLRequest.domainDTO());
        return ResponseEntity.ok(new CheckSSLResponse(verify));
    }
}

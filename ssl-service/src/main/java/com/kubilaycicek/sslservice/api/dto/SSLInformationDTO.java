package com.kubilaycicek.sslservice.api.dto;

public record SSLInformationDTO(String signature,String serialNumber,String issuerName, String notBefore, String notAfter,int version) {
    @Override
    public String toString() {
        return "SSLInformationDTO{" + "issuerName='" + issuerName + '\'' + ", notBefore='" + notBefore + '\'' + ", notAfter='" + notAfter + '\'' + '}';
    }
}

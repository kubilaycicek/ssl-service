package com.kubilaycicek.sslservice.service;

import com.kubilaycicek.sslservice.api.dto.DomainDTO;
import com.kubilaycicek.sslservice.api.dto.SSLInformationDTO;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CertificateServiceImpl implements CertificateService {
    private static final Logger LOGGER = Logger.getLogger(CertificateService.class.getName());

    @Override
    public boolean checkSSLCertificate(DomainDTO domainDTO) {
        try {
            URL url = new URL(domainDTO.url());
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            if (httpsURLConnection != null && httpsURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Certificate[] certs = httpsURLConnection.getServerCertificates();
                for (Certificate certificate : certs) {
                    var item = ((X509Certificate) certificate);
                    item.checkValidity();
                    printSSLInformation(getSSLInformation(item));
                }
            }
        } catch (CertificateNotYetValidException e) {
            LOGGER.log(Level.WARNING, domainDTO.url() + " is not yet valid");
            return false;
        } catch (CertificateExpiredException e) {
            LOGGER.log(Level.WARNING, domainDTO.url() + " is not expired");
            return false;
        } catch (SSLPeerUnverifiedException e) {
            LOGGER.log(Level.WARNING, domainDTO.url() + " certificate is not unverified");
            return false;
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, domainDTO.url() + " did not connect !");
            return false;
        }

        return true;
    }

    public SSLInformationDTO getSSLInformation(Certificate certificate) {
        var item = ((X509Certificate) certificate);
        String signature = Arrays.toString(item.getSignature());
        String serialNumber = item.getSerialNumber().toString();
        String issuerName = item.getIssuerX500Principal().getName();
        String notBefore = item.getNotBefore().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
        String notAfter = item.getNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
        int version = item.getVersion();
        return new SSLInformationDTO(signature, serialNumber, issuerName, notBefore, notAfter, version);
    }

    private void printSSLInformation(SSLInformationDTO sslInformationDTO) {
        LOGGER.info(sslInformationDTO.toString() + "\n");
    }
}

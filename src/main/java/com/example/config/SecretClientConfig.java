package com.example.config;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretClientConfig {
    @Bean
    public SecretClient getSecretClient() {
        String keyVaultUrl = "https://pldeltalake-vault.vault.azure.net";
        System.out.println(System.getenv("AZURE_CLIENT_ID"));
        System.out.println(System.getenv("AZURE_CLIENT_SECRET"));
        System.out.println(System.getenv("AZURE_TENANT_ID"));
        System.out.println("Vault url is " + keyVaultUrl);
        return  new SecretClientBuilder()
                .vaultUrl(keyVaultUrl)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
    }
}

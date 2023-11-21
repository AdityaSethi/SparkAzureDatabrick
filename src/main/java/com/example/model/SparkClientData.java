package com.example.model;

import lombok.Data;

@Data
public class SparkClientData {
    String sparkSessionUUID;
    String azureAppName;
    String azureClientId;
    String azureClientSecret;
    String azureStorageAccountKey;
    String azureTenantId;

    @Override
    public String toString() {
        return "SparkClientData{" +
                "sparkSessionUUID='" + sparkSessionUUID + '\'' +
                ", azureAppName='" + azureAppName + '\'' +
                ", azureClientId='" + azureClientId + '\'' +
                ", azureClientSecret='" + azureClientSecret + '\'' +
                ", azureStorageAccountKey='" + azureStorageAccountKey + '\'' +
                ", azureTenantId='" + azureTenantId + '\'' +
                '}';
    }
}

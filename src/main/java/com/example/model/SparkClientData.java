package com.example.model;

import lombok.Data;

@Data
public class SparkClientData {
    String azureAppName;
    String azureClientId;
    String azureClientSecret;
    String azureStorageAccountName;
    String azureStorageAccountKey;
    String azureTenantId;
    String azureContainerName;
    String azureFilePath;
    String azureFileName;
    String azureConditionId;

    @Override
    public String toString() {
        return "SparkClientData{" +
                "azureAppName='" + azureAppName + '\'' +
                ", azureClientId='" + azureClientId + '\'' +
                ", azureClientSecret='" + azureClientSecret + '\'' +
                ", azureStorageAccountName='" + azureStorageAccountName + '\'' +
                ", azureStorageAccountKey='" + azureStorageAccountKey + '\'' +
                ", azureTenantId='" + azureTenantId + '\'' +
                ", azureContainerName='" + azureContainerName + '\'' +
                ", azureFilePath='" + azureFilePath + '\'' +
                ", azureFileName='" + azureFileName + '\'' +
                ", azureConditionId='" + azureConditionId + '\'' +
                '}';
    }
}

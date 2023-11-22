package com.example.model;

import lombok.Data;

@Data
public class SparkWriteInput {
    String sparkSessionUUID;
    String azureFilePath;
    String azureFileName;
    String azureConditionId;
    String azureStorageAccountName;
    String azureContainerName;
}

package com.example.services;

import com.example.model.SparkClientData;
import com.example.model.SparkReadInput;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadDataService {

    public ReadDataService() {
        System.setProperty("hadoop.home.dir", "C:\\Users\\Asus\\Downloads\\hadoop-common-2.2.0-bin-master\\hadoop-common-2.2.0-bin-master");
    }

    public SparkSession createSparkSession(SparkClientData sparkClientData) {
        String azureAppName = sparkClientData.getAzureAppName(); //PSDeltaLakeDemoRG
        String azureClientId = sparkClientData.getAzureClientId();//"b33ce05c-63e0-42cb-9d75-6efe5a0d86df";
        String azureClientSecret = sparkClientData.getAzureClientSecret();//"n+NZtxvNMmEAzq8sgnGGFz0if/0ZXQPZrmwqHjj3UZE=";

        String azureStorageAccountKey = sparkClientData.getAzureStorageAccountKey();//"tt92YNKNv9SpcUnvjSHIsAdERGlS03wZnYJqZac2FklsT1vcUoG6T08+OSDC89YIWzTA/WNHLLo1+AStIj3KEQ==";
        String tenantId = sparkClientData.getAzureTenantId();//6a310e33-5293-4125-87b1-0c69a570347f

        // Create a Spark configuration
        SparkConf conf = new SparkConf().setAppName(azureAppName);
        SparkSession spark = SparkSession.builder().config(conf).config("spark.master", "local")
                .config("fs.azure.account.auth.type.pltaxidatalake1.dfs.core.windows.net", "SharedKey")
                .config("fs.azure.account.key.pltaxidatalake1.dfs.core.windows.net", azureStorageAccountKey)
                .getOrCreate();

        // // Set Azure Storage configuration
        spark.conf().set("fs.azure.account.auth.type", "OAuth");
        spark.conf().set("fs.azure.account.oauth.provider.type", "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider");
        spark.conf().set("fs.azure.account.auth.oauth2.client.id", azureClientId);
        spark.conf().set("fs.azure.account.auth.oauth2.client.secret", azureClientSecret);
        spark.conf().set("fs.azure.account.auth.oauth2.client.endpoint", "https://login.microsoftonline.com/" + tenantId + "/oauth2/token");
        spark.conf().set("spark.databricks.delta.preview.enabled", "true");

        System.out.println("spark session ******** : "+spark);
        return spark;
    }

    public List<String> getDataFromContainer(SparkReadInput sparkReadInput) {
        String azureStorageAccountName = sparkReadInput.getAzureStorageAccountName();//"pltaxidatalake1";

        // Define the Azure Storage container and file path
        String containerName = sparkReadInput.getAzureContainerName();//"taxidata";
        String filePath = sparkReadInput.getAzureFilePath();//"Output";
        String fileName = sparkReadInput.getAzureFileName();//"YellowTaxis.delta";
        String conditionId = sparkReadInput.getAzureConditionId();//"VendorId=4";

        // Read data from Azure Storage into a DataFrame
        String azurePath = "abfss://" + containerName + "@" + azureStorageAccountName + ".dfs.core.windows.net/" + filePath + "/" + fileName;

        System.out.println("**********************");
        System.out.println(azurePath);
        System.out.println("**********************");

        Dataset<Row> dataset;
        if (!conditionId.isEmpty())
            dataset = sparkReadInput.getSparkSession().read().format("delta").load(azurePath).where(conditionId);
        else
            dataset = sparkReadInput.getSparkSession().read().format("delta").load(azurePath);

        dataset.show();

        /*for (String s :dataset.toJSON().collectAsList()) {
            JSONObject jsonObject = new JSONObject(s);

        System.out.println("************"+jsonObject+"************");
        }*/
        List<String> list = dataset.toJSON().collectAsList();

        System.out.println("data list : " + list);
        return list;
    }
}

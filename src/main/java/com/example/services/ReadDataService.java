package com.example.services;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadDataService {

    public ReadDataService() {
    }

    public List<String> getDataFromContainer() {
        System.out.println("Hello World New!");
        System.setProperty("hadoop.home.dir", "C:\\Users\\Asus\\Downloads\\hadoop-common-2.2.0-bin-master\\hadoop-common-2.2.0-bin-master");

        // Set your Azure Storage account name and key
        String azureClientId = "b33ce05c-63e0-42cb-9d75-6efe5a0d86df";
        String azureClientSecret = "n+NZtxvNMmEAzq8sgnGGFz0if/0ZXQPZrmwqHjj3UZE=";
        String azureStorageAccountName = "pltaxidatalake1";
        String azureStorageAccountKey = "tt92YNKNv9SpcUnvjSHIsAdERGlS03wZnYJqZac2FklsT1vcUoG6T08+OSDC89YIWzTA/WNHLLo1+AStIj3KEQ==";

        // Create a Spark configuration
        SparkConf conf = new SparkConf().setAppName("PSDeltaLakeDemoRG");
        SparkSession spark = SparkSession.builder().config(conf).config("spark.master", "local")
                .config("fs.azure.account.auth.type.pltaxidatalake1.dfs.core.windows.net", "SharedKey")
                .config("fs.azure.account.key.pltaxidatalake1.dfs.core.windows.net", azureStorageAccountKey)
                .getOrCreate();

        // // Set Azure Storage configuration
        spark.conf().set("fs.azure.account.auth.type", "OAuth");
        spark.conf().set("fs.azure.account.oauth.provider.type", "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider");
        spark.conf().set("fs.azure.account.auth.oauth2.client.id", azureClientId);
        spark.conf().set("fs.azure.account.auth.oauth2.client.secret", azureClientSecret);
        spark.conf().set("fs.azure.account.auth.oauth2.client.endpoint", "https://login.microsoftonline.com/6a310e33-5293-4125-87b1-0c69a570347f/oauth2/token");
        spark.conf().set("spark.databricks.delta.preview.enabled", "true");
        // Define the Azure Storage container and file path
        String containerName = "taxidata";
        String filePath = "Output/YellowTaxis.delta";

        // Read data from Azure Storage into a DataFrame
        String azurePath = "abfss://" + containerName + "@" + azureStorageAccountName + ".dfs.core.windows.net/" + filePath;

        System.out.println("**********************");
        System.out.println(azurePath);
        System.out.println("**********************");

        Dataset<Row> dataset = spark.read().format("delta").load(azurePath).where("VendorId=4");

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

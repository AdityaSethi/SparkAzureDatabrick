package com.example;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import io.delta.tables.DeltaTable;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World New!" );
        
        // Set your Azure Storage account name and key
        String azureClientId = "b33ce05c-63e0-42cb-9d75-6efe5a0d86df";
        String azureClientSecret = "n+NZtxvNMmEAzq8sgnGGFz0if/0ZXQPZrmwqHjj3UZE=";
        String azureStorageAccountName = "pltaxidatalake1";
        String azureStorageAccountKey = "tt92YNKNv9SpcUnvjSHIsAdERGlS03wZnYJqZac2FklsT1vcUoG6T08+OSDC89YIWzTA/WNHLLo1+AStIj3KEQ==";

        // Create a Spark configuration
        SparkConf conf = new SparkConf().setAppName("PSDeltaLakeDemoRG");
        SparkSession spark = SparkSession.builder().config(conf).config("spark.master", "local").getOrCreate();

        // // Set Azure Storage configuration
        // spark.conf().set("fs.azure", "org.apache.hadoop.fs.azure.NativeAzureFileSystem");
        // spark.conf().set("fs.azure.account.auth.type", "SharedKey");
        // spark.conf().set("fs.azure.account.key." + azureStorageAccountName, azureStorageAccountKey);

        // spark.conf().set("fs.azure", "org.apache.hadoop.fs.azure.NativeAzureFileSystem");
        spark.conf().set("fs.azure.account.auth.type", "OAuth");
        spark.conf().set("fs.azure.account.oauth.provider.type", "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider");
        spark.conf().set("fs.azure.account.auth.oauth2.client.id", azureClientId);
        spark.conf().set("fs.azure.account.auth.oauth2.client.secret", azureClientSecret);
        spark.conf().set("fs.azure.account.auth.oauth2.client.endpoint", "https://login.microsoftonline.com/6a310e33-5293-4125-87b1-0c69a570347f/oauth2/token");
        spark.conf().set("spark.databricks.delta.preview.enabled", "true");
        // Define the Azure Storage container and file path
        String containerName = "taxidata";
        String filePath = "/mnt/datalake/Output/YellowTaxis.delta";

        // Read data from Azure Storage into a DataFrame
        String azurePath = "abfss://" + containerName + "@" + azureStorageAccountName + ".dfs.core.windows.net/" +filePath;
        
        Dataset<Row> data = spark.read().format("delta").load(azurePath);
        // DeltaTable deltaTable = DeltaTable.forPath(spark, azurePath);
        // Dataset<Row> data = deltaTable.toDF();
        // Perform operations on the data (e.g., show the content)
        data.show();

        // Stop the Spark session
        spark.stop();
    }
}

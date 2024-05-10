package optimove.sdk;

import com.google.cloud.storage.Blob;
import com.google.cloud.ReadChannel;

import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.file.DataFileStream;

import java.io.IOException;
import java.nio.channels.Channels;

import java.io.InputStream;

import org.apache.log4j.Logger;


public class Engagement {
    private final EngagementSettings settings;
    private final Logger logger;

    public Engagement(String decryptionKey, int tenantID, String bucketName, String customersFolderPath,
                      String metadataFilePath, Logger logger) {
        this.settings = new EngagementSettings(decryptionKey, tenantID, bucketName, customersFolderPath, metadataFilePath);
        this.logger = logger;
    }

    public Metadata getMetadata() {
        try {
            Blob fileBlob = StorageSingleton.getBlob(this.settings.getBucketName(), this.settings.getMetadataFilePath());
            String metadataString = StorageSingleton.blobToString(fileBlob, this.settings.getDecryptionKey());

            return MetadataService.jsonStringToMetadata(metadataString);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve metadata: " + e.getMessage(), e);
        }
    }

    public DataFileStream<GenericRecord> getCustomerBatchById(int id) {
        String batchName = "";

        if(id <= 0) {
            throw new IllegalArgumentException("id must be greater than 0");
        }

        if(id < 10) {
            batchName = batchName + "00" + id;
        } else  if(id < 100) {
            batchName = batchName + "0" + id;
        } else {
            batchName = batchName + id;
        }

        String filePath = this.settings.getCustomersFolderPath() + "/customers_file" + batchName + ".deflate.avro";

        try {
            return this.getCustomersFileStream(filePath);
        } catch (Exception e) {
            this.logger.error("Failed to get customer batch by id: " + e.getMessage(), e);
            throw new RuntimeException("Failed to get customer batch by id: " + e.getMessage(), e);
        }
    }

    public DataFileStream<GenericRecord> getCustomersFileStream(String srcFileName) {
        if (srcFileName == null || srcFileName.isEmpty()) {
            throw new IllegalArgumentException("srcFileName is null or empty");
        }

        try (ReadChannel readChanel = StorageSingleton.getReadChanel(this.settings.getBucketName(), srcFileName, this.settings.getDecryptionKey());
             InputStream targetStream = Channels.newInputStream(readChanel)) {

            DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();
            return new DataFileStream<>(targetStream, datumReader);
        } catch (IOException e) {
            this.logger.error("Failed to read customers file stream: " + e.getMessage(), e);
            throw new RuntimeException("Failed to read customers file stream: " + e.getMessage(), e);
        }
    }
}
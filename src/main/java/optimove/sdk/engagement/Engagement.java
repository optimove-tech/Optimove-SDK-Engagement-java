package optimove.sdk.engagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.cloud.ReadChannel;

import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.file.DataFileStream;

import java.io.IOException;
import java.nio.channels.Channels;

import java.io.InputStream;

import org.slf4j.Logger;

public class Engagement {
    private final EngagementSettings settings;
    private final Logger logger;

    public Engagement(String decryptionKey, int tenantID, String bucketName, String customersFolderPath,
                      String metadataFilePath, Logger logger) {
        this.settings = new EngagementSettings(decryptionKey, tenantID, bucketName, customersFolderPath, metadataFilePath);
        this.logger = logger;
    }

    // Constructor that accepts JSON string and logger
    public Engagement(String jsonConfig, Logger logger) {
        if (jsonConfig == null || logger == null) {
            throw new IllegalArgumentException("Neither jsonConfig nor logger can be null");
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            EngagementConfig config = mapper.readValue(jsonConfig, EngagementConfig.class);
            this.settings = createSettingsFromConfig(config);
            this.logger = logger;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse engagement configuration: " + e.getMessage(), e);
        }
    }

    // Constructor that accepts any object and logger
    public Engagement(Object configObject, Logger logger) {
        if (configObject == null || logger == null) {
            throw new IllegalArgumentException("Neither configObject nor logger can be null");
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            // First convert the object to a JSON string
            String jsonString = mapper.writeValueAsString(configObject);
            // Then convert it to our EngagementConfig class
            EngagementConfig config = mapper.readValue(jsonString, EngagementConfig.class);

            this.settings = createSettingsFromConfig(config);
            this.logger = logger;
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert object to engagement configuration: " + e.getMessage(), e);
        }
    }

    // Helper method to create settings from config
    private EngagementSettings createSettingsFromConfig(EngagementConfig config) {
        return new EngagementSettings(
                config.getDecryptionKey(),
                config.getTenantID(),
                config.getBucketName(),
                config.getCustomersFolderPath(),
                config.getMetadataFilePath()
        );
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
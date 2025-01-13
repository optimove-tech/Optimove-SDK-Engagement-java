package org.example;

import org.apache.avro.file.DataFileStream;
import org.apache.avro.generic.GenericRecord;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import optimove.sdk.engagement.Engagement;
import optimove.sdk.engagement.Metadata;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);

        Engagement engagement = new Engagement("{\r\n  \"EventTypeID\": 14,\r\n  \"TimeStamp\": \"2025-01-08 08:58\",\r\n  \"CampaignID\": 8677,\r\n  \"EngagementID\": 9061,\r\n  \"TenantID\": 593,\r\n  \"BucketName\": \"optigration-external-eu\",\r\n  \"CustomersFolderPath\": \"593_2025-01-08 09 00_optigration demo_507_9061/customers\",\r\n  \"MetadataFilePath\": \"593_2025-01-08 09 00_optigration demo_507_9061/metadata_9061\",\r\n  \"DecryptionKey\": \"Sdy+upo5M2yFYOWYqk3cxBoZHq2vDNaXRZeqxUSITIU=\",\r\n  \"ChannelID\": 507\r\n}", logger);

        Metadata metadata = engagement.getMetadata();
        System.out.println(metadata.getNumberOfBatches());

        for (int fileNumber = 1; fileNumber <= metadata.getNumberOfBatches(); fileNumber++) {
            System.out.println("file number: " + fileNumber);

            DataFileStream<GenericRecord> dataFileReader = null;
            try {
                dataFileReader = engagement.getCustomerBatchById(fileNumber);
                GenericRecord record = null;
                while (dataFileReader.hasNext()) {
                    record = dataFileReader.next(record);
                    System.out.println(record);
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }

        System.out.println("end!");
    }
}
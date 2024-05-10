package org.example;

import org.apache.avro.file.DataFileStream;
import org.apache.avro.generic.GenericRecord;
import java.io.IOException;

import optimove.sdk.Engagement;
import optimove.sdk.Metadata;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();

        Engagement engagement = new Engagement(null, 1, "optigration-internal-dev", "260_2024-03-14 06 40_google-customer-match_22_286384/customers", "260_2024-03-14 06 40_google-customer-match_22_286384/metadata_286384", logger);

        Metadata metadata = engagement.getMetadata();
        System.out.println(metadata.getNumberOfFiles());

        for (int fileNumber = 1; fileNumber <= metadata.getNumberOfFiles(); fileNumber++) {
            System.out.println("file number: " + fileNumber);

            try (DataFileStream<GenericRecord> dataFileReader = engagement.getCustomerBatchById(fileNumber)) {
                GenericRecord record = null;
                while (dataFileReader.hasNext()) {
                    record = dataFileReader.next(record);
                    System.out.println(record);
                }
            } catch (IOException e) {
                Main.logger.error(e.getMessage(), e);
            }
        }

        System.out.println("end!");
    }
}
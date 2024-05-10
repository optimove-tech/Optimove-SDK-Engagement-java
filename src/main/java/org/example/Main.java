package org.example;

import org.apache.avro.file.DataFileStream;
import org.apache.avro.generic.GenericRecord;
import java.io.IOException;

import optimove.sdk.Engagement;
import optimove.sdk.Metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);

        Engagement engagement = new Engagement(null, 1, "bucket-name", "customers-folder/customers", "metadata-path/metadata_287934", logger);

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
                logger.error(e.getMessage(), e);
            }
        }

        System.out.println("end!");
    }
}
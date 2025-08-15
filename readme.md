# Optimove Java SDK

This SDK provides a Java library for working with customer engagement data. The library includes classes for getting engagement data, as well as metadata about the engagement.

## Getting Started

To use this SDK in your project, you'll need to add it as a dependency in your Maven `pom.xml` file:

```xml
<dependency>
    <groupId>io.github.maryan-opti</groupId>
    <artifactId>Optimove-SDK-Engagement-java</artifactId>
    <version>1.0.4</version>
</dependency>
```
Then, you can import the necessary classes from the SDK into your Java code:

```java
import optimove.sdk.engagement.Engagement;
import optimove.sdk.engagement.Metadata;
```

## Example Usage
Here's an example of how to use the SDK to load and print out customer engagement data:

```java
package org.example;

import org.apache.avro.file.DataFileStream;
import org.apache.avro.generic.GenericRecord;

import java.io.IOException;

import optimove.sdk.engagement.Engagement;
import optimove.sdk.engagement.Metadata;

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
```

In this example, we're creating a new `Engagement` object, which represents a customer engagement. We pass in some configuration options, such as the engagement ID and the location of the customer data files.

We then retrieve the `Metadata` object for the engagement, which contains information about the engagement such as the number of customer data files.

Finally, we loop through each customer data file and print out the contents using the `getCustomerBatchById` method of the `Engagement` object.

## Error Handling
### Metadata Retrieval
When retrieving metadata for an engagement, the SDK may encounter errors such as a missing or malformed metadata file. In these cases, the getMetadata() method will throw a RuntimeException with a message indicating the cause of the error. For example:


```java
try {
Metadata metadata = engagement.getMetadata();
// use metadata
} catch (RuntimeException e) {
logger.error("Failed to retrieve metadata: " + e.getMessage(), e);
// handle error
}
```
### Customer Data Retrieval
When retrieving customer data for an engagement, the SDK may encounter errors such as a missing or malformed customer data file, or an invalid file ID.

In the case of a missing or malformed customer data file, the getCustomerBatchById() method will throw a RuntimeException with a message indicating the cause of the error. For example:


```java
try (DataFileStream<GenericRecord> dataFileReader = engagement.getCustomerBatchById(fileNumber)) {
// use dataFileReader
} catch (RuntimeException e) {
logger.error("Failed to get customer batch by id: " + e.getMessage(), e);
// handle error
}
```
If an invalid file ID is provided, the getCustomerBatchById() method will throw an IllegalArgumentException with a message indicating the cause of the error. For example:


```java
try (DataFileStream<GenericRecord> dataFileReader = engagement.getCustomerBatchById(-1)) {
// use dataFileReader
} catch (IllegalArgumentException e) {
logger.error("Invalid file id: " + e.getMessage(), e);
// handle error
}
```
### Customer Data File Stream
When creating a file stream to read customer data, the SDK may encounter errors such as a missing or malformed customer data file, or an invalid file path.

In the case of a missing or malformed customer data file, the getCustomersFileStream() method will throw a RuntimeException with a message indicating the cause of the error. For example:


```java
try {
DataFileStream<GenericRecord> dataFileStream = engagement.getCustomersFileStream("customers_file1.avro");
// use dataFileStream
} catch (RuntimeException e) {
logger.error("Failed to read customers file stream: " + e.getMessage(), e);
// handle error
}
```
If an invalid file path is provided, the getCustomersFileStream() method will throw an IllegalArgumentException with a message indicating the cause of the error. For example:


```java
try {
DataFileStream<GenericRecord> dataFileStream = engagement.getCustomersFileStream(null);
// use dataFileStream
} catch (IllegalArgumentException e) {
logger.error("Invalid file path: " + e.getMessage(), e);
// handle error
}
```

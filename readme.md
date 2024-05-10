# Optimove Java SDK

This SDK provides a Java library for working with customer engagement data. The library includes classes for getting engagement data, as well as metadata about the engagement.

## Getting Started

To use this SDK in your project, you'll need to add it as a dependency in your Maven `pom.xml` file:

```xml
<dependency>
    <groupId>io.github.maryan-opti</groupId>
    <artifactId>Optimove-SDK-Engagement-java</artifactId>
    <version>1.0.0</version>
</dependency>
```
Then, you can import the necessary classes from the SDK into your Java code:

```java
import optimove.sdk.Engagement;
import optimove.sdk.Metadata;
```

## Example Usage
Here's an example of how to use the SDK to load and print out customer engagement data:

```java
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

        Engagement engagement = new Engagement(null, 1, "external-bucket", "file-name/customers", "file-path/metadata_286384", logger);

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

```

In this example, we're creating a new `Engagement` object, which represents a customer engagement. We pass in some configuration options, such as the engagement ID and the location of the customer data files.

We then retrieve the `Metadata` object for the engagement, which contains information about the engagement such as the number of customer data files.

Finally, we loop through each customer data file and print out the contents using the `getCustomerBatchById` method of the `Engagement` object.

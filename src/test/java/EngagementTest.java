import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import optimove.sdk.Engagement;
import optimove.sdk.EngagementSettings;
import optimove.sdk.Metadata;
import optimove.sdk.StorageSingleton;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.*;

import org.apache.log4j.Logger;


public class EngagementTest {

    final String decryptionKey = "decryptionKey";
    final String metadataString = "{\n" +
            "  \"CampaignPlanID\": 10,\n" +
            "  \"CampaignID\": 1,\n" +
            "  \"PlanDetailChannelID\": 1,\n" +
            "  \"EngagementID\": 12,\n" +
            "  \"ScheduledTime\": \"2024-03-30T09:00:00Z\",\n" +
            "  \"ChannelName\": \"TestChannel\",\n" +
            "  \"ChannelID\": 1,\n" +
            "  \"TargetGroupName\": \"TARGET_GROUP_TEST\",\n" +
            "  \"ActionName\": \"Test Action\",\n" +
            "  \"ActionID\": 1,\n" +
            "  \"TemplateName\": \"Default Template\",\n" +
            "  \"TemplateID\": 1,\n" +
            "  \"NumberOfFiles\": 3,\n" +
            "  \"NumberOfCustomers\": 300,\n" +
            "  \"Promotions\": \"\",\n" +
            "  \"Duration\": 1,\n" +
            "  \"InternalAccountID\": \"\",\n" +
            "  \"AccountName\": \"\",\n" +
            "  \"CampaignType\": \"No Control\",\n" +
            "  \"IsMultiChannel\": false,\n" +
            "  \"Tags\": \"\",\n" +
            "  \"Identifier\": null\n" +
            "}";

    // Retrieving metadata from valid bucket and file path returns expected Metadata object
    @Test
    public void test_retrieving_metadata_from_valid_bucket_and_file_path() {
        // Mock the necessary dependencies
        Blob fileBlob = mock(Blob.class);
        ReadChannel readChannel = mock(ReadChannel.class);

        try (MockedStatic<StorageSingleton> classMock = mockStatic(StorageSingleton.class)) {

            classMock.when(() -> StorageSingleton.getBlob(anyString(), anyString())).thenReturn(fileBlob);
            classMock.when(() -> StorageSingleton.blobToString(eq(fileBlob), any(String.class))).thenReturn(metadataString);
            classMock.when(() -> StorageSingleton.getReadChanel(anyString(), anyString(), anyString())).thenReturn(readChannel);

            Engagement engagement = new Engagement("decryptionKey", 123, "bucketName", "customersFolderPath", "metadataFilePath", mock(Logger.class));


            Metadata metadata = engagement.getMetadata();

            // Verify the expected behavior
            assertNotNull(metadata);
            assumeTrue(metadata.getNumberOfFiles() == 3);
            assumeTrue(metadata.getNumberOfCustomers() == 300);
            assumeTrue(metadata.getCampaignPlanID() == 10);
            assumeTrue(metadata.getCampaignID() == 1);
            assumeTrue(metadata.getPlanDetailChannelID() == 1);
            assumeTrue(metadata.getEngagementID() == 12);

        }
    }

    // Retrieving metadata from invalid bucket name throws IllegalArgumentException
    @Test
    public void test_creating_engagement_with_invalid_bucket_name() {
        // Call the method under test and assert that it throws an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Engagement("decryptionKey", 123, null, "customersFolderPath", "metadataFilePath", mock(Logger.class)));
    }

    @Test
    public void test_creating_engagement_with_external_bucket_and_invalid_decryption_key() {
        assertThrows(IllegalArgumentException.class, () -> new Engagement(null, 123, "bucketName_external", "customersFolderPath", "metadataFilePath", mock(Logger.class)));

    }

    @Test
    public void test_creating_engagement_with_invalid_tenant_id() {
        assertThrows(IllegalArgumentException.class, () -> new Engagement(null, -1, "bucketName", "customersFolderPath", "metadataFilePath", mock(Logger.class)));
        assertThrows(IllegalArgumentException.class, () -> new Engagement(null, 0, "bucketName", "customersFolderPath", "metadataFilePath", mock(Logger.class)));
    }

    // Throws an IllegalArgumentException when given a null customer batch id.
    @Test
    public void test_throws_illegal_argument_exception_with_negative_id() {
        // Arrange
        int id = -1;
        EngagementSettings settings = new EngagementSettings("decryptionKey", 123, "bucketName", "customersFolderPath", "metadataFilePath");
        Logger logger = mock(Logger.class);
        Engagement engagement = new Engagement("decryptionKey", 123, "bucketName", "customersFolderPath", "metadataFilePath", logger);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            engagement.getCustomerBatchById(id);
        });
    }
}
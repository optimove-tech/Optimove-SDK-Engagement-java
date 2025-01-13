package optimove.sdk.engagement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
class EngagementConfig {
    @JsonProperty("DecryptionKey")
    private String decryptionKey;

    @JsonProperty("TenantID")
    private int tenantID;

    @JsonProperty("BucketName")
    private String bucketName;

    @JsonProperty("CustomersFolderPath")
    private String customersFolderPath;

    @JsonProperty("MetadataFilePath")
    private String metadataFilePath;

    @JsonProperty("EventTypeID")
    private int eventTypeId;

    @JsonProperty("TimeStamp")
    private String timestamp;

    @JsonProperty("CampaignID")
    private int campaignId;

    @JsonProperty("EngagementID")
    private int engagementId;

    @JsonProperty("ChannelID")
    private int channelId;

    // Getters
    public String getDecryptionKey() { return decryptionKey; }
    public int getTenantID() { return tenantID; }
    public String getBucketName() { return bucketName; }
    public String getCustomersFolderPath() { return customersFolderPath; }
    public String getMetadataFilePath() { return metadataFilePath; }
    public int getEventTypeId() { return eventTypeId; }
    public String getTimestamp() { return timestamp; }
    public int getCampaignId() { return campaignId; }
    public int getEngagementId() { return engagementId; }
    public int getChannelId() { return channelId; }
}

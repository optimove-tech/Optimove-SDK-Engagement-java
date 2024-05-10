package optimove.sdk;

public class EngagementSettings {
    private String decryptionKey;
    private int tenantID;
    private String bucketName;
    private String customersFolderPath;
    private String metadataFilePath;


    public EngagementSettings(String decryptionKey, int tenantID, String bucketName, String customersFolderPath,
                              String metadataFilePath) {
        this.decryptionKey = decryptionKey;
        this.tenantID = tenantID;
        this.bucketName = bucketName;
        this.customersFolderPath = customersFolderPath;
        this.metadataFilePath = metadataFilePath;

        validateSettings();
    }

    public void validateSettings() throws IllegalArgumentException{
        if (this.bucketName == null || this.bucketName.isEmpty()) {
            throw new IllegalArgumentException("bucketName is mandatory");
        }

        if (this.bucketName.contains("external") && this.decryptionKey == null) {
            throw new IllegalArgumentException("Decryption key is mandatory for external bucket");
        }

        if (this.tenantID <= 0) {
            throw new IllegalArgumentException("tenantID is mandatory");
        }

        if (this.customersFolderPath == null || this.customersFolderPath.isEmpty()) {
            throw new IllegalArgumentException("customersFolderPath is mandatory");
        }

        if (this.metadataFilePath == null || this.metadataFilePath.isEmpty()) {
            throw new IllegalArgumentException("metadataFilePath is mandatory");
        }
    }


    public String getDecryptionKey() {
        return decryptionKey;
    }

    public void setDecryptionKey(String decryptionKey) {
        this.decryptionKey = decryptionKey;
    }

    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getCustomersFolderPath() {
        return customersFolderPath;
    }

    public void setCustomersFolderPath(String customersFolderPath) {
        this.customersFolderPath = customersFolderPath;
    }

    public String getMetadataFilePath() {
        return metadataFilePath;
    }

    public void setMetadataFilePath(String metadataFilePath) {
        this.metadataFilePath = metadataFilePath;
    }
}


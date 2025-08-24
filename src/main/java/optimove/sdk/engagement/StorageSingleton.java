package optimove.sdk.engagement;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.BlobId;
import com.google.cloud.ReadChannel;



import java.nio.charset.StandardCharsets;

public class StorageSingleton {

    private static Storage instance = null;

    private StorageSingleton() { }

    public static Storage getInstance() {
        if(instance == null) {
            instance = StorageOptions.newBuilder()
                    .build()
                    .getService();
        }
        return instance;
    }
    public static Blob getBlob(String bucketName, String srcFileName) {
        return getInstance().get(BlobId.of(bucketName, srcFileName));
    }
    public static Blob getBlob(String bucketName, String srcFileName, String decryptionKey) {
        return getInstance().get(BlobId.of(bucketName, srcFileName), Storage.BlobGetOption.decryptionKey(decryptionKey));
    }
    public static ReadChannel getReadChanel(String bucketName, String srcFileName) {
        return getInstance().reader(BlobId.of(bucketName, srcFileName));
    }
    public static ReadChannel getReadChanel(String bucketName, String srcFileName, String decryptionKey) {
        if (decryptionKey != null) {
            return getInstance().reader(BlobId.of(bucketName, srcFileName), Storage.BlobSourceOption.decryptionKey(decryptionKey));
        } else {
            return  getReadChanel(bucketName, srcFileName);
        }
    }

    public static String blobToString(Blob blob, String decryptionKey) {
        if (decryptionKey != null) {
            return new String(blob.getContent(Blob.BlobSourceOption.decryptionKey(decryptionKey)), StandardCharsets.UTF_8);
        } else {
            return blobToString(blob);
        }
    }
    public static String blobToString(Blob blob) {
        return new String(blob.getContent(), StandardCharsets.UTF_8);
    }
}

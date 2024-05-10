package optimove.sdk;
import java.util.Date;

public class Metadata {
    private long ActionID;
    private String ActionName;
    private long CampaignID;
    private long CampaignPlanID;
    private long ChannelID;
    private String ChannelName;
    private long EngagementID;
    private long NumberOfCustomers;
    private long NumberOfFiles;
    private long PlanDetailChannelID;
    private String Promotions;
    private Date ScheduledTime;
    private String TargetGroupName;
    private long TemplateID;
    private String TemplateName;
    private long TenantID;
    private String BucketName;
    private String CustomersFolderPath;
    private String MetadataFilePath;
    private String Duration;
    private String InternalAccountID;
    private String AccountName;
    private String Identifier;
    private String Tags;

    public Metadata(long actionID, String actionName, long campaignID, long campaignPlanID, long channelID, String channelName, long engagementID, long numberOfCustomers, long numberOfFiles, long planDetailChannelID, String promotions, Date scheduledTime, String targetGroupName, long templateID, String templateName, long tenantID, String bucketName, String customersFolderPath, String metadataFilePath, String duration, String internalAccountID, String accountName, String identifier, String tags) {
        this.ActionID = actionID;
        this.ActionName = actionName;
        this.CampaignID = campaignID;
        this.CampaignPlanID = campaignPlanID;
        this.ChannelID = channelID;
        this.ChannelName = channelName;
        this.EngagementID = engagementID;
        this.NumberOfCustomers = numberOfCustomers;
        this.NumberOfFiles = numberOfFiles;
        this.PlanDetailChannelID = planDetailChannelID;
        this.Promotions = promotions;
        this.ScheduledTime = scheduledTime;
        this.TargetGroupName = targetGroupName;
        this.TemplateID = templateID;
        this.TemplateName = templateName;
        this.TenantID = tenantID;
        this.BucketName = bucketName;
        this.CustomersFolderPath = customersFolderPath;
        this.MetadataFilePath = metadataFilePath;
        this.Duration = duration;
        this.InternalAccountID = internalAccountID;
        this.AccountName = accountName;
        this.Identifier = identifier;
        this.Tags = tags;
    }

    public Metadata() {}

    public long getActionID() {
        return ActionID;
    }

    public String getActionName() {
        return ActionName;
    }

    public long getCampaignID() {
        return CampaignID;
    }

    public long getCampaignPlanID() {
        return CampaignPlanID;
    }

    public long getChannelID() {
        return ChannelID;
    }

    public String getChannelName() {
        return ChannelName;
    }

    public long getEngagementID() {
        return EngagementID;
    }

    public long getNumberOfCustomers() {
        return NumberOfCustomers;
    }

    public long getNumberOfFiles() {
        return NumberOfFiles;
    }

    public long getPlanDetailChannelID() {
        return PlanDetailChannelID;
    }

    public String getPromotions() {
        return Promotions;
    }

    public Date getScheduledTime() {
        return ScheduledTime;
    }

    public String getTargetGroupName() {
        return TargetGroupName;
    }

    public long getTemplateID() {
        return TemplateID;
    }

    public String getTemplateName() {
        return TemplateName;
    }

    public long getTenantID() {
        return TenantID;
    }

    public String getBucketName() {
        return BucketName;
    }

    public String getCustomersFolderPath() {
        return CustomersFolderPath;
    }

    public String getMetadataFilePath() {
        return MetadataFilePath;
    }

    public String getDuration() {
        return Duration;
    }

    public String getInternalAccountID() {
        return InternalAccountID;
    }

    public String getAccountName() {
        return AccountName;
    }

    public String getIdentifier() {
        return Identifier;
    }

    public String getTags() {
        return Tags;
    }
}

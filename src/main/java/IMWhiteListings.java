import java.util.HashSet;
import java.util.Set;

/**
 * Created by mansi.awasthi on 29/01/16.
 */
public class IMWhiteListings {
    private Set<String> listingIds = new HashSet<>();
    private boolean enablePercentMode = false;
    private Integer numberOfBuckets = 100000;
    private Integer whitelistUptoBucketNumber = -1;

    public Set<String> getListingIds() {
        return listingIds;
    }

    public void setListingIds(Set<String> listingIds) {
        this.listingIds = listingIds;
    }

    public boolean isEnablePercentMode() {
        return enablePercentMode;
    }

    public void setEnablePercentMode(boolean enablePercentMode) {
        this.enablePercentMode = enablePercentMode;
    }

    public Integer getNumberOfBuckets() {
        return numberOfBuckets;
    }

    public void setNumberOfBuckets(Integer numberOfBuckets) {
        this.numberOfBuckets = numberOfBuckets;
    }

    public Integer getWhitelistUptoBucketNumber() {
        return whitelistUptoBucketNumber;
    }

    public void setWhitelistUptoBucketNumber(Integer whitelistUptoBucketNumber) {
        this.whitelistUptoBucketNumber = whitelistUptoBucketNumber;
    }
}

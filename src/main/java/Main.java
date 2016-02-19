import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.io.*;

/**
 * Created by shashi.sharma on 18/02/16.
 */
public class Main {
    public static void main(String[] args) {
        try {
            BucketDivision bucketDivision = new BucketDivision();
            if (args[0].equals("--firstXbuckets")) {
                bucketDivision.listingClassification(Integer.parseInt(args[1]), args[2], args[3]);
            } else if (args[0].equals("--fromFile")) {
                bucketDivision.checkListings(args[1]);
            } else {
                bucketDivision.checkListings(args);
            }
        } catch (Exception e) {
            System.out.println("Usage:");
            System.out.println("java -jar Main.jar {\n\t\t\tspace_seprated_listing_id   |\n\t\t\t --fromFile <fileName>   |\n\t\t\t --firstNBuckets bucketNumber <input_file> <output_file>\n}");
            e.printStackTrace();
        }
    }


}

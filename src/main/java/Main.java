/**
 * Created by shashi.sharma on 18/02/16.
 */
public class Main {
    public static void main(String[] args){
        BucketDivision bucketDivision = new BucketDivision();
        int bucket = bucketDivision.isListingFallsInAllowedBuckets("LSTACCE8E8MYGJFU5JXEFVWCE");
        System.out.println(bucket);
    }
}

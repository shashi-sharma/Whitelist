import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import java.io.*;

/**
 * Created by shashi.sharma on 18/02/16.
 */
public class BucketDivision {
    //temporary test case....
    public int isListingFallsInAllowedBuckets(String listingId){
        HashCode hc = Hashing.md5().newHasher()
                .putString(listingId, Charsets.UTF_8)
                .hash();
        Integer bucketNumber = Math.abs(hc.asInt())%100000;
        return bucketNumber;
    }


    public void testListingClassification() throws Exception{
        FileInputStream in = new FileInputStream("/Users/shashi.sharma/code/pe/top_listings.csv");
        FileOutputStream out = new FileOutputStream(new File("/Users/shashi.sharma/code/pe/output.csv"));
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        System.out.println(bufferedReader.readLine());
        String str;
        while((str = bufferedReader.readLine()) != null){
            str = str.substring(1, str.length()-1);
            int b = isListingFallsInAllowedBuckets(str);
            if((b <= 9)) {
                bufferedWriter.write(str + " - " + b);
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.close();
    }
}

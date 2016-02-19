
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.*;


/**
 * Created by shashi.sharma on 18/02/16.
 */
public class BucketDivision {
    private final String GET_WHITELIST_URI = "http://10.34.37.186:30303/config/im_white_listings";

    private int listingBucket(String listingId, int size){
        HashCode hc = Hashing.md5().newHasher()
                .putString(listingId, Charsets.UTF_8)
                .hash();
        Integer bucketNumber = Math.abs(hc.asInt())%size;
        return bucketNumber;
    }

    private boolean isListingFallsInAllowedBuckets(String listingId, int size, int bucket){
        HashCode hc = Hashing.md5().newHasher()
                .putString(listingId, Charsets.UTF_8)
                .hash();
        Integer bucketNumber = Math.abs(hc.asInt())%size;
        return bucketNumber <= bucket;
    }

    public void listingClassification(int bucket, String input, String output) throws Exception {
        IMWhiteListings config = getConfig();
        FileInputStream in = new FileInputStream(input);
        File f = new File(output);
        FileWriter fileWriter = new FileWriter(f.getAbsolutePath(), true);
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            str = str.substring(1, str.length() - 1);
            int b = listingBucket(str, config.getNumberOfBuckets());
            if ((b <= bucket)) {
                bufferedWriter.write(str + " - " + b);
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.close();
    }


//    public void testListingClassification() throws Exception{
//        FileInputStream in = new FileInputStream("/Users/shashi.sharma/code/pe/top_listings.csv");
//        FileOutputStream out = new FileOutputStream(new File("/Users/shashi.sharma/code/pe/output.csv"));
//        InputStreamReader inputStreamReader = new InputStreamReader(in);
//        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
//        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
//        System.out.println(bufferedReader.readLine());
//        String str;
//        while((str = bufferedReader.readLine()) != null){
//            str = str.substring(1, str.length()-1);
//            int b = isListingFallsInAllowedBuckets(str);
//            if((b <= 9)) {
//                bufferedWriter.write(str + " - " + b);
//                bufferedWriter.newLine();
//            }
//        }
//        bufferedWriter.close();
//    }

    private IMWhiteListings getConfig(){
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(GET_WHITELIST_URI);
        request.setHeader("accept", "application/json");
        request.setHeader("X-Flipkart-Client", "test");
        IMWhiteListings imWhiteListings = null;
        try {
            HttpResponse response = client.execute(request);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String json = reader.readLine();
            imWhiteListings = new ObjectMapper().readValue(json, IMWhiteListings.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imWhiteListings;
    }

    public void checkListings(String input) throws Exception {
        IMWhiteListings config = getConfig();
        FileInputStream in = new FileInputStream(input);
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str;
        while((str = bufferedReader.readLine()) != null){
            boolean b = isListingFallsInAllowedBuckets(str, config.getNumberOfBuckets(), config.getWhitelistUptoBucketNumber())
                    ||  config.getListingIds().contains(str);
            System.out.println(str + " data is fetched from " + (b ? "IM." : "Redis."));
        }
    }

    public void checkListings(String[] input) throws Exception {
        IMWhiteListings config = getConfig();
        for(String str : input){
            boolean b = isListingFallsInAllowedBuckets(str, config.getNumberOfBuckets(), config.getWhitelistUptoBucketNumber())
                    ||  config.getListingIds().contains(str);
            System.out.println(str + " data is fetched from " + (b ? "IM." : "Redis."));
        }
    }

    //    public int isListingFallsInAllowedBuckets(String listingId){
//        HashCode hc = Hashing.md5().newHasher()
//                .putString(listingId, Charsets.UTF_8)
//                .hash();
//        Integer bucketNumber = Math.abs(hc.asInt())%100000;
//        return bucketNumber;
//    }


}

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created by zaorish on 02/11/14.
 */
public class Week3Homework1 {

    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));

        DB database = client.getDB("school");
        final DBCollection collection = database.getCollection("students");

        DBCursor cursor = collection.find();
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                BasicDBList scores = (BasicDBList) cur.get("scores");

                System.out.println(cur.get("_id"));

                double min = 1000;
                int index = -100;
                for (Object object : scores) {
                    if (((DBObject) object).get("type").equals("homework")) {
                        Object score = ((DBObject) object).get("score");
                        double fScore = (Double) score;

                        if (min > fScore) {
                            min = fScore;
                            index = scores.indexOf(object);
                        }
                    }
                }
                scores.remove(index);
                collection.save(cur);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created by zaorish on 26/10/14.
 */
public class Week2Homework2 {

    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));

        DB database = client.getDB("students");
        final DBCollection collection = database.getCollection("grades");

        long total = collection.count();
        System.out.println("initial size: " + total);

        DBCursor cursor = collection.find(new BasicDBObject("type", "homework")).sort(new BasicDBObject("student_id", 1).append("score", 1));

        int currentStudent = Integer.MIN_VALUE;
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                if (currentStudent != (Integer) cur.get("student_id")) {
                    currentStudent = (Integer) cur.get("student_id");
                    // System.out.println(currentStudent);
                    collection.remove(cur);
                }
                // System.out.println(cur);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        total = collection.count();
        System.out.println("final size: " + total);
    }

}
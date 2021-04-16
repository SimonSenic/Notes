package sk.kosickaacademic.simon.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseMongo {
    MongoClient mc = new MongoClient("localhost", 27017);
    MongoDatabase db = mc.getDatabase("myfirstdb");

    public boolean insertNewNote(String title, String task, int priority, int price){
        if(title==null || title.equals("") || task==null || task.equals("") || priority<1 || priority>3 || price<=0)
            return false;
        Document doc = new Document();
        doc.append("date", new SimpleDateFormat("dd:MM:yyyy").format(new Date()));
        doc.append("title", title);
        doc.append("task", task);
        doc.append("priority", priority);
        doc.append("price", price);
        doc.append("done", false);
        db.getCollection("notes").insertOne(doc);
        return true;
    }

    public boolean updateNote(){

        return true;
    }

    //get all notes, get undone notes, get notes by priority, delete completed tasks
}

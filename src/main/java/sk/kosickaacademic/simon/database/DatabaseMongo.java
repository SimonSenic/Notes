package sk.kosickaacademic.simon.database;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sk.kosickaacademic.simon.entity.Notes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseMongo {
    MongoClient mc = new MongoClient("localhost", 27017);
    MongoDatabase db = mc.getDatabase("myfirstdb");

    public boolean insertNewNote(String title, String task, int priority, double price){
        if(title==null || title.equals("") || task==null || task.equals("") || priority<1 || priority>3 || price<0)
            return false;
        Document doc = new Document();
        doc.append("date", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        doc.append("title", title);
        doc.append("task", task);
        doc.append("priority", priority);
        doc.append("price", price);
        doc.append("done", false);
        db.getCollection("notes").insertOne(doc);
        return true;
    }

    public boolean updateNote(){ //
        BasicDBObject query = new BasicDBObject();
        query.put("done", false);
        BasicDBObject doc = new BasicDBObject();
        doc.put("done", true);
        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", doc);
        db.getCollection("notes").updateOne(query, updateObj);
        return true;
    }

    public ArrayList<Notes> getAllNotes(){
        ArrayList<Notes> list = new ArrayList<>();
        for(Document doc : db.getCollection("notes").find()){
            try {
                JSONObject object = (JSONObject) new JSONParser().parse(doc.toJson());
                String date = (String) object.get("date");
                String title = (String) object.get("title");
                String task = (String) object.get("task");
                int priority = Integer.parseInt(String.valueOf(object.get("priority")));
                double price = (double) object.get("price");
                boolean done = (boolean) object.get("done");
                list.add(new Notes(date, title, task, priority, price, done));
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<Notes> getDoneNotes(){
        ArrayList<Notes> list = new ArrayList<>();
        for(Document doc : db.getCollection("notes").find()){
            try {
                JSONObject object = (JSONObject) new JSONParser().parse(doc.toJson());
                String date = (String) object.get("date");
                String title = (String) object.get("title");
                String task = (String) object.get("task");
                int priority = Integer.parseInt(String.valueOf(object.get("priority")));
                double price = (double) object.get("price");
                boolean done = (boolean) object.get("done");
                if(done==true) list.add(new Notes(date, title, task, priority, price, done));
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<Notes> getUndoneNotes(){
        ArrayList<Notes> list = new ArrayList<>();
        for(Document doc : db.getCollection("notes").find()){
            try {
                JSONObject object = (JSONObject) new JSONParser().parse(doc.toJson());
                String date = (String) object.get("date");
                String title = (String) object.get("title");
                String task = (String) object.get("task");
                int priority = Integer.parseInt(String.valueOf(object.get("priority")));
                double price = (double) object.get("price");
                boolean done = (boolean) object.get("done");
                if(done==false) list.add(new Notes(date, title, task, priority, price, done));
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<Notes> getNotesByPriority(int p){
        if(p<=0 || p>3) return null;
        ArrayList<Notes> list = new ArrayList<>();
        for(Document doc : db.getCollection("notes").find()){
            try {
                JSONObject object = (JSONObject) new JSONParser().parse(doc.toJson());
                String date = (String) object.get("date");
                String title = (String) object.get("title");
                String task = (String) object.get("task");
                int priority = Integer.parseInt(String.valueOf(object.get("priority")));
                double price = (double) object.get("price");
                boolean done = (boolean) object.get("done");
                if(p==priority) list.add(new Notes(date, title, task, priority, price, done));
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<Notes> getNotesByTitle(String t){
        if(t==null || t.equals("")) return null;
        ArrayList<Notes> list = new ArrayList<>();
        for(Document doc : db.getCollection("notes").find()){
            try {
                JSONObject object = (JSONObject) new JSONParser().parse(doc.toJson());
                String date = (String) object.get("date");
                String title = (String) object.get("title");
                String task = (String) object.get("task");
                int priority = Integer.parseInt(String.valueOf(object.get("priority")));
                double price = (double) object.get("price");
                boolean done = (boolean) object.get("done");
                if(t.equalsIgnoreCase(title)) list.add(new Notes(date, title, task, priority, price, done));
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
        return list;
    }

    public boolean insertNoteJSON(JSONObject note){
        if(note.isEmpty() || note==null) return false;
        Document doc = new Document();
        doc.append("date", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        doc.append("title", note.get("title"));
        doc.append("task", note.get("task"));
        doc.append("priority", note.get("priority"));
        doc.append("price", note.get("price"));
        doc.append("done", false);
        return true;
    }

    public JSONObject getNotesJSON(){
        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();
        for(Document doc : db.getCollection("notes").find()){
            try {
                JSONObject object = (JSONObject) new JSONParser().parse(doc.toJson());
                array.add(object);
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
        json.put("notes", array);
        return json;
    }

    //get all notes, get undone notes, get notes by priority, delete completed tasks
}

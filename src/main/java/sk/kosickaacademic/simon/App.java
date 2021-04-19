package sk.kosickaacademic.simon;

import sk.kosickaacademic.simon.database.DatabaseMongo;
import sk.kosickaacademic.simon.entity.Notes;

import java.util.ArrayList;

public class App
{
    public static void main( String[] args )
    {
        //System.out.println(new DatabaseMongo().insertNewNote("Comp", "Buy a new computer", 3, 1500));
        //System.out.println(new DatabaseMongo().updateNote());
        ArrayList<Notes> list = new DatabaseMongo().getNotesByPriority(2);
        for(Notes temp : list) System.out.println(temp.toString());
    }
}

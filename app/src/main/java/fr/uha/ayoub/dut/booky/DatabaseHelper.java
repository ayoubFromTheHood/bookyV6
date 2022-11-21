package fr.uha.ayoub.dut.booky;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "books.db";
    public static final String TABLENAME = "book";

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLENAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT " +
                ",ISBN TEXT, TITLE TEXT , AUTHOR TEXT , RELEASEDATE TEXT, DESCRIPTION TEXT) ";
        db.execSQL(createTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }


    public boolean insertData(String isbn , String title , String author , String releasedate , String description){
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues contentValues = new ContentValues() ;
        contentValues.put("ISBN", isbn) ;
        contentValues.put("TITLE", title) ;
        contentValues.put("AUTHOR" ,author);
        contentValues.put("RELEASEDATE" , releasedate);
        contentValues.put("DESCRIPTION" , description);
        long result = db.insert( TABLENAME, null , contentValues) ;
        if (result == -1) return false ;
        else
            return true ;
    }


    public ArrayList<Book> getAllData1()
    {


        ArrayList<Book> arrayList = new ArrayList<Book>() ;
        SQLiteDatabase db = this.getReadableDatabase() ;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLENAME , null) ;
        while (cursor.moveToNext())
        {
            String isbn = cursor.getString(1) ;
            String title = cursor.getString(2) ;
            String author = cursor.getString(3) ;
            String releasedate = cursor.getString(4) ;
            String description = cursor.getString(5) ;
            Book book = new Book(isbn,title,author,releasedate,description) ;
            arrayList.add(book) ;


        }
        return arrayList ;
    }



}
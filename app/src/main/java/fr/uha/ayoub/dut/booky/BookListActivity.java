package fr.uha.ayoub.dut.booky;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import static fr.uha.ayoub.dut.booky.MainActivity.ACTS;


public class BookListActivity extends AppCompatActivity {
    private VideoView back ;
    int backposition ;
    Button scan ;
    DatabaseHelper databaseHelper ;
    ArrayList<Book> arrayList ;
    BookAdapter adapter ;
    public static TextView titleshow ;
    ListView lv ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        scan = findViewById(R.id.Scan) ;
        scan.getBackground().setAlpha(50);
        back  = findViewById(R.id.vid) ;
        Uri uri = Uri.parse("android.resource://" + getPackageName()
                +"/"+R.raw.backy);

        back.setVideoURI(uri);
        back.start();

        back.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                if (backposition !=0 ){
                    mp.seekTo(backposition);
                    mp.start();
                }
            }
        });
        titleshow = findViewById(R.id.title) ;
        lv = findViewById(R.id.list);
       lv.getBackground().setAlpha(90);
        databaseHelper = new DatabaseHelper(this) ;
        arrayList = new ArrayList<>() ;
        loadData() ;
    }

    private void loadData() {

        arrayList = databaseHelper.getAllData1() ;
        adapter = new BookAdapter(this,arrayList ) ;
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Book book = adapter.get(position) ;
                String description = book.getDescription() ;
                Toast.makeText(BookListActivity.this, description, Toast.LENGTH_LONG).show();
            }
        });
        adapter.notifyDataSetChanged();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (resultCode == RESULT_OK) {

            String x = " " ;
            String isbn = data.getStringExtra("ISBN");
            Toast.makeText(this, isbn , Toast.LENGTH_SHORT).show();
            FetchBook process = new FetchBook(isbn ,adapter , databaseHelper );
            process.execute();

        } else if (resultCode == RESULT_CANCELED) {


            Toast.makeText(this, "A Problem Accurred ! ", Toast.LENGTH_SHORT).show();

        }
    }





    public void Scan(View view) {

        Intent intent = new Intent(this, ScanningActivity.class);
        startActivityForResult(intent, ACTS);
    }






    @Override
    protected void onResume() {


        super.onResume();
        back.start();
    }
    @Override
    protected void onPause() {
        back.suspend();
        super.onPause();

    }
    @Override
    protected void onDestroy() {
        back.stopPlayback();
        super.onDestroy();


    }

    public boolean chechNetworkconnection(){
        ConnectivityManager connectivityManager= (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
        return      (networkInfo!= null &&  networkInfo.isConnected()) ;
    }


}
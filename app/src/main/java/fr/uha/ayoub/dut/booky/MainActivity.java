package fr.uha.ayoub.dut.booky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private  VideoView back ;
    int  backposition ;


    private static String TAG = MainActivity.class.getSimpleName() ;
    private Button button ;
    public static TextView author ;
    public static TextView title ;

    public static int ACTS = 100;
    public static int ACTL = 101;
    DatabaseHelper databaseHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        back  = findViewById(R.id.vid) ;

        button = findViewById(R.id.button2) ;
        button.getBackground().setAlpha(50);
        ;
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
        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);


        title = findViewById(R.id.title) ;





    }
    @Override
    protected void onResume() {


        super.onResume();
        back.start();
        if (mServ != null) {
            mServ.resumeMusic();
        }

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
        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);

    }
    ;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ACTS) {
            if (resultCode == RESULT_OK) {

                String isbn = data.getStringExtra("ISBN");
                Toast.makeText(this, isbn , Toast.LENGTH_SHORT).show();
               /*
                FetchBook process = new FetchBook(isbn ,  );
                process.execute();
*/


            } else if (resultCode == RESULT_CANCELED) {


                Toast.makeText(this, "A PROBLEM ACCURED !", Toast.LENGTH_SHORT).show();

            }
        }
    }



    public void act2(View view) {
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.v);
mediaPlayer.start();
        Intent intent = new Intent(this, BookListActivity.class);
        startActivityForResult(intent, ACTL);



    }

    @Override
    public Context getBaseContext() {
        return super.getBaseContext();
    }

    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    } ;

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon,Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }
}

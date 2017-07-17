package com.android.internal;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.widget.ImageView; import android.widget.TextView;
import android.app.Activity; import android.graphics.Bitmap; import android.graphics.BitmapFactory; import android.graphics.Color; import android.media.MediaMetadataRetriever; import android.os.Bundle; import android.widget.ImageView; import android.widget.TextView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.concurrent.ThreadFactory;

import ltd.pvt.tagore_6.mymusic.MainActivity;
import ltd.pvt.tagore_6.mymusic.R;
import ltd.pvt.tagore_6.mymusic.ShakeListener;

public class Player extends AppCompatActivity implements View.OnClickListener {
    ImageView album_art; TextView album, artist, genre; MediaMetadataRetriever metaRetriver; byte[] art;

   // Read more: http://mrbool.com/how-to-extract-meta-data-from-media-file-in-android/28130#ixzz4LdD6Vyeo
    private ShakeListener mShaker;
    static MediaPlayer mp;
    ArrayList<File> mySongs;
    int position;
    Thread updateSeekBar;
    Uri u;
    SeekBar sb;
    private GestureDetectorCompat gestureDetector;

    Button btPlay,btFF,btNxt,btFB,btPv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        btPlay=(Button)findViewById(R.id.btPlay);
        btFF=(Button)findViewById(R.id.btFF);
        btFB=(Button)findViewById(R.id.btFB);
        btNxt=(Button)findViewById(R.id.btNxt);
        btPv=(Button)findViewById(R.id.btPv);
        sb=(SeekBar)findViewById(R.id.seekBar);
        album_art = (ImageView) findViewById(R.id.album_art);
        album = (TextView) findViewById(R.id.Album);
        artist = (TextView) findViewById(R.id.artist_name);
        genre = (TextView) findViewById(R.id.genre);
        metaRetriver = new MediaMetadataRetriever();

        //metaRetriver = new MediaMetadataRetriever();
        //metaRetriver.setDataSource();

       // u=Uri.parse(mySongs.get(position).toString());

     //  changeMet();
      /*  metaRetriver = new MediaMetadataRetriever();
        metaRetriver.setDataSource(u.getPath().toString());
        try { art = metaRetriver.getEmbeddedPicture();
            Bitmap songImage = BitmapFactory .decodeByteArray(art, 0, art.length);
            album_art.setImageBitmap(songImage);
            album.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
            artist.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
            genre.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE)); }
        catch (Exception e) {
            album_art.setBackgroundColor(Color.GRAY);
            album.setText("Unknown Album");
            artist.setText("Unknown Artist");
            genre.setText("Unknown Genre");
        }*/

      //  Read more: http://mrbool.com/how-to-extract-meta-data-from-media-file-in-android/28130#ixzz4LdE3bLRW
       /* this.gestureDetector=new GestureDetectorCompat(this, (GestureDetector.OnGestureListener) this);
        gestureDetector.setOnDoubleTapListener((GestureDetector.OnDoubleTapListener) this);
    */
        btPlay.setOnClickListener(this);
        btFF.setOnClickListener(this);
        btFB.setOnClickListener(this);
        btNxt.setOnClickListener(this);
        btPv.setOnClickListener(this);
        updateSeekBar =new Thread(){
            @Override
            public void run() {
                int totalDuration=mp.getDuration();
                int currentPosition=0;

                while(!(currentPosition>totalDuration)){
                    try {
                        sleep(500);
                        currentPosition= mp.getCurrentPosition();
                        sb.setProgress(currentPosition);

                      if(currentPosition>totalDuration)
                         next();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                //super.run();
            }
        };
        /*if(currentPosition==mp.getDuration()){
            mp.stop();
            mp.release();
            position=((position+1))%(mySongs.size());
            u=Uri.parse(mySongs.get((position)).toString());
            mp=MediaPlayer.create(getApplicationContext(),u);

            mp.start();
            sb.setProgress(0);
            sb.setMax(mp.getDuration());
        }*/
        if(mp!=null){
            mp.stop();
            mp.release();
        }
        Intent i=getIntent();
        Bundle b=i.getExtras();
        mySongs=(ArrayList) b.getParcelableArrayList("songlist");
        position =b.getInt("pos",0);
        u=Uri.parse(mySongs.get(position).toString());

        mp=MediaPlayer.create(getApplicationContext(),u);
        mp.start();
        sb.setMax(mp.getDuration());
        updateSeekBar.start();
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener () {
            public void onShake()
            {
               // Toast.makeText(Player.this, "Shake " , Toast.LENGTH_LONG).show();
                //next();
                handleShakeEvent();

            }
        });
        changeMet();
    }
public void handleShakeEvent(){
    next();
}

    @Override
    protected void onResume() {
        super.onResume();
        mShaker.resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mShaker.pause();
        //   super.onPause();

    }




 /*   public boolean onSingleTapConfirmed(MotionEvent e){
        mp.stop();
        mp.release();
        position=((position+1))%(mySongs.size());
        u=Uri.parse(mySongs.get((position)).toString());
        mp=MediaPlayer.create(getApplicationContext(),u);

        mp.start();
        sb.setProgress(0);
        sb.setMax(mp.getDuration());
        return true;
    }

    public boolean onDoubleTap(MotionEvent e){
        mp.stop();
        mp.release();
        position=((position-1)<0?mySongs.size()-1:position-1);
        u=Uri.parse(mySongs.get((position)).toString());
        mp=MediaPlayer.create(getApplicationContext(),u);

        mp.start();
        sb.setProgress(0);
        sb.setMax(mp.getDuration());
        return true;

    }
    public boolean onTouchEvent(MotionEvent e){
        this.gestureDetector.onTouchEvent(e);
        return super.onTouchEvent(e);
    }*/

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btPlay:
                if(mp.isPlaying()) {
                    btPlay.setText(">");
                    mp.pause();
                }
                else {
                    btPlay.setText("||");
                    mp.start();
                }
                break;
            case R.id.btFF:
                mp.seekTo(mp.getCurrentPosition()+5000);
                break;
            case R.id.btFB:
                mp.seekTo(mp.getCurrentPosition()-5000);
                break;

            case R.id.btNxt:
                mp.stop();
                mp.release();
                position=((position+1))%(mySongs.size());
               u=Uri.parse(mySongs.get((position)).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
               changeMet();
                mp.start();
                sb.setProgress(0);
                sb.setMax(mp.getDuration());

                break;
            case R.id.btPv:
                mp.stop();
                mp.release();
                position=((position-1)<0?mySongs.size()-1:position-1);
                u=Uri.parse(mySongs.get((position)).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                changeMet();
                mp.start();
                sb.setProgress(0);
                sb.setMax(mp.getDuration());
                break;
        }
    }
    public void next(){
        mp.stop();
        mp.release();
        position=((position+1))%(mySongs.size());
        u=Uri.parse(mySongs.get((position)).toString());
        mp=MediaPlayer.create(getApplicationContext(),u);
        changeMet();
        mp.start();
        sb.setProgress(0);
        sb.setMax(mp.getDuration());
    }
    public void changeMet(){

        metaRetriver.setDataSource(u.getPath().toString());
        try { art = metaRetriver.getEmbeddedPicture();
            Bitmap songImage = BitmapFactory .decodeByteArray(art, 0, art.length);
            album_art.setImageBitmap(songImage);
            album.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
            artist.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
            genre.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE)); }
        catch (Exception e) {
            album_art.setBackgroundColor(Color.TRANSPARENT);
            album.setText("Unknown Album");
            artist.setText("Unknown Artist");
            genre.setText("Unknown Genre");
        }


    }
}

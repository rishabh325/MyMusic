package ltd.pvt.tagore_6.mymusic;

import android.content.Intent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.internal.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

public class MainActivity extends AppCompatActivity  {
    ListView lv;
    String[] items;
   // SensorManager sensorMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=(ListView)findViewById(R.id.lvPlaylist);
        String str=getIntent().getExtras().getString("name","User");

        final ArrayList<File> mySongs=findSongs(Environment.getExternalStorageDirectory());
        items=new String[mySongs.size()];
     //   msensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);

        for(int i=0;i<mySongs.size();i++){
           // toast(mySongs.get(i).getName().toString());
            items[i]=mySongs.get(i).getName().toString().replace(".mp3","").replace(".wav","");
        }
        ArrayAdapter<String> adp=new ArrayAdapter<String>(getApplicationContext(),R.layout.song_layout,R.id.textView,items);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long ld) {
                startActivity(new Intent(getApplicationContext(),Player.class).putExtra("pos",position).putExtra("songlist",mySongs));
            }
        });
        Toast.makeText(MainActivity.this,"Welcome "+str,Toast.LENGTH_LONG).show();
    }
    public ArrayList<File> findSongs(File root){
File[] files=root.listFiles();
        ArrayList<File> al=new ArrayList<File>();
     for(File singleFile : files ){
         if(singleFile.isDirectory() && !singleFile.isHidden()){
           al.addAll(findSongs(singleFile));

         }
       else{
             if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")){
           al.add(singleFile);

             }
         }
     }
 return al;
    }


}

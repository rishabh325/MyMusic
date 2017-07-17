package ltd.pvt.tagore_6.mymusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    Button btnSubmit;
    EditText passwd;
    EditText name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnSubmit=(Button) findViewById(R.id.btnSubmit);
        passwd=(EditText) findViewById(R.id.passwd);
        name=(EditText) findViewById(R.id.name);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = passwd.getText().toString();
                String p=name.getText().toString();
                Intent intent=new Intent(login.this,MainActivity.class);
                if(str.toUpperCase().equals("ZEPHYR")){
                    intent.putExtra("name",p);
                    startActivity(intent);
                }
                else Toast.makeText(login.this,"Wrong Password!!!Enter 'zephyr' ",Toast.LENGTH_SHORT).show();

            }
        });

    }

}

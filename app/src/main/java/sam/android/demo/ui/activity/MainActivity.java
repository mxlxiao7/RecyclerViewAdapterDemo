package sam.android.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import sam.android.demo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


    public void simpleDemo(View view)
    {
        startActivity(new Intent(this, SimpleActivity.class));
    }



    public void more(View view)
    {
        startActivity(new Intent(this,MutilLayoutActivity.class));
    }


}

package com.example.lab2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Map<String, String> descriptions;
    String description;

    public static final String str="example string";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("lifecycle:","create");
        descriptions=new HashMap<>();
        descriptions.put("Coca Cola","Coca Cola Classic, price 3.5");
        descriptions.put("Coca Cola Zero","Coca Cola Zero, price 3.7");
        descriptions.put("Fanta","Fanta Orange, price 3");
        descriptions.put("Sprite","Sprite juice, price 3.2");
        descriptions.put("Delivery","Delivery service, variable price");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        final TextView textView=(TextView)findViewById(R.id.textView);
        ListView listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.add("Coca Cola");
        arrayAdapter.add("Coca Cola Zero");
        arrayAdapter.add("Fanta");
        arrayAdapter.add("Sprite");
        arrayAdapter.add("Delivery");
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(descriptions.get(((TextView)view).getText().toString()));
                description=descriptions.get(((TextView)view).getText().toString());
            }
        };
        listView.setOnItemClickListener(listener);
    }

    @Override
    public void onStart(){
        Log.d("lifecycle:","start");
        super.onStart();
    }

    @Override
    public void onResume(){
        Log.d("lifecycle:","resume");
        super.onResume();
    }

    @Override
    public void onPause(){
        Log.d("lifecycle:","pause");
        super.onPause();
    }

    @Override
    public void onStop(){
        Log.d("lifecycle:","stop");
        super.onStop();
    }

    @Override
    public void onDestroy(){
        Log.d("lifecycle:","destroy");
        super.onDestroy();
    }

    @Override
    public void onRestart(){
        Log.d("lifecycle:","restart");
        super.onRestart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("textView", (String) description);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(savedInstanceState.getString("textView"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mail:
                Log.d("menu item:", "mail");
                AlertDialog alert = new AlertDialog.Builder(this).setMessage("example message").create();
                alert.show();
                return true;
            case R.id.share:
                Log.d("menu item:", "share");
                return true;
            case R.id.upload:
                Log.d("menu item:", "upload");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void sendMessage(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        EditText editText = (EditText) findViewById(R.id.textInputEditText);
        String message = editText.getText().toString();
        intent.putExtra(str, message);
        intent.setType("text/plain");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}

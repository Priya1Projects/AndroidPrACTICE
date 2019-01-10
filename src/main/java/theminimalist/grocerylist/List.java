package theminimalist.grocerylist;

import android.animation.TypeConverter;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import android.app.Activity;

public class List extends AppCompatActivity {

    ListView listView;
    EditText editText;
    ArrayList strings;
    ArrayAdapter<String> adapter;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.grocery_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list);
        listView = (ListView) findViewById(R.id.lv);
        editText = (EditText) findViewById(R.id.text);
        strings = new ArrayList<String>();

        sharedPref= this.getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        //Retrieve the values
        final Set<String> storePref = new HashSet<String>();
        try {
            String set = sharedPref.getString("key", null);
            strings.add(set);
        }
        catch(Exception e) {
            Set <String> set = sharedPref.getStringSet("key", null);
            strings.addAll(set);
        }
        adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,strings);
        listView.setAdapter(adapter);

             ((EditText)findViewById(R.id.text)).setOnEditorActionListener(
                new EditText.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (event != null && event.getAction() != KeyEvent.ACTION_DOWN) {
                            return false;
                        } else if (actionId == EditorInfo.IME_ACTION_SEARCH
                                || event == null
                                || event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                            if (event == null || !event.isShiftPressed()) {
                                String text = editText.getText().toString();
                                strings.add(text);
                                // Now add it to the list
                                storePref.addAll(strings);
                                editText.setText("");

                                // And finally, update the list
                                adapter.notifyDataSetChanged();
                                return true;
                            }
                        }
                        return false;
                    }
                }
        );
        editor.putStringSet("key", storePref);
        editor.apply();
       // arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    public void onResume(){
        super.onResume();
        SharedPreferences sharedPref= this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        //Retrieve the values
        try {
            String set = sharedPref.getString("key", null);
            strings.add(set);
        }
        catch(Exception e) {
            Set <String> set = sharedPref.getStringSet("key", null);
            strings.addAll(set);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        System.out.printf("Hello World!!");

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

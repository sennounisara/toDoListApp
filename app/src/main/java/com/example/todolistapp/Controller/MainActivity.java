package com.example.todolistapp.Controller;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.todolistapp.Model.ListItem;
import com.example.todolistapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ItemAdapter adapter;
    private ListView list;
    private ArrayList<ListItem> items = new ArrayList<>();
    private SharedPreferences sharedPreferences;


    @Override
    protected void onStop() {
        Log.e("TAO","ONDESTROY");
        savePref();
        super.onStop();
    }

    private void savePref() {
        Log.e("LIST","isChecked "+items.get(1).isCheked());
        sharedPreferences = getSharedPreferences("toDoList",MODE_PRIVATE);
        sharedPreferences.edit().remove("ItemL").commit();
        JSONArray array = new JSONArray();
        try {
            for(ListItem item : items){
                JSONObject objet = new JSONObject();
                objet.put("task",item.getTask());
                objet.put("checked",item.isCheked());
                array.put(objet);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sharedPreferences.edit().putString("ItemL",array.toString()).commit();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);
        loadList();
    /*
        items.add(new ListItem(false,"oasfljna kjsndfas djknas k"));
        items.add(new ListItem(true,"sorry dfas djknas k"));
        items.add(new ListItem(false,"oasfljna kjsndfas djknas k"));
        items.add(new ListItem(true,"sorry dfas djknas k"));
    */

        adapter = new ItemAdapter(getApplicationContext(),items);

        list.setAdapter(adapter);




        final FloatingActionButton addItem = findViewById(R.id.addItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
    }

    private void loadList() {
        sharedPreferences = getSharedPreferences("toDoList",MODE_PRIVATE);
        String contenu = sharedPreferences.getString("ItemL",null);
        try {
            JSONArray array = new JSONArray(contenu);
            for (int i = 0; i< array.length();i++){
                JSONObject objet = array.getJSONObject(i);
                String task = objet.getString("task");
                boolean checked = objet.getBoolean("checked");
                items.add(new ListItem(checked,task));
            }
            adapter.notifyDataSetChanged();

        }catch (Exception e){

        }

    }


    public void addItem(){
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        final EditText newTask = new EditText(this);

        builer.setTitle("Add Item").setMessage("What is new wan to do next ? ").setView(newTask)
            .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String addItemString = newTask.getText().toString();
                    /*ListItem item = new ListItem(false,addItemString);
                       items.add(item);
                       adapter.notifyDataSetChanged();*/
                    boolean exis = false;
                    for(ListItem itm : items){
                        if(addItemString.equals(itm.getTask())){
                            exis = true;
                            break;
                        }
                    }
                    if(exis){
                        Toast.makeText(getApplicationContext(),"Task is already exist !",Toast.LENGTH_SHORT).show();
                    }else{
                        items.add(new ListItem(false,addItemString));

                        adapter.notifyDataSetChanged();
                    }

                }
                })
                .create().show();
    }

}

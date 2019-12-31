package com.example.todolistapp.Controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolistapp.Model.ListItem;
import com.example.todolistapp.R;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends ArrayAdapter<ListItem> {
    ArrayList<ListItem> items;
    public ItemAdapter(@NonNull Context context, @NonNull List<ListItem> Item) {
        super(context, 0, Item);
        items = (ArrayList) Item;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ListItem  item = (ListItem) getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_main,parent,false);
        }

        final CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        TextView textView = convertView.findViewById(R.id.item);
        ImageView imageView = convertView.findViewById(R.id.delete);

        checkBox.setChecked(item.isCheked());
        textView.setText(item.getTask());

        deleteItem(imageView, item);
        //---- ischeched -----//
        /*checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("Tao","checked nnew V is "+b);
                item.setCheked(b);

                checkBox.setChecked(item.isCheked());
                notifyDataSetChanged();
            }
        });*/
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setCheked(!item.isCheked());
                Log.e("TAO",item.getTask()+" Changed is CHecked TO "+item.isCheked());
            }
        });

        return convertView;

    }

    private void deleteItem(ImageView imageView, final ListItem item){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(item);
                notifyDataSetChanged();
            }
        });
    }
}

package com.example.eat_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = findViewById(R.id.main_list);
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", "row click"+ position);
            }
        });

    }

    class MyAdapter extends BaseAdapter{

        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @Override
        //count the number of rows
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null)
            {
                convertView = inflater.inflate(R.layout.list_row , null);
            }
            TextView titleTv = convertView.findViewById(R.id.row_title_tv);
            TextView locationTv = convertView.findViewById(R.id.row_location_tv);
            TextView descriptionTv = convertView.findViewById(R.id.row_descroption_tv);
            ImageView imageView = convertView.findViewById(R.id.imageView);
            titleTv.setText("name " + position);
            locationTv.setText("location" + position);
            descriptionTv.setText("desc "+ position);
            return convertView;
        }
    }
}
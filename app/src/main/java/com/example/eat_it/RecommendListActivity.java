package com.example.eat_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eat_it.model.Model;
import com.example.eat_it.model.Recommend;

import java.util.List;

public class RecommendListActivity extends AppCompatActivity {

    RecyclerView list;
    List<Recommend> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_list);
        data= Model.instance.getAllRec();

        list = findViewById(R.id.recommend_list);
        list.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);

       MyAdapter adapter = new MyAdapter();
       list.setAdapter(adapter);
    }

    static class RecommendViewHolder extends RecyclerView.ViewHolder{

        TextView titleTv;
        TextView locationTv;
        TextView descriptionTv;
        ImageView imageView;

        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.row_title_tv);
            locationTv = itemView.findViewById(R.id.row_location_tv);
            descriptionTv = itemView.findViewById(R.id.row_descroption_tv);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void bind(Recommend recommend){
            titleTv.setText(recommend.title);
            locationTv.setText(recommend.location);
            descriptionTv.setText(recommend.description);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<RecommendViewHolder>{

        @NonNull
        @Override
        // what happen when create a view of row

        public RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);
            RecommendViewHolder viewHolder = new RecommendViewHolder(view);
            return viewHolder;
        }

        //take a row and connect her data
        @Override
        public void onBindViewHolder(@NonNull RecommendViewHolder holder, int position) {
            Recommend recommend = data.get(position);
            holder.bind(recommend);



        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
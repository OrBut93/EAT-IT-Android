package com.example.eat_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class RecommendListActivity extends AppCompatActivity {

    RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_list);

        list = findViewById(R.id.recommend_list);
        list.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
    }

    static class RecommendViewHolder extends RecyclerView.ViewHolder{

        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<RecommendViewHolder>{



        @NonNull
        @Override
        // what happen when create a view of row
        public RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        //take a row and connect her data
        @Override
        public void onBindViewHolder(@NonNull RecommendViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
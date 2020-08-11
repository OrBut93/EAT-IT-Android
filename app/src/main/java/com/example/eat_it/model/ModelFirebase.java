package com.example.eat_it.model;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.Task;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {

	final static String RECOMMENDS_COLLECTION = "recommends";
	
    FirebaseFirestore db;
    final Map<String, Object> recommend;

    public ModelFirebase(){
        db = FirebaseFirestore.getInstance();
        recommend = new HashMap<>();

        recommend.put("first", "Ada");
        recommend.put("last", "Lovelace");
        recommend.put("born", 1815);
    }
    
    
    public static void getAllRecommends(final Model.Listener<List<Recommend>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(RECOMMENDS_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Recommend> recData = null;
                if (task.isSuccessful()){
                    recData = new LinkedList<Recommend>();
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Recommend recommend = doc.toObject(Recommend.class);
                        recData.add(recommend);
                    }
                }
                listener.onComplete(recData);
            }
        });
    }

    public void addRecommend (final Recommend recommend, Model.Listener<Boolean> listener){

// Add a new document with a generated ID
        db.collection("recommends")
                .add(recommend)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        String postId = documentReference.getId();
                        recommend.setId(postId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }
    
    

    public interface GetAllRecommendsListener{
        void onComplete(List<Recommend> data);
    }

    public void getAllRecommends(GetAllRecommendsListener listener)
    {

    }
}

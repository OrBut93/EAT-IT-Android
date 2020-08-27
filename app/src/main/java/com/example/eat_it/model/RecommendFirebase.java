package com.example.eat_it.model;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.SnapshotMetadata;
import com.google.firestore.v1.Document;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RecommendFirebase {

	final static String RECOMMENDS_COLLECTION = "recommends";
	
//    FirebaseFirestore db;
////    final Map<String, Object> recommend;
////
////    public ModelFirebase(){
////        db = FirebaseFirestore.getInstance();
////        recommend = new HashMap<>();
////
////        recommend.put("first", "Ada");
////        recommend.put("last", "Lovelace");
////        recommend.put("born", 1815);
////    }

//    public static void getAllRecommendsSince(long since, final RecommendModel.Listener<List<Recommend>> listener) {
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Timestamp ts = new Timestamp(since,0);
//
//        db.collection(RECOMMENDS_COLLECTION).whereGreaterThanOrEqualTo("lastUpdated", ts)
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                List<Recommend> recData = null;
//                if (task.isSuccessful()){
//                    recData = new LinkedList<Recommend>();
//                    for(QueryDocumentSnapshot doc : task.getResult()){
//                        Map<String,Object> json = doc.getData();
//                        Recommend recommend = factory(json);
//                        recData.add(recommend);
//                    }
//                }
//                listener.onComplete(recData);
//                Log.d("TAG","refresh " + recData.size());
//            }
//        });
//    }

    public static void getAllRecommendsSince(long since, final RecommendModel.Listener<List<Recommend>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(since, 0);

        db.collection(RECOMMENDS_COLLECTION).whereGreaterThanOrEqualTo("lastUpdated", ts)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d("TAG", "", e);
                            listener.onComplete(null);
                        }

                        List<Recommend> outfits = new LinkedList<>();
                        List<Recommend> outfitsToDelete = new LinkedList<>();

                        for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                            switch (documentChange.getType()) {
                                case ADDED:
                                case MODIFIED:
                                    SnapshotMetadata metadata = documentChange.getDocument().getMetadata();
                                    if (!metadata.hasPendingWrites()) {
                                        Map<String, Object> json = documentChange.getDocument().getData();
                                        Recommend recommend = factory(documentChange.getDocument().getId(), json);
                                        outfits.add(recommend);
                                    }
                                    break;
                                case REMOVED:
                                    Map<String, Object> json = documentChange.getDocument().getData();
                                    Recommend recommend = factory(documentChange.getDocument().getId(), json);
                                    outfitsToDelete.add(recommend);
                                    break;
                            }
                        }

//                        if (!outfitsToDelete.isEmpty()) {
//                            RecommendModel.instance.deleteOutfits(outfitsToDelete);
//                        }

                        listener.onComplete(outfits);
                    }
                });
    }




    public static void getAllRecommends(final RecommendModel.Listener<List<Recommend>> listener) {
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

//    public void addRecommend (final Recommend recommend, Model.Listener<Boolean> listener){
//
//// Add a new document with a generated ID
//        db.collection("recommends")
//                .add(recommend)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
//                        String postId = documentReference.getId();
//                        recommend.setId(postId);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("TAG", "Error adding document", e);
//                    }
//                });
//    }

    public static void addRecommend(final Recommend recommend, final RecommendModel.Listener<Boolean> listener) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(RECOMMENDS_COLLECTION).document().set(toJson(recommend)).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener!=null){
                    listener.onComplete(task.isSuccessful());
                    String myID2 = db.collection(RECOMMENDS_COLLECTION).document(task.toString()).getId();
                }
            }
        });
    }

    private static Recommend factory(String id, Map<String, Object> json) {
        String title = (String) json.get("title");
        String imageUrl = (String) json.get("imageUrl");
        String description = (String) json.get("description");
        String location = (String) json.get("location");
        String ownerId = (String) json.get("ownerId");
        String ownerName = (String) json.get("ownerName");
        Recommend recommend = new Recommend(id, ownerId, ownerName, title, location, description, imageUrl);
        Timestamp ts = (Timestamp) json.get("lastUpdated");
        if (ts != null) recommend.lastUpdated = ts.getSeconds();
        return recommend;
    }

    private static Map<String, Object> toJson(Recommend recommend) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", recommend.title);
        result.put("location", recommend.location);
        result.put("description", recommend.description);
        result.put("imageUrl", recommend.avatar);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        result.put("ownerName", recommend.OwenrName);
        result.put("ownerId", recommend.Ownerid);
        return result;
    }
    

//    public interface GetAllRecommendsListener{
//        void onComplete(List<Recommend> data);
//    }
//
//    public void getAllRecommends(GetAllRecommendsListener listener)
//    {
//
//    }
}

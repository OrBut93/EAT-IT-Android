package com.example.eat_it.model;

public class StoreModel {
	
	public class Listener{
		void onSuccess(String url);
		void onFail();
	}
	
	public void saveImage(Bitmap imageBmp, String name, final Listener listener){
		 
		FirebaseStorage storage = FirebaseStorage.getInstance();
		 
		 StorageReference imagesRef = storage.getReference().child("images").child(name);
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		 byte[] data = baos.toByteArray();
		 UploadTask uploadTask = imagesRef.putBytes(data);
		 uploadTask.addOnFailureListener(new OnFailureListener() {
		 @Override
		 public void onFailure(Exception exception) {
		 listener.fail();
		 }
		 }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
		 @Override
		 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
		 @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
		 listener.complete(downloadUrl.toString());
		 }
		 });
		}
}

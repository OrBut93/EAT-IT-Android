
package com.example.eat_it.model.User;

public class UserModel {
    public static final UserModel instance = new UserModel();

    private UserModel() {
    }

    public interface Listener<T> {
        void onComplete(T data);
    }

    public User getCurrentUser() {
        return UserFirebase.getCurrentUser();
    }

    public boolean isUserLoggedIn() {
        return this.getCurrentUser() != null;
    }

    public void register(User user, String password, Listener<Boolean> listener) {
        UserFirebase.register(user, password, listener);
    }

    public void login(String email, String password, Listener<Boolean> listener) {
        UserFirebase.login(email, password, listener);
    }

    public void logout() {
        UserFirebase.logout();
    }
}
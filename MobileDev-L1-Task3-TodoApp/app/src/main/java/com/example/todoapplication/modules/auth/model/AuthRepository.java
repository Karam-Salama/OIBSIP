package com.example.todoapplication.modules.auth.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthRepository {

    public interface AuthCallback<T> {
        void onSuccess(T result);
        void onFailure(@NonNull Exception e);
    }

    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;

    public AuthRepository() {
        this.auth = FirebaseAuth.getInstance();
        this.firestore = FirebaseFirestore.getInstance();
    }

    private Task<Void> saveUserToFirestore(@NonNull FirebaseUser user,
                                           @Nullable String fullName,
                                           @Nullable String phone) {
        String resolvedName = fullName != null
                ? fullName
                : (user.getDisplayName() != null ? user.getDisplayName() : "");
        String resolvedEmail = user.getEmail() != null ? user.getEmail() : "";

        UserModel userModel = new UserModel(
                user.getUid(),
                resolvedName,
                resolvedEmail

        );

        return firestore.collection("users")
                .document(user.getUid())
                .set(userModel.toMap())
                .continueWithTask(task -> {
                    if (!task.isSuccessful()) {
                        String msg = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        return Tasks.forException(new Exception("Failed to save user data: " + msg));
                    }
                    return Tasks.forResult(null);
                });
    }

    public void registerWithEmail(@NonNull String fullName,
                                  @Nullable String phone,
                                  @NonNull String email,
                                  @NonNull String password,
                                  @NonNull AuthCallback<FirebaseUser> callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = authResult.getUser();
                    if (user == null) {
                        callback.onFailure(new Exception("User creation failed"));
                        return;
                    }

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(fullName)
                            .build();

                    user.updateProfile(profileUpdates)
                            .continueWithTask(task -> {
                                if (!task.isSuccessful() && task.getException() != null) {
                                    throw task.getException();
                                }
                                return saveUserToFirestore(user, fullName, phone);
                            })
                            .continueWithTask(task -> {
                                if (!task.isSuccessful() && task.getException() != null) {
                                    throw task.getException();
                                }
                                return user.sendEmailVerification();
                            })
                            .addOnSuccessListener(unused -> callback.onSuccess(user))
                            .addOnFailureListener(callback::onFailure);
                })
                .addOnFailureListener(callback::onFailure);
    }

    public void loginWithEmail(@NonNull String email,
                               @NonNull String password,
                               @NonNull AuthCallback<FirebaseUser> callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = authResult.getUser();
                    if (user == null) {
                        callback.onFailure(new Exception("Login failed"));
                        return;
                    }
                    if (!user.isEmailVerified()) {
                        auth.signOut();
                        callback.onFailure(new Exception("Email not verified"));
                        return;
                    }
                    callback.onSuccess(user);
                })
                .addOnFailureListener(callback::onFailure);
    }



    public void sendPasswordResetEmail(@NonNull String email,
                                       @NonNull AuthCallback<Void> callback) {
        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(callback::onSuccess)
                .addOnFailureListener(e -> {
                    String errorMessage;
                    if (e instanceof FirebaseAuthInvalidUserException) {
                        String errorCode = ((FirebaseAuthInvalidUserException) e).getErrorCode();
                        if ("ERROR_USER_NOT_FOUND".equals(errorCode)) {
                            errorMessage = "No account found with this email";
                        } else {
                            errorMessage = "Invalid email address";
                        }
                    } else {
                        errorMessage = "Failed to send reset email: " + e.getMessage();
                    }
                    callback.onFailure(new Exception(errorMessage));
                });
    }
}

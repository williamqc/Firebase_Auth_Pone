 package com.example.william.firebase_auth_phone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

 public class MainActivity extends AppCompatActivity {

     private TextView number,code;
     private FirebaseAuth mAuth;
     private FirebaseAuth.AuthStateListener mAuthListener;
     private String mVerificationId;



     @Override
     public void onStart(){
         super.onStart();
         mAuth.addAuthStateListener(mAuthListener);

     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = (TextView) findViewById(R.id.id_Phone);
        code = (TextView) findViewById(R.id.idCodigo);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null ) {

                    Toast.makeText(MainActivity.this, getString(R.string.now_logged_in) + firebaseAuth.getCurrentUser().getProviderId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                    finish();
                }


            }

        };
    }

     public void requestCode(View view) {
         String phoneNumber = number.getText().toString();
         if (TextUtils.isEmpty(phoneNumber))
             return;
         PhoneAuthProvider.getInstance().verifyPhoneNumber(
                 phoneNumber, 60, TimeUnit.SECONDS, MainActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                     @Override
                     public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                         //Called if it is not needed to enter verification code
                         signInWithCredential(phoneAuthCredential);
                     }

                     @Override
                     public void onVerificationFailed(FirebaseException e) {
                         //incorrect phone number, verification code, emulator, etc.
                         Toast.makeText(MainActivity.this, "onVerificationFailed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                     }

                     @Override
                     public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                         //now the code has been sent, save the verificationId we may need it
                         super.onCodeSent(verificationId, forceResendingToken);

                         mVerificationId = verificationId;
                     }

                     @Override
                     public void onCodeAutoRetrievalTimeOut(String verificationId) {
                         //called after timeout if onVerificationCompleted has not been called
                         super.onCodeAutoRetrievalTimeOut(verificationId);
                         Toast.makeText(MainActivity.this, "onCodeAutoRetrievalTimeOut :" + verificationId, Toast.LENGTH_SHORT).show();
                     }
                 }
         );
     }

     private void signInWithCredential(PhoneAuthCredential phoneAuthCredential) {
         mAuth.signInWithCredential(phoneAuthCredential)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             Toast.makeText(MainActivity.this, R.string.signed_success, Toast.LENGTH_SHORT).show();
                         } else {
                             Toast.makeText(MainActivity.this, getString(R.string.sign_credential_fail) + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
     }

     public void signIn(View view) {
         String cod = code.getText().toString();
         if (TextUtils.isEmpty(cod))
         signInWithCredential(PhoneAuthProvider.getCredential(mVerificationId, cod));
         return;
     }
     }














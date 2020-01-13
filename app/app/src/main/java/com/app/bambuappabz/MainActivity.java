package com.app.bambuappabz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;


public class MainActivity extends AppCompatActivity  {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private CardView register;
    private TextInputLayout impCorreo, impPass;
    private TextInputEditText editcorreo, editpass;
    private ImageButton btnlog;
    private boolean cor = false, pass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Inicializar();
        InicializarFirebase();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, Register_Activity.class);
                startActivity(intent);

            }
        });
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Patterns.EMAIL_ADDRESS.matcher(editcorreo.getText().toString()).matches()==false){
                    impCorreo.setError("Invalid e-mail");
                    cor = false;
                }else{
                    cor= true;
                    impCorreo.setError(null);
                }
                if(editpass.getText().length()<8 || editpass.getText().length()>16){
                    impPass.setError("Invalid password");
                    pass= false;
                }else {
                    pass = true;
                    impPass.setError(null);
                }
                if(cor == true && pass == true){
                    LogIn();

                }
            }
        });
    }

    private void InicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void LogIn() {
        firebaseAuth.signInWithEmailAndPassword(editcorreo.getText().toString(),editpass.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent= new Intent(MainActivity.this, News_activity.class);
                    startActivity(intent);
                    FancyToast.makeText(getApplicationContext(), "Welcome",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();

                }else{

                    FancyToast.makeText(getApplicationContext(), "Password or email wrong",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

                }
            }
        });
        ///database
        databaseReference.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void Inicializar (){
        firebaseAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.registerbtn);
        impCorreo = findViewById(R.id.email_text_input);
        impPass = findViewById(R.id.password_text_input);
        editcorreo = findViewById(R.id.email_edit_text);
        editpass = findViewById(R.id.password_edit_text);
        btnlog = findViewById(R.id.loginbtn);
    }


}

package com.app.bambuappabz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Register_Activity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    private Button savebtn;
    private TextInputLayout nameLayout, lastLayout, mailLayout, passLayout1,passLayout2;
    private EditText nameET, lastET, mailET, passET1, passET2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        Inicializar();
        InicializarFirebase();
        passLayout1.setHelperText("Must be 8 to 16 characters");
        passLayout2.setHelperText("Write again the password");
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Validado()){
                    RegistrarUsuario();

                }

            }
        });
    }

    private void RegistrarUsuario() {
        final String nombre = nameET.getText().toString();
        final String apellido = lastET.getText().toString();
        final String mail = mailET.getText().toString();
        final String password = passET1.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(Register_Activity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id = firebaseAuth.getCurrentUser().getUid();
                    Usuarios p = new Usuarios();
                    p.setNombre(nombre);
                    p.setApellido(apellido);
                    p.setCorreo(mail);
                    p.setpass(password);
                    databaseReference.child("Usuarios").child(id).setValue(p);
                    cleanbox();
                    FancyToast.makeText(getApplicationContext(), "Registered",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                    Intent intent= new Intent(Register_Activity.this, MainActivity.class);
                    startActivity(intent);

                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        FancyToast.makeText(getApplicationContext(), "That email has already been registered",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                    }
                    FancyToast.makeText(getApplicationContext(), "Registered fail",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

                }
            }
        });
    }

    private void cleanbox(){
        nameET.setText("");
        lastET.setText("");
        mailET.setText("");
        passET1.setText("");
        passET2.setText("");
    }
    private void InicializarFirebase(){

        FirebaseApp.initializeApp(Register_Activity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    public boolean Validado(){
        if (nameET.getText().length()==0){
            nameLayout.setError("Put your name");
            return false;
        }else {
            nameLayout.setError(null);
        }
        if(lastET.getText().length()==0){
            lastLayout.setError("Put your last name");
            return false;
        }else{
            lastLayout.setError(null);
        }
        if(Patterns.EMAIL_ADDRESS.matcher(mailET.getText().toString()).matches()==false){
            mailLayout.setError("Invalid email");
            return false;
        }else{
            mailLayout.setError(null);
        }
        if(passET1.getText().length()<8||passET1.getText().length()>16){
            passLayout1.setError("Must be 8 to 16 characters");
            return false;
        }else{
            passLayout1.setError(null);
        }
        if(!passET1.getText().toString().equals(passET2.getText().toString())){
            passLayout2.setError("The password does't match");

            return false;
        }else{
            passLayout2.setError(null);
        }
        return true;
    }
    public void Inicializar(){
        firebaseAuth = FirebaseAuth.getInstance();
        nameLayout = findViewById(R.id.nameLYR);
        lastLayout= findViewById(R.id.lastLYR);
        savebtn = findViewById(R.id.registerbtn);
        nameET = findViewById(R.id.etname);
        lastET = findViewById(R.id.lastet);
        mailLayout = findViewById(R.id.emailLYR);
        mailET = findViewById(R.id.emailEtR);
        passLayout1 = findViewById(R.id.passLY1R);
        passET1 = findViewById(R.id.passEt1R);
        passLayout2 = findViewById(R.id.passLY2R);
        passET2 = findViewById(R.id.passET2R);
    }
}

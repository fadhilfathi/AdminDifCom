package id.fathi.admindifcom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    Button login;
//    TextView tambahadmin;
    EditText email, password;
    DatabaseReference databaseReference;
    String key;
    Admin admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
//        tambahadmin = (TextView) findViewById(R.id.tambahadmin);
        login = (Button) findViewById(R.id.button_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

//        tambahadmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent tambahAdmin = new Intent(LoginActivity.this,TambahAdminActivity.class);
//                startActivity(tambahAdmin);
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(LoginActivity.this,HomeAdminActivity.class);
                startActivity(home);
//                databaseReference.orderByChild("email").equalTo(email.getText().toString()).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot data : dataSnapshot.getChildren()) {
//                            key = data.getKey();
//                        }
//                        databaseReference.child(key).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                admin = dataSnapshot.getValue(Admin.class);
//                                if (password.getText().toString().equals(admin.getPassword())) { Toast.makeText(LoginActivity.this, "login sukses", Toast.LENGTH_SHORT).show();
//                                        key = null;
//                                } else {
//                                        Toast.makeText(LoginActivity.this, "password salah", Toast.LENGTH_SHORT).show();
//                                        key = null;
//                                }
//                            }
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                            });
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
            }
        });

    }
}

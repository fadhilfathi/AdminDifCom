package id.fathi.admindifcom.Controller.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.fathi.admindifcom.Controller.Adapter.RecyclerViewAdapterTamu;
import id.fathi.admindifcom.Model.Tamu;
import id.fathi.admindifcom.R;

public class HomeAdminActivity extends AppCompatActivity {

    DatabaseReference databaseReferenceTamu, databaseReferenceDifabel, databaseReferencePendamping;
    RecyclerView recyclerView;
    private List<Tamu> tamu;
    private RecyclerViewAdapterTamu recyclerViewAdapterTamu;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        databaseReferenceTamu = FirebaseDatabase.getInstance().getReference("Tamu");
        databaseReferenceDifabel = FirebaseDatabase.getInstance().getReference("Difabel");
        databaseReferencePendamping = FirebaseDatabase.getInstance().getReference("Pendamping");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        load();

    }

    private void load(){
        tamu = new ArrayList<>();
        recyclerViewAdapterTamu = new RecyclerViewAdapterTamu(HomeAdminActivity.this,tamu);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeAdminActivity.this));
        recyclerView.setAdapter(recyclerViewAdapterTamu);
        databaseReferenceTamu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tamu.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Tamu temp = postSnapShot.getValue(Tamu.class);
                    tamu.add(temp);
                }
                recyclerViewAdapterTamu.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

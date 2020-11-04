package id.fathi.admindifcom.Controller.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import id.fathi.admindifcom.API.JavaMailAPI;
import id.fathi.admindifcom.Model.Tamu;
import id.fathi.admindifcom.Model.User;
import id.fathi.admindifcom.R;

public class RecyclerViewAdapterTamu extends RecyclerView.Adapter<RecyclerViewAdapterTamu.RecycleViewHolder> {

    DatabaseReference databaseReference,databaseReferenceDifabel, databaseReferencePendamping;
    FirebaseAuth firebaseAuth;
    Context mContext;
    List<Tamu> mData;
    Dialog myDialog;

    public RecyclerViewAdapterTamu(Context mContext, List<Tamu> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_tamu,parent,false);
        final RecycleViewHolder vholder = new RecycleViewHolder(v);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Tamu");
        databaseReferenceDifabel = FirebaseDatabase.getInstance().getReference("Difabel");
        databaseReferencePendamping = FirebaseDatabase.getInstance().getReference("Pendamping");
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.alertdialog_tamu);
        myDialog.setTitle("Data Pendaftar");

        vholder.dialog_tamu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView nama = (TextView) myDialog.findViewById(R.id.namadialog);
                TextView ttl = (TextView) myDialog.findViewById(R.id.ttldialog);
                TextView nik = (TextView) myDialog.findViewById(R.id.nikdialog);
                TextView jeniskelamin = (TextView) myDialog.findViewById(R.id.jeniskelamindialog);
                TextView alamat = (TextView) myDialog.findViewById(R.id.alamatdialog);
                TextView pekerjaan = (TextView) myDialog.findViewById(R.id.pekerjaandialog);
                TextView nohp = (TextView) myDialog.findViewById(R.id.nohpdialog);
                Button tolak = (Button) myDialog.findViewById(R.id.tolakdialog);
                Button terima = (Button) myDialog.findViewById(R.id.terimadalog);

                nama.setText(" : " + mData.get(vholder.getAdapterPosition()).getNama());
                ttl.setText(" : " + mData.get(vholder.getAdapterPosition()).getTtl());
                nik.setText(" : " + mData.get(vholder.getAdapterPosition()).getNik());
                jeniskelamin.setText(" : " + mData.get(vholder.getAdapterPosition()).getJeniskelamin());
                alamat.setText(" : " + mData.get(vholder.getAdapterPosition()).getAlamat());
                pekerjaan.setText(" : " + mData.get(vholder.getAdapterPosition()).getPekerjaan());
                nohp.setText(" : " + mData.get(vholder.getAdapterPosition()).getNohp());

                tolak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseReference.child(mData.get(vholder.getAdapterPosition()).getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mContext, "Pendaftar Berhasil Ditolak", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                terima.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mData.get(vholder.getAdapterPosition()).getJenis().equals("Difabel")){
                            firebaseAuth.createUserWithEmailAndPassword(mData.get(vholder.getAdapterPosition()).getEmail(),
                                    mData.get(vholder.getAdapterPosition()).getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    FirebaseUser temp = FirebaseAuth.getInstance().getCurrentUser();
                                    User user = new User(temp.getUid(), mData.get(vholder.getAdapterPosition()).getEmail(), mData.get(vholder.getAdapterPosition()).getPassword(),
                                            mData.get(vholder.getAdapterPosition()).getNama(), mData.get(vholder.getAdapterPosition()).getTtl(), mData.get(vholder.getAdapterPosition()).getNik(),
                                            mData.get(vholder.getAdapterPosition()).getJeniskelamin(), mData.get(vholder.getAdapterPosition()).getAlamat(), mData.get(vholder.getAdapterPosition()).getPekerjaan(),
                                            mData.get(vholder.getAdapterPosition()).getNohp(), 0.0, 0.0, "Tidak Perlu Pendamping");
                                    JavaMailAPI javaMailAPI = new JavaMailAPI(mContext, mData.get(vholder.getAdapterPosition()).getEmail(), "Diffable Companion", "Selamat pendaftaran anda pada Diffable Companion telah diterima");
                                    javaMailAPI.execute();
                                    databaseReferenceDifabel.child(temp.getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(mContext, "Pendaftar Berhasil Diterima", Toast.LENGTH_SHORT).show();
                                }
                            });
                                }
                            });
                        }
                        if (mData.get(vholder.getAdapterPosition()).getJenis().equals("Pendamping")){
                            firebaseAuth.createUserWithEmailAndPassword(mData.get(vholder.getAdapterPosition()).getEmail(),
                                    mData.get(vholder.getAdapterPosition()).getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    FirebaseUser temp = FirebaseAuth.getInstance().getCurrentUser();
                                    User user = new User(temp.getUid(), mData.get(vholder.getAdapterPosition()).getEmail(), mData.get(vholder.getAdapterPosition()).getPassword(),
                                            mData.get(vholder.getAdapterPosition()).getNama(), mData.get(vholder.getAdapterPosition()).getTtl(), mData.get(vholder.getAdapterPosition()).getNik(),
                                            mData.get(vholder.getAdapterPosition()).getJeniskelamin(), mData.get(vholder.getAdapterPosition()).getAlamat(), mData.get(vholder.getAdapterPosition()).getPekerjaan(),
                                            mData.get(vholder.getAdapterPosition()).getNohp(), 0.0, 0.0, "Tidak Tersedia");
                                    JavaMailAPI javaMailAPI = new JavaMailAPI(mContext, mData.get(vholder.getAdapterPosition()).getEmail(), "Diffable Companion", "Selamat pendaftaran anda pada Diffable Companion telah diterima");
                                    javaMailAPI.execute();
                                    databaseReferencePendamping.child(temp.getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(mContext, "Pendaftar Berhasil Diterima", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                        Query query = databaseReference.orderByChild("email").equalTo(mData.get(vholder.getAdapterPosition()).getEmail());
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                    dataSnapshot1.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });

                myDialog.show();
            }
        });

        return vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {

        holder.email.setText(mData.get(position).getEmail());
        holder.jenis.setText(mData.get(position).getJenis());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder{

        TextView email, jenis;
        LinearLayout dialog_tamu;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);

            dialog_tamu = (LinearLayout) itemView.findViewById(R.id.recycler_tamu);
            email = (TextView) itemView.findViewById(R.id.email);
            jenis = (TextView) itemView.findViewById(R.id.jenis);

        }
    }

}

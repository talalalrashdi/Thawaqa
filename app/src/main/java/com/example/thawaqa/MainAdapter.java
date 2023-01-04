package com.example.thawaqa;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel , MainAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull MainModel model) {

        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());

        Glide.with(holder.image.getContext())
                .load(model.getImage())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)

                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.image);



        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.image.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1200)
                        .create();



                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.txtBookName);
                EditText price = view.findViewById(R.id.txtBookPrice);
                EditText image = view.findViewById(R.id.txtImgUrl);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                name.setText(model.getName());
                price.setText(model.getPrice());
                image.setText(model.getImage());

                dialogPlus.show();


                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, Object> map = new HashMap<>();
                        map.put("Price", price.getText().toString());

                        map.put("image" , image.getText().toString());
                        map.put("name" , name.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Books")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Book Updated Successfuly", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(holder.name.getContext(), "Error cannot update", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });


            }
        });


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure to delete this ?");
                builder.setMessage("You can not recover agine");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase.getInstance().getReference().child("Books")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(holder.name.getContext(), "has cencelled" , Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent, false);
        return new  myViewHolder(view);

    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView image;

        Button btnEdit,btnDelete;

        TextView  auth,  price,  name;
        public myViewHolder(@NonNull View itemView){

            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.idIVimage);
            name = (TextView) itemView.findViewById(R.id.idBooktext);
            price = (TextView)itemView.findViewById(R.id.priceID);

            btnEdit = (Button) itemView.findViewById(R.id.btnEdite);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);

        }
    }

}

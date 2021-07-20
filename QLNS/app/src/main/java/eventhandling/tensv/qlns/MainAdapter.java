package eventhandling.tensv.qlns;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Guideline;
import androidx.coordinatorlayout.widget.ViewGroupUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHodel> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull @NotNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    // có thêm final ở position hi thanh
    protected void onBindViewHolder(@NonNull @NotNull MainAdapter.myViewHodel holder, final int position, @NonNull @NotNull MainModel model) {
        holder.name.setText(model.getName());
        holder.course.setText(model.getCourse());
        holder.email.setText(model.getEmail());

        Glide.with(holder.img.getContext())
                .load(model.getTurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                         .setContentHolder(new ViewHolder(R.layout.update_popup))
                         .setExpanded(true,1300)
                         .create();
//                 dialogPlus.show();
                View view = dialogPlus.getHolderView();
// đay là hienr thị ở pahnf sửa nha thạnh
                EditText name = view.findViewById(R.id.txtName);
                EditText course = view.findViewById(R.id.txtCourse);
                EditText email = view.findViewById(R.id.txtEmail);
                EditText turl = view.findViewById(R.id.txtImage);

                Button btnUpdate = view.findViewById(R.id.txtUpdate);
                 name.setText(model.getName());
                course.setText(model.getCourse());
                email.setText(model.getEmail());
                turl.setText(model.getTurl());

                dialogPlus.show();
                 btnUpdate.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         //ham map há mơi nên ko biết
                         Map<String,Object> map = new HashMap<>();
                         map.put("name",name.getText().toString());
                         map.put("course",course.getText().toString());
                         map.put("email",email.getText().toString());
                         map.put("turl",turl.getText().toString());

                         FirebaseDatabase.getInstance().getReference().child("teachers")
                                 .child(getRef(position).getKey()).updateChildren(map)
                                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void unused) {
                                         Toast.makeText(holder.name.getContext(),"Sửa học viên ok nha !", Toast.LENGTH_SHORT).show();
                                         dialogPlus.dismiss();                                     }
                                 })
                                 //neu sai nha
                                 .addOnFailureListener(new OnFailureListener() {
                                     @Override
                                     public void onFailure(Exception e) {
                                         Toast.makeText(holder.name.getContext(),"Sửa học viên thất bại !", Toast.LENGTH_SHORT).show();
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
                builder.setTitle("Bạn có muốn xóa?");
                builder.setMessage("Xóa học viên rồi sẽ không trở lại được đó !");

                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("teachers")
                                .child(getRef(position).getKey()).removeValue();
                        Toast.makeText(holder.name.getContext(),"Bạn đã xóa học viên !",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.name.getContext(),"Đã thoát !",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public myViewHodel onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHodel(view);
    }

    class myViewHodel extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView name, course, email;

        Button btnEdit,btnDelete;

        public myViewHodel(@NonNull View itemView) {
            super(itemView);

            img =(CircleImageView)itemView.findViewById(R.id.img1);
            name =(TextView)itemView.findViewById(R.id.nametext);
            course =(TextView)itemView.findViewById(R.id.coursetext);
            email =(TextView)itemView.findViewById(R.id.emailtext);

            btnEdit = (Button)itemView.findViewById(R.id.btnEdit);
            btnDelete= (Button)itemView.findViewById(R.id.btnDelete);
        }
    }
}


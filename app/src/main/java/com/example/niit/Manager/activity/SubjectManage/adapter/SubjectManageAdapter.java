package com.example.niit.Manager.activity.SubjectManage.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niit.Manager.activity.SubjectManage.SubjectManageActivity;
import com.example.niit.Manager.activity.SubjectManage.pojo.Subjects;
import com.example.niit.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectManageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Subjects> subjectsList;
    private DatabaseReference databaseReference;

    public SubjectManageAdapter(Context context, List<Subjects> subjectsList, DatabaseReference databaseReference) {
        this.context = context;
        this.subjectsList = subjectsList;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_subject_manage, viewGroup, false);
        return new SubjectManageViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final SubjectManageViewHodler hodler = (SubjectManageViewHodler) viewHolder;

        final Subjects subject = subjectsList.get(i);

        hodler.position = i;

        hodler.txt_subject.setText(subject.getSubject());

        hodler.img_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_edit:
                                final Dialog dialog = new Dialog(context);
                                dialog.setTitle("Sửa môn học");
                                dialog.setContentView(R.layout.dialog_edit_subject);

                                final EditText edt_subject = dialog.findViewById(R.id.edt_subject);
                                Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                                Button btn_edit = dialog.findViewById(R.id.btn_edit);

                                edt_subject.setText(subject.getSubject());

                                btn_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                btn_edit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final String subjectString = edt_subject.getText().toString().trim();
                                        final Subjects subjects = new Subjects(subject.getId(), subjectString);
                                        databaseReference.child("subjects").child(subject.getId()).setValue(subjects, new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                                if (databaseError == null) {
                                                    Toast.makeText(context, "Sửa thành công !", Toast.LENGTH_SHORT).show();
                                                    subjectsList.remove(hodler.position);
                                                    dialog.dismiss();
                                                } else {
                                                    Toast.makeText(context, "Sửa thất bại, thử lại !", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });

                                dialog.show();
                                break;
                            case R.id.item_delete:
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Bạn có muốn xóa không ?");
                                builder.setCancelable(false);
                                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        databaseReference.child("subjects").child(subject.getId()).removeValue(new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                                Toast.makeText(context, "Xóa thành công !", Toast.LENGTH_SHORT).show();
                                                subjectsList.remove(hodler.position);
                                                notifyDataSetChanged();
                                            }
                                        });
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                break;
                        }

                        return false;
                    }
                });

                popupMenu.getMenuInflater().inflate(R.menu.popup_option_menu, popupMenu.getMenu());
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (subjectsList != null) return subjectsList.size();
        return 0;
    }

    class SubjectManageViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_subject)
        TextView txt_subject;
        @BindView(R.id.img_popup)
        ImageView img_popup;
        int position;

        public SubjectManageViewHodler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

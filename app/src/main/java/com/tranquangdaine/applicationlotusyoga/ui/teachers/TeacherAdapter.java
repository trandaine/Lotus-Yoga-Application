// app/src/main/java/com/tranquangdaine/applicationlotusyoga/ui/teachers/TeacherAdapter.java
package com.tranquangdaine.applicationlotusyoga.ui.teachers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tranquangdaine.applicationlotusyoga.R;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {
    private List<Teacher> teachers;
    private OnTeacherEditListener editListener;
    private OnTeacherDeleteListener deleteListener;

    public interface OnTeacherEditListener {
        void onEdit(Teacher teacher);
    }
    public void setOnTeacherEditListener(OnTeacherEditListener listener) {
        this.editListener = listener;
    }
    public interface OnTeacherDeleteListener {
        void onDelete(Teacher teacher);
    }
    public void setOnTeacherDeleteListener(OnTeacherDeleteListener listener) {
        this.deleteListener = listener;
    }

    public TeacherAdapter(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_teacher, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher teacher = teachers.get(position);
        holder.name.setText(teacher.name);
        holder.bio.setText(teacher.bio);

        holder.editButton.setOnClickListener(v -> {
            if (editListener != null) editListener.onEdit(teacher);
        });
        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) deleteListener.onDelete(teacher);
        });
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
        notifyDataSetChanged();
    }

    static class TeacherViewHolder extends RecyclerView.ViewHolder {
        TextView name, bio;
        ImageButton editButton, deleteButton;

        TeacherViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewTeacherName);
            bio = itemView.findViewById(R.id.textViewTeacherBio);
            editButton = itemView.findViewById(R.id.buttonEditTeacher);
            deleteButton = itemView.findViewById(R.id.buttonDeleteTeacher);
        }
    }
}
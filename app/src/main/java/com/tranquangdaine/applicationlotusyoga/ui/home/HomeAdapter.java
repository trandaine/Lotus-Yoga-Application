package com.tranquangdaine.applicationlotusyoga.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tranquangdaine.applicationlotusyoga.R;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Course;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.CourseViewHolder> {
    private List<Course> courses;

    public HomeAdapter(List<Course> courses) {
        this.courses = courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.textViewName.setText(course.getName());
        // Bind other fields if needed
    }

    @Override
    public int getItemCount() {
        return courses == null ? 0 : courses.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewCourseName);
        }
    }
}
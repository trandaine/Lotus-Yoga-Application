package com.tranquangdaine.applicationlotusyoga.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.tranquangdaine.applicationlotusyoga.R;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Category;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Course;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private List<Course> courses;

    public HomeAdapter(List<Course> courses) {
        this.courses = courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

//    @NonNull
//    @Override
//    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_course, parent, false);
//        return new HomeViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
//        Course course = courses.get(position);
//        holder.textViewName.setText(course.getName());
//        // Bind other fields if needed
//    }
//
//    @Override
//    public int getItemCount() {
//        return courses == null ? 0 : courses.size();
//    }
//
//    static class HomeViewHolder extends RecyclerView.ViewHolder {
//        TextView textViewName;
//        public HomeViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textViewName = itemView.findViewById(R.id.textViewCourseName);
//        }
//    }
//public void setCategoryList(List<Category> categories) {
//    categoryNameMap = new HashMap<>();
//    for (Category c : categories) {
//        categoryNameMap.put(c.getId(), c.getName());
//    }
//}
//
//    public void setTeacherList(List<Teacher> teachers) {
//        teacherNameMap = new HashMap<>();
//        for (Teacher t : teachers) {
//            teacherNameMap.put(t.getId(), t.getName());
//        }
//    }


    // Implement other required methods like onCreateViewHolder, onBindViewHolder, getItemCount, etc.

    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);
        return new HomeAdapter.HomeViewHolder(view); // Replace with actual implementation
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        Course course = courses.get(position);

        holder.courseName.setText(course.getName());
        holder.courseDescription.setText(course.getDescription());
        holder.courseRoom.setText("Room: " + course.getRoom());
        holder.coursePrice.setText("Price: " + course.getPrice() + " £");
        holder.courseDuration.setText("Duration: " + course.getDuration() + " min");

        // Set chip text
        holder.courseLevel.setText(course.getLevel());

        // Image loading
        Glide.with(holder.courseImage.getContext())
                .load(course.getImageUrl())
                .placeholder(R.drawable.ic_alt_image)
                .error(R.drawable.ic_error)
                .fitCenter()
                .into(holder.courseImage);

        // Set chip colors
        String level = course.getLevel();
        if ("beginner".equalsIgnoreCase(level)) {
            holder.courseLevel.setChipBackgroundColorResource(R.color.white);
        } else if ("intermediate".equalsIgnoreCase(level)) {
            holder.courseLevel.setChipBackgroundColorResource(R.color.green);
        } else if ("advanced".equalsIgnoreCase(level)) {
            holder.courseLevel.setChipBackgroundColorResource(R.color.red);
        } else if ("vip".equalsIgnoreCase(level)) {
            holder.courseLevel.setChipBackgroundColorResource(R.color.yellow);
        } else {
            holder.courseLevel.setChipBackgroundColorResource(R.color.orange);
        }
    }



    public int getItemCount() {
        return courses != null ? courses.size() : 0;
    }


    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageView courseImage;
        TextView courseName, courseDescription, courseRoom, coursePrice, courseDuration;
        Chip courseLevel;

        public HomeViewHolder(View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.imageViewCourse);
            courseName = itemView.findViewById(R.id.textViewCourseName);
            courseDescription = itemView.findViewById(R.id.textViewCourseDescription);
            courseRoom = itemView.findViewById(R.id.textViewCourseRoom);
            coursePrice = itemView.findViewById(R.id.textViewCoursePrice);
            courseLevel = itemView.findViewById(R.id.chipCourseLevel);
            courseDuration = itemView.findViewById(R.id.textViewCourseDuration);
        }
    }
}
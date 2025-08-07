package com.tranquangdaine.applicationlotusyoga.ui.courses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> courses;
    private OnEditClickListener onEditClickListener;
    private OnDeleteClickListener onDeleteClickListener;
    private Map<Integer, String> categoryNameMap;
    private Map<Integer, String> teacherNameMap;

    // Define your CourseViewHolder class here
    public CourseAdapter(List<Course> courses) {
        this.courses = courses;
    }

    public void setOnEditClickListener(OnEditClickListener listener) {
        this.onEditClickListener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }
    public interface OnDeleteClickListener {
        void onDeleteClick(Course course);
    }
    public interface OnEditClickListener {
        void onEditClick(Course course);
    }
    public void setCategoryList(List<Category> categories) {
        categoryNameMap = new HashMap<>();
        for (Category c : categories) {
            categoryNameMap.put(c.getId(), c.getName());
        }
    }

    public void setTeacherList(List<Teacher> teachers) {
        teacherNameMap = new HashMap<>();
        for (Teacher t : teachers) {
            teacherNameMap.put(t.getId(), t.getName());
        }
    }


    // Implement other required methods like onCreateViewHolder, onBindViewHolder, getItemCount, etc.

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view); // Replace with actual implementation
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.courseName.setText(course.getName());
        holder.courseDescription.setText(course.getDescription());
        holder.courseRoom.setText("Room: " + course.getRoom());
        holder.coursePrice.setText("Price: " + course.getPrice());
        holder.courseLevel.setText(course.getLevel());
        holder.courseDuration.setText("Duration: " + course.getDuration());
        String categoryName = categoryNameMap.get(course.getCategoryId());
        String teacherName = teacherNameMap.get(course.getTeacherId());

        holder.courseCategory.setText((categoryName != null ? categoryName : "Unknown"));
        holder.courseTeacher.setText("Teacher: " + (teacherName != null ? teacherName : "Unknown"));


        String imageUrl = course.getImageUrl();
        Glide.with(holder.courseImage.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_alt_image) // optional: add a placeholder drawable
                .error(R.drawable.ic_error) // optional: add an error drawable
                .into(holder.courseImage);

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
            holder.courseLevel.setChipBackgroundColorResource(R.color.pink); // default
        }


        holder.editButton.setOnClickListener(v -> {
            if (onEditClickListener != null) {
                onEditClickListener.onEditClick(course);
            }
        });
        holder.deleteButton.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(course);
            }
        });



    }
    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }


    public int getItemCount() {
        return courses != null ? courses.size() : 0;
    }


    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        ImageView courseImage;
        TextView courseName, courseDescription, courseRoom, coursePrice, courseTeacher, courseDuration;
        Chip courseCategory, courseLevel;
        ImageButton editButton, deleteButton;

        public CourseViewHolder(View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.imageViewCourse);
            courseName = itemView.findViewById(R.id.textViewCourseName);
            courseDescription = itemView.findViewById(R.id.textViewCourseDescription);
            courseRoom = itemView.findViewById(R.id.textViewCourseRoom);
            coursePrice = itemView.findViewById(R.id.textViewCoursePrice);
            courseLevel = itemView.findViewById(R.id.chipCourseLevel);
            editButton = itemView.findViewById(R.id.editCourseButton);
            deleteButton = itemView.findViewById(R.id.deleteCourseButton);
            courseCategory = itemView.findViewById(R.id.chipCourseCategory);
            courseTeacher = itemView.findViewById(R.id.textViewCourseTeacher);
            courseDuration = itemView.findViewById(R.id.textViewCourseDuration);
        }
    }

}

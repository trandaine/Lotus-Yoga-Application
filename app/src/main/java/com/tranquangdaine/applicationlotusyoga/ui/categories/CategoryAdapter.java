package com.tranquangdaine.applicationlotusyoga.ui.categories;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tranquangdaine.applicationlotusyoga.R;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Category;


import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categories;
    private CategoryAdapter.OnCategoryEditListener editListener;
    private CategoryAdapter.OnCategoryDeleteListener deleteListener;

    public interface OnCategoryEditListener {
        void onEdit(Category category);
    }
    public void setOnCategoryEditListener(OnCategoryEditListener listener) {
        this.editListener = listener;
    }
    public interface OnCategoryDeleteListener {
        void onDelete(Category category);
    }
    public void setOnCategoryDeleteListener(OnCategoryDeleteListener listener) {
        this.deleteListener = listener;
    }

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.name.setText(category.name);
        holder.description.setText(category.description);

        holder.editButton.setOnClickListener(v -> {
            if (editListener != null) editListener.onEdit(category);
        });
        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) deleteListener.onDelete(category);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView name, description;
        ImageButton editButton, deleteButton;

        CategoryViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewCategoryName);
            description = itemView.findViewById(R.id.textViewDescription);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
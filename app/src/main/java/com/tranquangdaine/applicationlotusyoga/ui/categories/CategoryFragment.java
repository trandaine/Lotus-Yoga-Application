package com.tranquangdaine.applicationlotusyoga.ui.categories;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tranquangdaine.applicationlotusyoga.databinding.FragmentCategoryBinding;
import com.tranquangdaine.applicationlotusyoga.infrastructure.LotusYogaDbContext;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Category;
import com.tranquangdaine.applicationlotusyoga.services.categoryControllers.CreateCategoryActivity;
import com.tranquangdaine.applicationlotusyoga.services.categoryControllers.EditCategoryActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    private FragmentCategoryBinding binding;
    private CategoryAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.floatingActionButtonAddCategory.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), CreateCategoryActivity.class);
            startActivity(intent);
        });

        adapter = new CategoryAdapter(new ArrayList<>()); // Initialize the adapter with an empty list, if not the app will crash on a null object reference
        adapter.setOnCategoryEditListener(category -> {
            Intent intent = new Intent(requireContext(), EditCategoryActivity.class);
            intent.putExtra("categoryId", category.categoryId);
            startActivity(intent);
        });

        adapter.setOnCategoryDeleteListener(category -> {
            requireActivity().runOnUiThread(() -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Delete Category")
                        .setMessage("Are you sure you want to delete this category?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            new Thread(() -> {
                                try {
                                    LotusYogaDbContext.getInstance(requireContext()).categoryDao().deleteCategory(category);
                                    List<Category> categories = LotusYogaDbContext.getInstance(requireContext())
                                            .categoryDao().getAllCategories();
                                    requireActivity().runOnUiThread(() -> adapter.setCategories(categories));
                                } catch (Exception e) {
                                    requireActivity().runOnUiThread(() ->
                                            Toast.makeText(requireContext(), "Cannot delete: Category is referenced by other records", Toast.LENGTH_LONG).show()
                                    );
                                }
                            }).start();
                        })
                        .setNegativeButton("No", null)
                        .show();
            });
        });

        binding.classesRecyclerView.setAdapter(adapter);
        binding.classesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        new Thread(() -> {
            List<Category> categories = LotusYogaDbContext.getInstance(requireContext())
                    .categoryDao().getAllCategories();
            requireActivity().runOnUiThread(() -> adapter.setCategories(categories));
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

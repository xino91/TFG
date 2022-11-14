package com.example.apprpe.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprpe.R;
import com.example.apprpe.modelo.Ent_Realizado;

import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getAllEntrenamientosRealizados().observe(getViewLifecycleOwner(), new Observer<List<Ent_Realizado>>() {
            @Override
            public void onChanged(List<Ent_Realizado> ent_realizados) {

            }
        });
        return root;
    }
}
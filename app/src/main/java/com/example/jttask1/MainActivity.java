package com.example.jttask1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.appinfosdk.Launcher;
import com.example.appinfosdk.PackageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private AppNameAdapter adapter;
    private List<PackageInfo> exampleList;

    Launcher objSDKLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        objSDKLauncher = new Launcher(this);

        objSDKLauncher.showDialog();
        Runnable runnable = () -> {
            fillExampleList();
            runOnUiThread(() -> {
                objSDKLauncher.dismissDialog();
                setUpRecyclerView();
            });
        };

        Executors.newSingleThreadExecutor().execute(runnable);


    }

    private void fillExampleList() {
        exampleList = new ArrayList<>();
        exampleList = objSDKLauncher.getPackages();
       /* exampleList.add(new PInfo("One", "Ten", "1.0", 1, null));
        exampleList.add(new PInfo("Two", "Eleven", "1.0", 1, null));
        exampleList.add(new PInfo("Three", "Twelve", "1.0", 1, null));
        exampleList.add(new PInfo("Four", "Thirteen", "1.0", 1, null));*/
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new AppNameAdapter(exampleList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }



}
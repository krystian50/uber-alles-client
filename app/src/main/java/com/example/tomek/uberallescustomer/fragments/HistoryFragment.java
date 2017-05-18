package com.example.tomek.uberallescustomer.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tomek.uberallescustomer.R;
import com.example.tomek.uberallescustomer.api.pojo.Fare;
import com.example.tomek.uberallescustomer.api.pojo.HistorialFare;
import com.example.tomek.uberallescustomer.api.pojo.Point;
import com.example.tomek.uberallescustomer.database.FeedReaderDbHelper;
import com.example.tomek.uberallescustomer.utils.RecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.tomek.uberallescustomer.LogedUserData.FARES_LIST;
import static com.example.tomek.uberallescustomer.LoginActivity.giveMeLoginContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Fare> fares;
    RecyclerAdapter adapter;
    public static boolean refershing = false;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        createList();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        adapter = new RecyclerAdapter(createList(), getActivity());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of LoginFragment");
        super.onResume();
        if (refershing) {
            HistoryFragment historyFragment = new HistoryFragment();
            openFragment(historyFragment);
            refershing = false;
        }
    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of loginFragment");
        super.onPause();
    }

    private ArrayList<Fare> createExampleList() {
        fares = new ArrayList<>();

        if (FARES_LIST == null) {
            Point first = new Point(17.1, 20.2);
            Point second = new Point(27.1, 10.2);
            FARES_LIST = new HashMap<>();
            Fare fare = new Fare(first, second, "Szymon Zwoliński", 500700600, "12/03/1016");
            FARES_LIST.put("-KiQxGUbh-TvcznBn6nX", fare);
        }
        return new ArrayList(FARES_LIST.values());

    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private ArrayList<HistorialFare> createList(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String login = prefs.getString("Authentication_Id", " ");
        Log.d("Login", login);
        FeedReaderDbHelper helper = new FeedReaderDbHelper(getContext());
        HashMap<String, HistorialFare> historyMap = helper.selectById(login);
        ArrayList<HistorialFare> historyList = new ArrayList<HistorialFare>(historyMap.values());
        return historyList;

    }
}

package com.example.test1.Recycler;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.media.session.MediaSessionManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test1.Classes.Exercises;
import com.example.test1.Adapters.MyAdapter;
import com.example.test1.Fragments.LoginFragment;
import com.example.test1.Fragments.RestPassword;
import com.example.test1.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RvExercises#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RvExercises extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Exercises> exercisesArrayList;

    MyAdapter myAdapter;
    FirebaseFirestore db;
    TextView logout;
    String about;
    ProgressDialog progressDialog;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RvExercises() {
        // Required empty public constructor
    }
    public RvExercises(String about) {
        this.about=about;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RvExercises.
     */
    // TODO: Rename and change types and number of parameters
    public static RvExercises newInstance(String param1, String param2) {
        RvExercises fragment = new RvExercises();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rv_exercises, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectComponents();
    }

    private void connectComponents() {

        recyclerView =getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        db=FirebaseFirestore.getInstance();

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("fetching data...");
        progressDialog.show();

        exercisesArrayList=new ArrayList<Exercises>();
        EventChangeListener();
       // exercisesArrayListPath=new ArrayList<String>();
        myAdapter=new MyAdapter(getActivity(),exercisesArrayList,about /*,exercisesArrayListPath*/ );

        logout=getView().findViewById(R.id.tvLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FramlayoutMain, new LoginFragment());
                ft.commit();
            }
        });
        recyclerView.setAdapter(myAdapter);

    }

    private void EventChangeListener() {
        db. collection(  "exercises" ).orderBy(  "about", Query.Direction.ASCENDING)
                . addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            progressDialog.dismiss(); // Dismiss the progressDialog in case of an error
                            Log.e("firestore error!", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                try {
                                    if (dc.getDocument().toObject(Exercises.class).getAbout().equals(about)) {
                                        exercisesArrayList.add(dc.getDocument().toObject(Exercises.class));
                                    }
                                } catch (Exception e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        }

                        myAdapter.notifyDataSetChanged(); // Move this line outside the loop

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss(); // Move this line outside the loop
                        }


                    }
                });


        }


     }




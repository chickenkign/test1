package com.example.test1.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

/*import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;*/

import com.example.test1.Classes.Exercises;
import com.example.test1.Classes.FirebaseServices;
import com.example.test1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Details extends Fragment {
    FirebaseServices fbs;
    TextView about,description,instruction,warning,name;
    VideoView video;
    Exercises exercises;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Details() {
        // Required empty public constructor
    }
    public Details(Exercises exercises){

        this.exercises=exercises;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Details.
     */
    // TODO: Rename and change types and number of parameters
    public static Details newInstance(String param1, String param2) {
        Details fragment = new Details();
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
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectComponents();
    }

    private void connectComponents() {
        name = getView().findViewById(R.id.tvNameD);
        warning = getView().findViewById(R.id.tvWarningD);
        description = getView().findViewById(R.id.tvDescriptionD);
        instruction = getView().findViewById(R.id.tvInstructionD);
        video = getView().findViewById(R.id.videoView);

        Hearme();
    }

  /*  private void playVideo() {
        YouTubePlayerView youtubePlayerView = getView().findViewById(R.id.videoView);
        String youtubeLink = exercises.getLinkVideo(); // Replace "YOUR_YOUTUBE_VIDEO_LINK" with the actual YouTube video link retrieved from Firebase

        youtubePlayerView.initialize(AIzaSyBhO7Y9VE4ZuYfOe7IZkgl0TL6tRirTkww, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (youtubeLink != null) {
                    youTubePlayer.loadVideo(youtubeLink);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                // Handle any errors that occurred while initializing the YouTube player
            }
        });
    }*/

    private void Hearme() {
        name.setText(exercises.getName());
        warning.setText(exercises.getWarning());
        description.setText(exercises.getDescription());
        instruction.setText(exercises.getInstruction());
     //   playVideo();
    }

}
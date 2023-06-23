package com.example.test1.Data;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.test1.Classes.Exercises;
import com.example.test1.Classes.FirebaseServices;
import com.example.test1.R;
import com.example.test1.Recycler.ListExe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragExercises#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragExercises extends Fragment {
    private EditText  name,about,description ,instruction,warning,video;
    private Button add;
    private ImageView image;
    private FirebaseServices fbs;
    int SELECT_PICTURE =200;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragExercises() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragExercises.
     */
    // TODO: Rename and change types and number of parameters
    public static FragExercises newInstance(String param1, String param2) {
        FragExercises fragment = new FragExercises();
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
        return inflater.inflate(R.layout.fragment_frag_exercises, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectComponents();
    }

    private void connectComponents() {
        fbs = FirebaseServices.getInstance();
        name= getView().findViewById(R.id.etNameExe);
        about= getView().findViewById(R.id.etAboutExe);
        description= getView().findViewById(R.id.etDescriptionExe);
        instruction= getView().findViewById(R.id.etInstructionExe);
        warning =getView().findViewById(R.id.etWarningExe);
        video=getView().findViewById(R.id.etVideoview);
        image=getView().findViewById(R.id.ivImage);
        add= getView().findViewById(R.id.btnAddExe);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGalleryAndSelectPhoto();
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = UUID.randomUUID().toString();
                String n = name.getText().toString();
                String a=about.getText().toString();
                String d=description.getText().toString();
                String i=instruction.getText().toString();
                String w=warning.getText().toString();
                String l=video.getText().toString();


                // TODO: check data empty
                if (n == null || a== null || d == null ||i== null || w== null || l== null ){
                    Toast.makeText(getActivity(), "data is empty!", Toast.LENGTH_SHORT).show();
                }
                 else {
                    Exercises exercise = new Exercises(id, n, a, d, i, w,l,UploadImageToFirebase());
                    //Map<String, Exercises> exercises= new HashMap<>();

                    fbs.getFire().collection("exercises").document(id)
                            .set(exercise)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // todo: goto recyclerview page
                                    Log.i("TAG", "onSuccess: ");
                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.FramlayoutMain, new ListExe());
                                    ft.commit();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("TAG", "onFailure: ");

                                }
                            });
                }



            }
        });
    }
    private void openGalleryAndSelectPhoto(){
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Picture"),SELECT_PICTURE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    image.setImageURI(selectedImageUri);
                }
            }
        }
    }
    private String UploadImageToFirebase(){
        image = getView().findViewById(R.id.ivImage);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
        Bitmap image = bitmapDrawable.getBitmap();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        StorageReference ref =fbs.getStorage().getReference("listingPictures/" + UUID.randomUUID().toString());
        UploadTask uploadTask = ref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            }
        });
        return ref.getPath();
    }


}
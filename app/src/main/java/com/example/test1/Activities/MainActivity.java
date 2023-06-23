package com.example.test1.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.test1.Classes.FirebaseServices;
import com.example.test1.Data.FragExercises;
import com.example.test1.Fragments.FragmentSignup;
import com.example.test1.Fragments.LoginFragment;
import com.example.test1.R;
import com.example.test1.Recycler.ListExe;

public class MainActivity extends AppCompatActivity {
    private FirebaseServices fbs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectComponents();
        fbs=FirebaseServices.getInstance();
        if(fbs.getAuth().getCurrentUser()!=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.FramlayoutMain, new ListExe());
            ft.commit();
        }
        else {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.FramlayoutMain, new ListExe());
            ft.commit();

        }
    }

    private void connectComponents() {

    }

    public void LOGIN() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FramlayoutMain, new LoginFragment());
        ft.commit();
    }

    public void SingUp() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FramlayoutMain, new FragmentSignup());
        ft.commit();
    }

    public void gotoAddExcersize()
    {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FramlayoutMain, new FragExercises());
        ft.commit();
    }

    public void gotoExcersizeList()
    {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FramlayoutMain, new ListExe());
        ft.commit();
    }

}
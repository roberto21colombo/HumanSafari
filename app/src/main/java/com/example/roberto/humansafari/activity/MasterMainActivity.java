package com.example.roberto.humansafari.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.fragment.MasterCharacterFragment;
import com.example.roberto.humansafari.fragment.MasterMapsFragment;
import com.example.roberto.humansafari.fragment.MasterPlayersFragment;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.adapter.SectionsPageAdapter;

public class MasterMainActivity extends AppCompatActivity {

    private static final String TAG = "MasterMainActivity";

    public SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    TextView tvGameName, tvCodCharacter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_main);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tvGameName = (TextView)findViewById(R.id.tvGameName);
        tvCodCharacter = (TextView)findViewById(R.id.tvCodCharacter);
        tvGameName.setText(Model.getInstance().getGameName());
        tvCodCharacter.setText(Model.getInstance().getCodCharacters());
    }

    public void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new MasterCharacterFragment(), "Personaggi");
        adapter.addFragment(new MasterPlayersFragment(), "Giocatori");
        adapter.addFragment(new MasterMapsFragment(), "Mappa");
        viewPager.setAdapter(adapter);
    }


}

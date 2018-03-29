package droid.demos.com.gps1.views;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import droid.demos.com.gps1.R;
import droid.demos.com.gps1.fragments.CurrentLocationFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showGetCurrentFragment();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }

    private void showGetCurrentFragment() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        CurrentLocationFragment currentLocationFragment = new CurrentLocationFragment();

        transaction.addToBackStack(CurrentLocationFragment.TAG);
        transaction.add(R.id.conteinerFragments, currentLocationFragment, CurrentLocationFragment.TAG).commit();
    }

}

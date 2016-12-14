package fr.m2sili.mtroysi.horserace;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by Morgane TROYSI on 12/9/16.
 */

public class HorseRace extends Activity implements TaskFragment.TaskCallBacks {

    InterfaceFragment interfaceFragment;
    private static final String TAG_TASKS_FRAGMENT = "task_fragment";
    private TaskFragment mTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        FragmentManager fm = getFragmentManager();
        interfaceFragment = (InterfaceFragment) fm.findFragmentById(R.id.interface_fragment);

        mTaskFragment = (TaskFragment) fm.findFragmentByTag(TAG_TASKS_FRAGMENT);
        if (mTaskFragment == null) {
            mTaskFragment = new TaskFragment();
            fm.beginTransaction().add(mTaskFragment, TAG_TASKS_FRAGMENT).commit();
        }
    }

    @Override
    public void onHorseUpdate(Horse horse) {
        interfaceFragment.onHorseUpdate(horse);
    }

    @Override
    public void onHorseDone(Horse horse) {
        interfaceFragment.onHorseDone(horse);
    }

    public void onHorseAdded(Horse horse) {
        if(mTaskFragment.getHorses() == null) {
            mTaskFragment.setHorses(new ArrayList());
        }
        mTaskFragment.onHorseAdded(horse);
    }

    public ArrayList getHorses() {
        return mTaskFragment.getHorses();
    }

    public void launchRace(Horse horse, int distance) {
        mTaskFragment.launchRace(horse, distance);
    }
}

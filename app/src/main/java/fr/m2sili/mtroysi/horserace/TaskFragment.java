package fr.m2sili.mtroysi.horserace;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by Morgane TROYSI on 12/9/16.
 */

public class TaskFragment extends Fragment {
    static interface TaskCallBacks {
        public void onHorseUpdate(Horse horse);
        public void onHorseDone(Horse horse);
    }
    private TaskCallBacks mMainActivityListener = null;
    private ArrayList horses;
//    private final Semaphore mAvailable = new Semaphore (100 , true ) ;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mMainActivityListener = (TaskCallBacks) activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        this.mMainActivityListener = null;
    }

    protected void onProgressUpdate(Horse horse) {
        if (mMainActivityListener != null) mMainActivityListener.onHorseUpdate(horse);
    }
    protected void onPostExecute(Horse horse) {
        if (mMainActivityListener != null) mMainActivityListener.onHorseDone(horse);
    }

    public void launchRace(Horse horse, int distance) {
        new RaceTask(this, distance).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,horse);
    }

    public ArrayList getHorses() {
        return horses;
    }

    public void setHorses(ArrayList horses) {
        this.horses = horses;
    }

    public void onHorseAdded(Horse horse) {
        horses.add(horse);
    }

}

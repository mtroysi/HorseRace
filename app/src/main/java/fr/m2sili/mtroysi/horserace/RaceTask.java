package fr.m2sili.mtroysi.horserace;

import android.os.AsyncTask;

import java.util.Random;

/**
 * Created by Morgane TROYSI on 12/9/16.
 */

public class RaceTask extends AsyncTask<Horse, Horse, Horse> {

    protected TaskFragment taskFragment = null;
    private Random randomGenerator;
    private static int distance;

    public RaceTask(TaskFragment taskFragment, int distance) {
        this.taskFragment = taskFragment;
        this.randomGenerator = new Random();
        this.distance = distance;
    }

    @Override
    protected Horse doInBackground(Horse... horses) {
        try {
            while (horses[0].getProgression() < distance) {
//                taskFragment.getmAvailable().acquire();
                int randomInt = randomGenerator.nextInt(distance / 10);
                if(horses[0].isBoosted()) {
                    randomInt *= 2;
                }
                horses[0].setProgression(horses[0].getProgression() + randomInt);
                publishProgress(horses[0]);
//                taskFragment.getmAvailable().release();
                Thread.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return horses[0];
    }

    @Override
    protected void onProgressUpdate(Horse... values) {
        taskFragment.onProgressUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(Horse horse) {
        taskFragment.onPostExecute(horse);
    }
}

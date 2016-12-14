package fr.m2sili.mtroysi.horserace;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Morgane TROYSI on 12/9/16.
 */

public class InterfaceFragment extends ListFragment implements TaskFragment.TaskCallBacks {
    private HorseRace mainActivity;
    private HorseAdapter adapter;
    private MenuItem startRace;
    private static int total_distance;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (HorseRace) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.interface_fragment_layout, container, false);
        setHasOptionsMenu(true);
        adapter = new HorseAdapter(inflater.getContext(), R.layout.ligne);
        setListAdapter(adapter);

//        Insertion automatique des deux chevaux
//        onHorseAdded(new Horse());
//        onHorseAdded(new Horse());
//        adapter.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mainActivity.getHorses() != null) {
            // Si l'adapter est null et qu'il y a des éléments dans le FSI (-> rotation)
            this.populate();
        } else {
            // Si l'adapter n'est pas null, on peuple celui du FSI (-> 1er lancement)
            for (int i = 0; i < adapter.getCount(); ++i) {
                mainActivity.onHorseAdded(adapter.getItem(i));
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options, menu);
        startRace = menu.findItem(R.id.start_race);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.start_race);

        if (adapter.getCount() == 0) {
            item.setEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.subscription:
                LayoutInflater inflater = LayoutInflater.from(mainActivity);
                final View alertDialogView = inflater.inflate(R.layout.alert_dialog, null);
                AlertDialog.Builder adb = new AlertDialog.Builder(mainActivity);
                adb.setView(alertDialogView);
                adb.setTitle("Nouvelle course ");
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EditText number = (EditText) alertDialogView.findViewById(R.id.saisie_num);
                        EditText distance = (EditText) alertDialogView.findViewById(R.id.saisie_distance);
                        total_distance = Integer.parseInt(distance.getText().toString());
                        adapter.clear();
                        for (int i = 0; i < Integer.parseInt(number.getText().toString()); ++i) {
//                            adapter.add(new Horse());
                            onHorseAdded();
                        }
                        adapter.notifyDataSetChanged();
                        if (adapter.getCount() != 0) {
                            startRace.setEnabled(true);
                        }
                    }
                });

                adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                });
                adb.show();
                break;
            case R.id.start_race:
                for (int i = 0; i < adapter.getCount(); ++i) {
                    adapter.getItem(i).setInProgress(true);
                    mainActivity.launchRace(adapter.getItem(i), total_distance);
                }
                break;
        }
        return true;
    }

    @Override
    public void onHorseUpdate(Horse horse) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onHorseDone(Horse horse) {
        adapter.getItem(horse.getNum() - 1).setInProgress(false);
        adapter.notifyDataSetChanged();
        Toast.makeText(mainActivity, "Arrivée de " + horse.getNum() + " !", Toast.LENGTH_SHORT).show();
    }

    public void onHorseAdded() {
        Horse horse = new Horse();
        horse.setTotal_distance(total_distance);
        adapter.add(horse);
        mainActivity.onHorseAdded(horse);
    }

    public void populate() {
        int i;
        for (i = 0; i < mainActivity.getHorses().size(); ++i) {
            adapter.add((Horse) mainActivity.getHorses().get(i));
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Horse horse = adapter.getItem(position);
        horse.setBoosted(true);
        adapter.notifyDataSetChanged();
    }
}

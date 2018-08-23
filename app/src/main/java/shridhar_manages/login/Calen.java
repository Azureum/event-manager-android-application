package shridhar_manages.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Rachit on 7/30/2016.
 */
public class Calen extends Fragment {
    CalendarView calendarView;
    TextView dated;
    Calendar calendar;
    public Calen(){


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        View rootView = inflater.inflate(R.layout.calendar, container, false);
        calendarView = new CalendarView(getContext());
        calendar = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String currentdate= ss.format(date);
       calendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            }//met
        });
        dated = (TextView) rootView.findViewById(R.id.datetime);

        rootView.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

            public void onSwipeRight() {
                Fragment fr=new MyEvents();
                FragmentChangeListener fc=(FragmentChangeListener)getActivity();
                fc.replaceFragment(fr);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.calendar_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.addcal:
                Fragment fr=new CreateEvent();
                FragmentChangeListener fc=(FragmentChangeListener)getActivity();
                fc.replaceFragment(fr);
                return true;
            default:return super.onContextItemSelected(item);

        }
    }
}


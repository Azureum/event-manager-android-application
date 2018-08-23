package shridhar_manages.login;

import android.app.AlarmManager;
//import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
//import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Rachit on 7/30/2016.
 */
public class CreateEvent extends Fragment implements View.OnClickListener {
    Spinner type;
    EditText name, desc;
    TextView datetime,time;
    Button addevent;
    DatabaseHelper myDB;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    //TimePicker timePicker;
    SimpleDateFormat dateFormat;
    ToggleButton toggleButton;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    int hr,min;
    private static CreateEvent inst;
   // int hour,min;
    public CreateEvent(){


    }
    public static CreateEvent instance(){
        return inst;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.createevent, container, false);
        name = (EditText) rootView.findViewById(R.id.name);
        desc = (EditText) rootView.findViewById(R.id.desc);
        type = (Spinner) rootView.findViewById(R.id.type);
        addevent = (Button) rootView.findViewById(R.id.addevent);
        toggleButton=(ToggleButton) rootView.findViewById(R.id.alarmtoggle);
        toggleButton.setOnClickListener(this);
        alarmManager=(AlarmManager) this.getContext().getSystemService(Context.ALARM_SERVICE);

        rootView.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
                Fragment fr=new MyEvents();
                FragmentChangeListener fc=(FragmentChangeListener)getActivity();
                fc.replaceFragment(fr);
            }
        });

       /* if (Build.VERSION.SDK_INT>=23)
        {
            hour=timePicker.getHour();
            min=timePicker.getMinute();}
        else
        {
            hour=timePicker.getCurrentHour();
            min=timePicker.getCurrentMinute();

        }*/

        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getText().toString();
                String type1 = type.getSelectedItem().toString();
                String datetime1 = datetime.getText().toString();
                String desc1 = desc.getText().toString();
                String time1=time.getText().toString();

                if(name1.length()!=0) {
                    AddData(name1,type1,datetime1,desc1,time1);
                    name.setText("");
                    desc.setText("");
                    datetime.setText("");
                    time.setText("");
                    toggleButton.setChecked(false);

                }
                else
                    Toast.makeText(getContext(), "You must put something in the text field!", Toast.LENGTH_LONG).show();
            }
        });


        myDB = new DatabaseHelper(getContext());
        dateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        datetime = (TextView) rootView.findViewById(R.id.datetime);
        //datetime.setInputType(InputType.TYPE_NULL);
        //datetime.requestFocus();
        datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        Calendar calendar=Calendar.getInstance();
        datePickerDialog=new DatePickerDialog(getContext(), new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar newDate=Calendar.getInstance();
                newDate.set(year,month,day);
                datetime.setText(dateFormat.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        time=(TextView) rootView.findViewById(R.id.timeTextView);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hr=selectedHour;
                        min=selectedMinute;
                        time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });


        return rootView;
    }




    public void AddData(String name2, String type2, String datetime2, String desc2,String time2) {

        boolean insertData = myDB.addData(name2,datetime2,type2,desc2,time2);

        if(insertData==true){
            Toast.makeText(getContext(), "Data Successfully Entered", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(), "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.alarmtoggle:
                if(((ToggleButton)v).isChecked()){
                    Log.d("MyActivity","ALARM ON");
                    Calendar calendar=Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,hr);
                    calendar.set(Calendar.MINUTE,min);
                    Intent intent=new Intent(getContext(),AlarmReceiver.class);
                    pendingIntent=PendingIntent.getBroadcast(getContext(),0,intent,0);
                    alarmManager.set(AlarmManager.RTC,calendar.getTimeInMillis(),pendingIntent);
                }
                else{
                    alarmManager.cancel(pendingIntent);
                    //setAlarmText("");
                    Log.d("MyActivity","ALARM OFF");
                }
                break;
        }
    }
}


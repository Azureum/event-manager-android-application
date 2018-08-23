package shridhar_manages.login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MyEvents extends Fragment {
    DatabaseHelper myDB;
    ListView listView;
    SQLiteDatabase newDB;
    ArrayAdapter madapter;
    ArrayList<String> results=new ArrayList<String>();
    RelativeLayout relativeLayout;
    PopupWindow popupWindow;
    public MyEvents(){


    }

    @Nullable
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //dhdhd
        View rootView = inflater.inflate(R.layout.myevent, container, false);
        myDB = new DatabaseHelper(getContext());
        listView=(ListView) rootView.findViewById(R.id.listView);
        relativeLayout=(RelativeLayout) rootView.findViewById(R.id.rl);
        openAndQueryDatabase();
        displayResultList();
        registerForContextMenu(listView);
        rootView.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
                Fragment fr=new Calen();
                FragmentChangeListener fc=(FragmentChangeListener)getActivity();
                fc.replaceFragment(fr);
            }

            public void onSwipeRight() {
                Fragment fr=new CreateEvent();
                FragmentChangeListener fc=(FragmentChangeListener)getActivity();
                fc.replaceFragment(fr);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = listView.getItemAtPosition(position).toString();
                String[] yt = str.split(" ");
                String ide = yt[0];
                String name="",time="",date="",type="",desc="";
                try{
                DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                newDB = dbHelper.getReadableDatabase();
                Cursor c=newDB.rawQuery("Select * from events where ID="+ide,null);
                if (c != null ) {
                    if  (c.moveToFirst()) {
                        do {
                            name = c.getString(1);
                            type = c.getString(3);
                            date = c.getString(2);
                            time = c.getString(5);
                            desc = c.getString(4);
                        }while (c.moveToNext());
                    }
                }
            } catch (SQLiteException se ) {
                Log.e(getClass().getSimpleName(), "Could not create or Open the database");
            }

                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customview = inflater.inflate(R.layout.viewev,null);

                popupWindow = new PopupWindow(customview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if(Build.VERSION.SDK_INT>=21){
                    popupWindow.setElevation(5.0f);
                }
                TextView tname = (TextView) customview.findViewById(R.id.v_name);
                TextView ttime = (TextView) customview.findViewById(R.id.v_time);
                TextView tdate = (TextView) customview.findViewById(R.id.v_date);
                TextView ttype = (TextView) customview.findViewById(R.id.v_type);
                TextView tdesc = (TextView) customview.findViewById(R.id.v_desc);
                tname.setText(name);
                ttime.setText(time);
                tdate.setText(date);
                ttype.setText(type);
                tdesc.setText(desc);


                ImageButton closeButton = (ImageButton) customview.findViewById(R.id.ib_close);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER,0,0);

            }
        });
        return rootView;

    }

    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId()==R.id.listView){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(listView.getContentDescription());
            String[] menuItems = getResources().getStringArray(R.array.polk);
            for(int i=0;i<menuItems.length;i++){
                menu.add(Menu.NONE,i,i,menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.polk);
        String menuItemName = menuItems[menuItemIndex];
        Toast.makeText(getActivity(),menuItemName, Toast.LENGTH_SHORT);
        //String listItemName = listView.get
        if(menuItemName=="Delete"){
            String str = listView.getItemAtPosition(info.position).toString();
            Toast.makeText(getContext(),str, Toast.LENGTH_SHORT);
            String[] yt = str.split(" ");
            String id = yt[0];
            Toast.makeText(getContext(),id, Toast.LENGTH_SHORT);
            if(myDB.deleteData(id)==1){
                Toast.makeText(getContext(),"ITEM DELETED", Toast.LENGTH_SHORT);
            }
            else
                Toast.makeText(getContext(),"ERROR OCCURED", Toast.LENGTH_SHORT);
        }
        return true;

    }*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.main_contextmenu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.delete_id:
                String str = listView.getItemAtPosition(info.position).toString();
                //Toast.makeText(getContext(),str, Toast.LENGTH_SHORT).show();
                String[] yt = str.split(" ");
                String id = yt[0];
                //Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
                if(myDB.deleteData(id)==1){
                    Toast.makeText(getContext(),"EVENT DELETED", Toast.LENGTH_SHORT).show();
                    madapter.notifyDataSetChanged();
                    Fragment fr=new MyEvents();
                    FragmentChangeListener fc=(FragmentChangeListener)getActivity();
                    fc.replaceFragment(fr);



                    //getActivity().getSupportFragmentManager().findFragmentById(R.id.Container).
                }
                else
                    Toast.makeText(getContext(),"ERROR OCCURED", Toast.LENGTH_SHORT).show();

                return true;
            default:return super.onContextItemSelected(item);

        }

    }

    private void openAndQueryDatabase() {
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            newDB = dbHelper.getWritableDatabase();
            Cursor c = newDB.rawQuery("SELECT * from events", null);
            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {
                        String id = c.getString(0);
                        String name = c.getString(1);
                        String type = c.getString(3);
                        String date = c.getString(2);
                        String time = c.getString(5);
                        results.add(id+"    "+name+"  "+type+"    "+date+"    "+time );
                    }while (c.moveToNext());

                    Log.d("rac",results.toString());
                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } /*finally {
            if (newDB != null)
                newDB.execSQL("DELETE FROM events");
            newDB.close();
        }*/
    }
    private void displayResultList() {
        madapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,results);
        listView.setAdapter(madapter);
        listView.setTextFilterEnabled(true);
    }

}


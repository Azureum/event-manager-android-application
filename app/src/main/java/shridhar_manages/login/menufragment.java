package shridhar_manages.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Rachit on 7/30/2016.
 */
public class menufragment extends Fragment {
    Fragment frag;
    FragmentTransaction fragTransaction;


    public menufragment(){


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.menufragment, container, false);

        frag = new CreateEvent();
        fragTransaction = getFragmentManager().beginTransaction().add(R.id.Container, frag);
        fragTransaction.commit();

        Button btncreateevent = (Button) rootView.findViewById(R.id.button);
        Button btnmyevent = (Button) rootView.findViewById(R.id.button2);
        Button btncalendar = (Button) rootView.findViewById(R.id.button3);

        btncreateevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag = new CreateEvent();
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.Container, frag);
                fragTransaction.commit();
            }
        });

        btnmyevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag = new MyEvents();
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.Container, frag);
                fragTransaction.commit();
            }
        });

        btncalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag = new Calen();
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.Container, frag);
                fragTransaction.commit();
            }
        });
        return rootView;
    }
}


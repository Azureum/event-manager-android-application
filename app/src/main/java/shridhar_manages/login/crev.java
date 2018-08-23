package shridhar_manages.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class crev extends Fragment {
String[] evtype={"Anniversary","Birthday","Loan Payment","Fund Collection","Party","Bill Payment"};
    ListView listView;

    public crev() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.createevent, container, false);
        listView=(ListView) rootView.findViewById(R.id.listView);
        return rootView;
    }

}

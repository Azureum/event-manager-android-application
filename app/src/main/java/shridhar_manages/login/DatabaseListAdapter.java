package shridhar_manages.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rachit on 10/5/2016.
 */
public class DatabaseListAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> id;
    ArrayList<String> name;
    ArrayList<String> date;
    ArrayList<String> type;
    ArrayList<String> desc;

    public DatabaseListAdapter(Context con,ArrayList<String> id2,ArrayList<String> name2,ArrayList<String> date2,ArrayList<String> type2,ArrayList<String> desc2){
            this.context=con;
            this.id=id2;
        this.name=name2;
        this.date=date2;
        this.type=type2;
        this.desc=desc2;
    }

    public int getCount(){
        return id.size();
    }
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    public View getView(int position, View child, ViewGroup parent) {
        Holder holder;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child=layoutInflater.inflate(R.layout.listrow,null);
            holder=new Holder();
            holder.textviewname = (TextView) child.findViewById(R.id.ev_name);
            holder.textviewtype = (TextView) child.findViewById(R.id.ev_type);
            holder.textviewdate = (TextView) child.findViewById(R.id.ev_date);

            child.setTag(holder);
        }
        else{
            holder = (Holder) child.getTag();
        }
        holder.textviewtype.setText(type.get(position));
        holder.textviewname.setText(name.get(position));
        holder.textviewdate.setText(date.get(position));

        return child;
    }
    public class Holder {
        TextView textviewid;
        TextView textviewname;
        TextView textviewdate;
        TextView textviewtype;
        TextView textviewdesc;
    }

}

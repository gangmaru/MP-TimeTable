package androidtown.org;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_timetable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_timetable extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_timetable() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_timetable.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_timetable newInstance(String param1, String param2) {
        fragment_timetable fragment = new fragment_timetable();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView monday[]=new TextView[11];
    private TextView tuesday[]=new TextView[11];
    private TextView wednesday[]=new TextView[11];
    private TextView thursday[]=new TextView[11];
    private TextView friday[]=new TextView[11];
    //private Schedule schedule=new Schedule();

    //@Override
    public void onActivityCreate(Bundle b){
        super.onActivityCreated(b);
        monday[0]=(TextView) getView().findViewById(R.id.mon0);
        monday[1]=(TextView) getView().findViewById(R.id.mon1);
        monday[2]=(TextView) getView().findViewById(R.id.mon2);
        monday[3]=(TextView) getView().findViewById(R.id.mon3);
        monday[4]=(TextView) getView().findViewById(R.id.mon4);
        monday[5]=(TextView) getView().findViewById(R.id.mon5);
        monday[6]=(TextView) getView().findViewById(R.id.mon6);
        monday[7]=(TextView) getView().findViewById(R.id.mon7);
        monday[8]=(TextView) getView().findViewById(R.id.mon8);
        monday[9]=(TextView) getView().findViewById(R.id.mon9);
        monday[10]=(TextView) getView().findViewById(R.id.mon10);

        tuesday[0]=(TextView) getView().findViewById(R.id.tue0);
        tuesday[1]=(TextView) getView().findViewById(R.id.tue1);
        tuesday[2]=(TextView) getView().findViewById(R.id.tue2);
        tuesday[3]=(TextView) getView().findViewById(R.id.tue3);
        tuesday[4]=(TextView) getView().findViewById(R.id.tue4);
        tuesday[5]=(TextView) getView().findViewById(R.id.tue5);
        tuesday[6]=(TextView) getView().findViewById(R.id.tue6);
        tuesday[7]=(TextView) getView().findViewById(R.id.tue7);
        tuesday[8]=(TextView) getView().findViewById(R.id.tue8);
        tuesday[9]=(TextView) getView().findViewById(R.id.tue9);
        tuesday[10]=(TextView) getView().findViewById(R.id.tue10);

        wednesday[0]=(TextView) getView().findViewById(R.id.wed0);
        wednesday[1]=(TextView) getView().findViewById(R.id.wed1);
        wednesday[2]=(TextView) getView().findViewById(R.id.wed2);
        wednesday[3]=(TextView) getView().findViewById(R.id.wed3);
        wednesday[4]=(TextView) getView().findViewById(R.id.wed4);
        wednesday[5]=(TextView) getView().findViewById(R.id.wed5);
        wednesday[6]=(TextView) getView().findViewById(R.id.wed6);
        wednesday[7]=(TextView) getView().findViewById(R.id.wed7);
        wednesday[8]=(TextView) getView().findViewById(R.id.wed8);
        wednesday[9]=(TextView) getView().findViewById(R.id.wed9);
        wednesday[10]=(TextView) getView().findViewById(R.id.wed10);

        thursday[0]=(TextView) getView().findViewById(R.id.thu0);
        thursday[1]=(TextView) getView().findViewById(R.id.thu1);
        thursday[2]=(TextView) getView().findViewById(R.id.thu2);
        thursday[3]=(TextView) getView().findViewById(R.id.thu3);
        thursday[4]=(TextView) getView().findViewById(R.id.thu4);
        thursday[5]=(TextView) getView().findViewById(R.id.thu5);
        thursday[6]=(TextView) getView().findViewById(R.id.thu6);
        thursday[7]=(TextView) getView().findViewById(R.id.thu7);
        thursday[8]=(TextView) getView().findViewById(R.id.thu8);
        thursday[9]=(TextView) getView().findViewById(R.id.thu9);
        thursday[10]=(TextView) getView().findViewById(R.id.thu10);

        friday[0]=(TextView) getView().findViewById(R.id.fri0);
        friday[1]=(TextView) getView().findViewById(R.id.fri1);
        friday[2]=(TextView) getView().findViewById(R.id.fri2);
        friday[3]=(TextView) getView().findViewById(R.id.fri3);
        friday[4]=(TextView) getView().findViewById(R.id.fri4);
        friday[5]=(TextView) getView().findViewById(R.id.fri5);
        friday[6]=(TextView) getView().findViewById(R.id.fri6);
        friday[7]=(TextView) getView().findViewById(R.id.fri7);
        friday[8]=(TextView) getView().findViewById(R.id.fri8);
        friday[9]=(TextView) getView().findViewById(R.id.fri9);
        friday[10]=(TextView) getView().findViewById(R.id.fri10);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }
}
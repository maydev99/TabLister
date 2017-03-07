package com.droidloft.tablister;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private SectionPagerAdapter mSectionPagerAdapter;
    private ViewPager mViewPager;
    private static ArrayList<String> myArrayList;
    private boolean isEntryScreen = true;
    private Set set;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myArrayList = new ArrayList<>();
        //ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, R.id.list_item_item, myArrayList);

        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionPagerAdapter);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, R.id.list_item_item, myArrayList);

    }

    public static class PlaceHolderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section number";
        //private ArrayAdapter myAdapter = new ArrayAdapter(MainActivity.this, R.layout.list_item, R.id.list_item_item, myArrayList);


        public PlaceHolderFragment() {

        }


        public static PlaceHolderFragment newInstance(int sectionNumber) {
            PlaceHolderFragment fragment = new PlaceHolderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = null;




            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1: {
                    rootView = inflater.inflate(R.layout.entry_fragment, container, false);
                    final EditText editText = (EditText) rootView.findViewById(R.id.editText);
                    Button saveButton = (Button) rootView.findViewById(R.id.saveButton);
                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = editText.getText().toString();
                            myArrayList.add(text);
                            editText.setText("");
                            Toast.makeText(getActivity(), "Entry Added", Toast.LENGTH_SHORT).show();


                        }


                    });


                    break;
                }

                case 2: {

                    rootView = inflater.inflate(R.layout.list_fragment, container, false);

                    final ArrayAdapter<String>myAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.list_item_item, myArrayList);
                    ListView listView = (ListView)rootView.findViewById(R.id.list_view);
                    listView.setAdapter(myAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            myArrayList.remove(i);
                            myAdapter.notifyDataSetChanged();
                        }
                    });


                    break;
                }
            }



            return rootView;
        }




    }


    public class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceHolderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }

            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about){
            AlertDialog.Builder aboutAlert = new AlertDialog.Builder(this);
            aboutAlert.setTitle("TabLister v1.0");
            aboutAlert.setMessage("by Michael May" + "\n" + "DroidLoft 2017");
            aboutAlert.setCancelable(true);
            aboutAlert.setIcon(R.mipmap.ic_launcher);
            aboutAlert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}

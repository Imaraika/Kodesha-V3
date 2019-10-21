package com.example.kodesha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kodesha.YelpBusinessRenting;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Houses extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();


    int[] IMAGES = {R.drawable.backgr1, R.drawable.backgr1, R.drawable.backgr1, R.drawable.backgr1,
            R.drawable.mostbeautfl, R.drawable.kumaziphoto, R.drawable.kodeshapngpic, R.drawable.backgr1, R.drawable.backgr1, R.drawable.backgr1};

//    String[] hsesRoad = {"707 Kicukiro Ave", "2206 gisz ruhango GD","2816 Beletoire Ave", "8227 Folcroft kigali", "9227 lene KK","707 Kicukiro Ave",
//            "2206 gisz ruhango GD","2816 Beletoire Ave", "8227 Folcroft kigali", "9227 lene KK",};


    @BindView(R.id.listView)
    ListView listOfHouses;
    @BindView(R.id.display_Location_TextView)
    TextView dispLocationText;
    @BindView(R.id.errorTextView)
    TextView mErrorTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houses);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        listOfHouses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String houses = ((TextView) view).getText().toString();
                Toast.makeText(Houses.this, houses, Toast.LENGTH_LONG).show();
            }
        });
        dispLocationText.setText("Here are all the restaurants near: " + location);

        YelpApi client = YelpClient.getClient();

        Call<YelpBusinessRenting> call = client.getHouses(location, "houses");
        call.enqueue(new Callback<YelpBusinessRenting>() {
            @Override
            public void onResponse(Call<YelpBusinessRenting> call, Response<YelpBusinessRenting> response) {

                if (response.isSuccessful()) {
                    List<Business> HousesList = response.body().getBusinesses();
                    String[] houses = new String[HousesList.size()];
                    String[] categories = new String[HousesList.size()];

                    for (int i = 0; i < houses.length; i++) {
                        houses[i] = HousesList.get(i).getName();
                    }

                    for (int i = 0; i < categories.length; i++) {
                        Category category = HousesList.get(i).getCategories().get(0);
                        categories[i] = category.getTitle();
                    }

                    ArrayAdapter adapter
                            = new MyHousesArrayAdapter(Houses.this, android.R.layout.simple_list_item_1, houses, categories);
                    listOfHouses.setAdapter(adapter);


                    showHouses();
                } else {
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<YelpBusinessRenting> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);

                showFailureMessage();
            }

        });
    }

    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showHouses() {
        listOfHouses.setVisibility(View.VISIBLE);
        dispLocationText.setVisibility(View.VISIBLE);
    }
}



//        img = (ImageView) findViewById(R.id.imageViewoflist) ;
//        dispLocationText = (TextView) findViewById(R.id.display_Location_TextView);
//        listOfHouses = (ListView) findViewById(R.id.listView);
//
//
//        CustomerAdapter customerAdapter = new CustomerAdapter();
//        listOfHouses.setAdapter(customerAdapter);
//
//
//        listOfHouses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                Toast.makeText(Houses.this, "My place ", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(),ListdataActivity.class);
//                intent.putExtra("image",IMAGES[position]);
////                intent.putExtra("name",hsesRoad[position]);
//                startActivity(intent);
//
//            }
//
//        });
//        Intent intent = getIntent();
//        String location = intent.getStringExtra("location");
//        dispLocationText.setText("Houses available at " + location);
//
//    }

//     class CustomerAdapter extends BaseAdapter{
//
//         @Override
//         public int getCount() {
//          return IMAGES.length;
//         }
//
//         @Override
//         public Object getItem(int position) {
//             return null;
//         }
//
//         @Override
//         public long getItemId(int position) {
//             return 0;
//         }
//
//         @Override
//         public View getView(int i, View convertView, ViewGroup parent) {
//             convertView = getLayoutInflater().inflate(R.layout.customlayout,null);
//
//             ImageView img = (ImageView)convertView.findViewById(R.id.imageViewoflist);
//             TextView textView_Road = (TextView)convertView.findViewById(R.id.textView_Road);
//             TextView textView_descr = (TextView)convertView.findViewById(R.id.textView_descrp);
//             img.setImageResource(IMAGES[i]);
//
////             iimm.setImageResource(IMAGES[i]);
////             textView_Road.setText(hsesRoad[i]);
////             textView_descr.setText(hsesRoad[i]);
//
//             return convertView;
//         }
//     }
//}

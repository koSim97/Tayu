package com.example.tayu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


public class Fragment4 extends Fragment {

    ViewGroup viewGroup;
    EditText keywordView;
    ListView listView;
    ArrayAdapter<POI> mAdapter;
    private Context mContext = null;
    Editable stPoint=null;
    double stX;
    double stY;
    long posi;
    POI poi1;
    private TMapView tmapview = null;
    TextView wtemp,whumi,wtemax,wtemin;
    ImageView wtIcon;
    String icon;
    private BlankFragment.OnDataSetListener onDataSetListener;
    Handler handler;

    public Fragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_4,container,false);
        mContext = container.getContext();

        keywordView = (EditText) viewGroup.findViewById(R.id.edit_keyword);
        listView = (ListView) viewGroup.findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<POI>(mContext, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);
        tmapview=new TMapView(mContext);
        tmapview.setSKTMapApiKey("l7xx71cfd335915b4f6792efa7e150a510b4"); //api키 세팅
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        wtemp=(TextView)viewGroup.findViewById(R.id.wttemp);
        whumi=(TextView)viewGroup.findViewById(R.id.wthu);
        wtemax=(TextView)viewGroup.findViewById(R.id.wttmax);
        wtemin=(TextView)viewGroup.findViewById(R.id.wttmin);
        wtIcon=(ImageView)viewGroup.findViewById(R.id.wticon);

        Button btn = (Button) viewGroup.findViewById(R.id.btn_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stPoint = keywordView.getText();
                searchPOI();
            }
        });
        btn = (Button) viewGroup.findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listView.setVisibility(View.VISIBLE);
                searchPOI();

            }
        });
        btn = (Button)viewGroup.findViewById(R.id.btn_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    //시작지점과 종료 지점중 하나라도 널 값이 있다면 토스트 메시지 출력하도록 설정
                //onDataSetListener.onDataSet(""+stPoint,""+stX,""+stY,""+edPoint,""+edX,""+edY);
                weatherRun(stX,stY);
                listView.setVisibility(View.INVISIBLE);

            }
        });
        return viewGroup;
    }
    private void searchPOI() {
        TMapData data = new TMapData();
        final String keyword = keywordView.getText().toString();
        listView.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(keyword)) {
            data.findAllPOI(keyword, new TMapData.FindAllPOIListenerCallback() {
                @Override
                public void onFindAllPOI(final ArrayList<TMapPOIItem> arrayList) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.clear();

                            for (TMapPOIItem poi : arrayList) {
                                mAdapter.add(new POI(poi));
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                        poi1 = (POI) listView.getItemAtPosition(position);
                                        keywordView.setText(""+poi1);
                                        posi=listView.getItemIdAtPosition(position);
                                        Toast.makeText(mContext,""+posi+poi1,Toast.LENGTH_SHORT).show();
                                        TMapPOIItem poi = arrayList.get((int)posi);
                                        stX=poi.getPOIPoint().getLatitude();
                                        stY=poi.getPOIPoint().getLongitude();
                                        Toast.makeText(mContext, "x:" +stX+"y:"+stY, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
    }
    public void weatherRun(double x,double y){
        Retrofit client=new Retrofit.Builder().baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface service=client.create(ApiInterface.class);
        Call<WeatherTest> call=service.getWeather("b0ea95fa326c0d43e5c822aff3c99947",Double.valueOf(stX),Double.valueOf(stY));
        call.enqueue(new Callback<WeatherTest>() {
            @Override
            public void onResponse(Call<WeatherTest> call, Response<WeatherTest> response) {
                if(response.isSuccessful()){
                    WeatherTest repo=response.body();
                    List<Weather>weathers=repo.getWeather();
                    icon=weathers.get(0).getIcon();
                    wtemp.setText("현재 온도 : "+Math.round(repo.getMain().getTemp()-273.15)*10/10.0);
                    whumi.setText("  습 도  : "+repo.getMain().getHumidity());
                    wtemax.setText("최고 온도 : "+Math.round(repo.getMain().getTempMax()-273.15)*10/10.0);
                    wtemin.setText("최저 온도 : "+Math.round(repo.getMain().getTempMin()-273.15)*10/10.0);
                    handler=new Handler();
                    Thread t=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL url=new URL("http://openweathermap.org/img/w/"+icon+".png");
                                InputStream is=url.openStream();
                                final Bitmap bitmap=BitmapFactory.decodeStream(is);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        wtIcon.setImageBitmap(bitmap);
                                    }
                                });
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    t.start();
                }else{
                }
            }
            @Override
            public void onFailure(Call<WeatherTest> call, Throwable t) {
            }
        });

    }
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof BlankFragment.OnDataSetListener){
            onDataSetListener=(BlankFragment.OnDataSetListener) context;
        }else{
            throw new RuntimeException(context.toString()
                    + "must implement");
        }
    }
    public void onDetach(){
        super.onDetach();
        onDataSetListener=null;
    }
}
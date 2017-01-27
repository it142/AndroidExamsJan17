package com.example.user.androidexamsjan17;

import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by user on 27/1/2017.
 */

public class MerchantFragment extends Fragment{

    MerchantAdapter merchantAdapter;

    public MerchantFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchMerchants();
    }

    private void fetchMerchants(){
        FetchMerchantsTask fetchMerchantsTask = new FetchMerchantsTask(merchantAdapter);
        fetchMerchantsTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        merchantAdapter = new MerchantAdapter(getActivity(),new ArrayList<Merchant>());
        ListView merchantListView = (ListView)rootView.findViewById(R.id.listview_merchants);
        merchantListView.setAdapter(merchantAdapter);

        return rootView;
    }

    public class FetchMerchantsTask extends AsyncTask<String,Void,ArrayList<Merchant>> {

        private final String LOG_TAG = FetchMerchantsTask.class.getSimpleName();
        private MerchantAdapter merchantAdapter;
        public static final String YUMMY_BASE_DOMAIN = "http://dev.savecash.gr:3000";

        public FetchMerchantsTask( MerchantAdapter merchantAdapter){
            this.merchantAdapter = merchantAdapter;
        }

        private ArrayList<Merchant> getMerchantsFromJson(String merchantJsonStr) throws JSONException {
            ArrayList<Merchant> merchants = new ArrayList<>();
            try{
                Log.i("Erros", merchants.toString());
                JSONArray merchantsArray = new JSONArray(merchantJsonStr);
                //.....





                //......
                Log.d(LOG_TAG, "Merchant Fetching Complete. " + merchants.size() + "merchants inserted");
                return  merchants;
            }catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
                return  merchants;
            }
        }
        @Override
        protected ArrayList<Merchant> doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String merchantJsonStr = null;

            try {
                final String YUMMY_MERCHANTS_URL = "http://dev.savecash:3000/Merchant/index.json?$orderby=id%20desc";

                Uri builtUri = Uri.parse(YUMMY_BASE_DOMAIN+YUMMY_MERCHANTS_URL);


                URL url = new URL(builtUri.toString());

                // Create the request to Yummy Wallet server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                merchantJsonStr = buffer.toString();
                Log.e("Erros", merchantJsonStr);
                return  getMerchantsFromJson(merchantJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Merchant> merchants) {
            if(merchants.size() > 0){
                this.merchantAdapter.clear();
                //.....
            }
        }
    }

}

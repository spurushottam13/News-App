package app1.com.example.purushottam.e_news;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<reviews> reviewList;

    reviewsadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reviewList = new ArrayList<reviews>();
        new JSONAsyncTask().execute("https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=f68c061d9afb40aba19c8928b95d1226");

        final ListView listview = (ListView) findViewById(R.id.list_item);
        adapter = new reviewsadapter(getApplicationContext(), R.layout.row, reviewList);

        listview.setAdapter(adapter);



    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//for displaying progress bar
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
//establishing http connection
                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();
                // if connected then access data
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("articles");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        reviews rev = new reviews();
                        //getting json object values from json array
                        rev.setTitle(object.getString("title"));
                        rev.setDescription(object.getString("description"));
                        rev.setAuthor(object.getString("publishedAt"));
                        //getting value within json object




                        //adding data to the arraylist
                        reviewList.add(rev);

                    }
                    return true;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            // if data dint fetch from the url
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }

}


package com.example.empdb_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText e1, e2, e3, e4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void findbtn(View v) {

        e1 = (EditText) findViewById(R.id.t1);
        e2 = (EditText) findViewById(R.id.t2);
        e3 = (EditText) findViewById(R.id.t3);
        e4 = (EditText) findViewById(R.id.t4);
        if (e1.getText().toString() == "" || e1.getText().length() == 0)
            return;
        else {
            class CData extends AsyncTask<String, String, String> {

                @Override
                protected String doInBackground(String... arg0) {
                    try {

                        String link = "http://172.16.2.30/test.php?eno=" + e1.getText().toString();

                        URL url = new URL(link);
                        HttpClient client = new DefaultHttpClient();
                        HttpGet request = new HttpGet();
                        request.setURI(new URI(link));
                        HttpResponse response = client.execute(request);

                        BufferedReader in = new BufferedReader(new
                                InputStreamReader(response.getEntity().getContent()));

                        StringBuffer sb = new StringBuffer("");
                        String line = "";

                        while ((line = in.readLine()) != null) {
                            sb.append(line);
                            break;
                        }
                        in.close();

                        return sb.toString();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "error" + e.getMessage(), Toast.LENGTH_LONG).show();
                        return new String("Exception: " + e.getMessage());
                    }
                }

                protected void onPreExecute() {
                    super.onPreExecute();
                }

                protected void onPostExecute(String result) {
                    try {
                        JSONObject jobj = new JSONObject(result);
                        e1.setText(jobj.getString("eno"));
                        e2.setText(jobj.getString("ename"));
                        e3.setText(jobj.getString("esal"));
                    } catch (Exception ee) {
                        Toast.makeText(getApplicationContext(), "error2" + ee.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            CData obj = new CData();
            obj.execute();
        }
    }

    public void findAll(View view) {

        class CData extends AsyncTask<String, String, String> {

            @Override
            protected String doInBackground(String... arg0) {
// TODO Auto-generated method stub
                try {

                    String link = "http://172.16.2.30/showall.php?eno=1";

                    URL url = new URL(link);
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(link));
                    HttpResponse response = client.execute(request);

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(response.getEntity().getContent()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    in.close();

                    return sb.toString();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "error" + e.getMessage(), 2).show();
                    return new String("Exception: " + e.getMessage());
                }
            }

            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected void onPostExecute(String result) {
                String str = "";
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    if (jsonArray != null) {
                        int i = 0;
                        while (i < jsonArray.length()) {
                            JSONObject jobj = jsonArray.getJSONObject(i);
                            str = str + jobj.getString("eno");
                            str = str + "   ";
                            str = str + jobj.getString("ename");
                            str = str + "\n";
                            i++;
                        }
                        e4.setText(str);
                    }
                } catch (Exception ee) {
                    Toast.makeText(getApplicationContext(), "error2" + ee.getMessage(), 2).show();
                }
            }
        }
        CData obj = new CData();
        obj.execute();
    }
}
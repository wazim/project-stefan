package net.wazim.project_stefan;

import android.os.AsyncTask;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HtmlDoer extends AsyncTask<String, Void, String> {

    private final MainActivity activity;

    public HtmlDoer(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... urlToRequest) {
        RestTemplate httpClient = new RestTemplate();
        ResponseEntity<String> response = httpClient.getForEntity(urlToRequest[0], String.class);
        return response.getBody();
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        activity.speakVolumes(response);
    }
}

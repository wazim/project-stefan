package net.wazim.project_stefan;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.JsonReader;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.TableRow.LayoutParams;


public class MainActivity extends Activity {

    public static final String COLOR = "#ffffff";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            makeHttpRequest();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void makeHttpRequest() throws InterruptedException, ExecutionException {
        new HtmlDoer(this).execute("http://project-jordan.herokuapp.com/jordan/api/all?format=json");
    }

    public void speakVolumes(String volume) {
        try {
            JSONArray array = new JSONArray(volume);

            for (int i = 0; i < array.length(); i++) {
                JSONObject bluRay = new JSONObject(array.get(i).toString());
                TableLayout table = (TableLayout) findViewById(R.id.MainTable);

                TableRow row = new TableRow(this);
                row.setLayoutParams(new LayoutParams(MATCH_PARENT, WRAP_CONTENT));

                TextView name = new TextView(this);
                name.setText(createLinkedText(bluRay));
                name.setMovementMethod(LinkMovementMethod.getInstance());
                name.setLayoutParams(new LayoutParams(MATCH_PARENT, WRAP_CONTENT));
                name.setTextColor(Color.parseColor(COLOR));
                row.addView(name);

                TextView newPrice = new TextView(this);
                newPrice.setText("£"+bluRay.getDouble("newPrice"));
                newPrice.setLayoutParams(new LayoutParams(MATCH_PARENT, WRAP_CONTENT));
                newPrice.setTextColor(Color.parseColor(COLOR));
                row.addView(newPrice);

                TextView usedPrice = new TextView(this);
                usedPrice.setText("£"+bluRay.getDouble("usedPrice"));
                usedPrice.setLayoutParams(new LayoutParams(MATCH_PARENT, WRAP_CONTENT));
                usedPrice.setTextColor(Color.parseColor(COLOR));
                row.addView(usedPrice);

                table.addView(row, new TableLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static android.text.Spanned createLinkedText(JSONObject bluray) throws JSONException {
        String appropriateName;
        if(bluray.get("name").toString().length() > 15) {
            appropriateName = bluray.get("name").toString().substring(0, 15) + "...";
        } else {
            appropriateName = bluray.get("name").toString();
        }
        return Html.fromHtml("<a href=\""+bluray.get("url").toString()+"\">" + appropriateName + "<\\a>");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

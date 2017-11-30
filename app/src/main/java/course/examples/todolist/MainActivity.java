package course.examples.todolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listview;
    SimpleAdapter adapter;
    Button button;
    EditText inputText1, inputText2;
    String datafile= "datafile";
    TextView head, title, content;
    private List<Map<String, String>> listItems = new ArrayList<Map<String, String>>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listItems = new ArrayList<Map<String, String>>();
        adapter = new SimpleAdapter(this, listItems, R.layout.item, new String[]{"title", "content"}, new int[]{R.id.title, R.id.content});
        listview = (ListView) findViewById(R.id.mylist);
        listview.setAdapter(adapter);
        content = (TextView) findViewById(R.id.content);
        title = (TextView) findViewById(R.id.title);
        button = (Button) findViewById(R.id.button);
        head = (TextView) findViewById(R.id.textView);
        inputText1 = (EditText) findViewById(R.id.editText);
        inputText2 = (EditText) findViewById(R.id.editText2);

        OpenTheFile();

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                listItems.remove(i);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    public void onclick(View v) {

        if((inputText1.getText().toString().equals(""))&&(inputText2.getText().toString().equals(""))) {
            Toast.makeText(this, "Please input your task!!!", Toast.LENGTH_SHORT).show();}
        else {
            Toast.makeText(this,"You add a new task!",Toast.LENGTH_SHORT).show();
            FileOutputStream outputStream;
            try {

                outputStream = openFileOutput(datafile, Context.MODE_APPEND);
                outputStream.write((inputText1.getText().toString() + " \n").getBytes());
                outputStream.write((inputText2.getText().toString() + " ,").getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            add();
            listview.setAdapter(adapter);}
    }

    public void add(){
        String A = inputText1.getText().toString();
        String B = inputText2.getText().toString();
        FileInputStream inputStream;

        try {
            inputStream = openFileInput(datafile);
            int c;
            String temp = "";
            while ((c = inputStream.read()) != -1) {
                temp = temp + Character.toString((char) c);

            }
            Map<String, String> listItem2 = new HashMap<String, String>();
            listItem2.put("title", A);
            listItem2.put("content",B);
            listItems.add(listItem2);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void OpenTheFile(){
        FileInputStream inputStream;

        try {
            inputStream = openFileInput(datafile);
            int c;
            String temp = "";
            while ((c = inputStream.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }
            String[] str = temp.split("\n|,");

            for (int i=2;i<=str.length;i=i+2) {
                Map<String, String> listItem2 = new HashMap<String, String>();
                listItem2.put("title", str[i-2]);
                listItem2.put("content", str[i-1]);
                listItems.add(listItem2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


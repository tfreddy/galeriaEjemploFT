package cr.ac.unadeca.galeriaejemploft.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cr.ac.unadeca.galeriaejemploft.R;
import cr.ac.unadeca.galeriaejemploft.subclases.RunImage;
import cr.ac.unadeca.galeriaejemploft.util.ImageAdapter;

/**
 * Created by Freddy on 3/19/2018.
 */

public class FragmentsRandomImage extends Fragment {
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.BASELINE);
        layoutManager.setJustifyContent(JustifyContent.CENTER);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        setHasOptionsMenu(true);
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public  void setupRecyclerView(){

       new tarea().execute();
    }


    private class tarea extends AsyncTask<Void,Void, List<RunImage>>
    {
        @Override
        protected void onPostExecute(List<RunImage> runImages) {
            super.onPostExecute(runImages);
            ImageAdapter adapter = new ImageAdapter(runImages, getActivity());
            recyclerView.setAdapter(adapter);
        }

        @Override
        protected List<RunImage> doInBackground(Void... voids) {
            List<RunImage> images= new ArrayList<>();
            try{
                URL obj = new URL("https://picsum.photos/list");
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                System.out.println("GET Response Code :: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) { // success
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // print result
                    System.out.println(response.toString());

                    JSONArray array = new JSONArray(response.toString());
                    RunImage image;
                    JSONObject object;
                    for(int a=0; a < array.length() ;a++){
                        object = array.getJSONObject(a);
                        image = new RunImage();
                        image.author = object.getString("author");
                        image.name = object.getString("filename");
                        image.url = "https://picsum.photos/500/500?image="+object.getInt("id");
                        images.add(image);
                    }
                } else {
                    System.out.println("GET request not worked");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return images;
        }
    }
}

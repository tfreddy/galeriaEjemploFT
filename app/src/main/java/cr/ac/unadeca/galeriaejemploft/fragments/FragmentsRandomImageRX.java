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
import java.util.concurrent.Callable;


import cr.ac.unadeca.galeriaejemploft.R;
import cr.ac.unadeca.galeriaejemploft.subclases.RunImage;
import cr.ac.unadeca.galeriaejemploft.util.ImageAdapter;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Freddy on 3/19/2018.
 */

public class FragmentsRandomImageRX extends Fragment {
    private final CompositeDisposable disposables = new CompositeDisposable();

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.BASELINE);
        layoutManager.setJustifyContent(JustifyContent.CENTER);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        setHasOptionsMenu(true);
        return rootView;//retorno de vista

    }//cracion de vista

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();//instala el recycle view, llama a la tarea asincrona
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setupRecyclerView() {
        cargarImagenes();

    }
//esto es el observador del proceso
    void cargarImagenes() {
        disposables.add(sampleObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<RunImage>>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<RunImage> images) {
                        ImageAdapter adapter = new ImageAdapter(images, getActivity());
                        recyclerView.setAdapter(adapter);
                    }
                }));
    }
    //proceso a observar

    static Observable<List<RunImage>> sampleObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends List<RunImage>>>() {
            @Override public ObservableSource<? extends List<RunImage>> call() throws Exception {
                // Do some long running operation
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
                return  Observable.just(images);

            }
        });
    }





}


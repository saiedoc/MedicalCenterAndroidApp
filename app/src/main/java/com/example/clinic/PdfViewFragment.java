package com.example.clinic;
import ViewModels.*;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.common.util.JsonUtils;
import com.shockwave.pdfium.PdfDocument;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.Base64;
import java.util.List;

import data.ApiInterface;
import data.ClientByteApi;
import data.ClientJsonApi;


import okhttp3.ResponseBody;
import pojo.Attachment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

public class PdfViewFragment extends Fragment implements OnPageChangeListener,OnLoadCompleteListener {
    private static final String TAG = PdfViewFragment.class.getSimpleName();
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    String a_name;
    DownloadViewModel downloadViewModel = DownloadViewModel.getINSTANCE();


    public void writeAttachmentExternal(String a_name) {
        System.out.println("salem happy");
        downloadViewModel.getByteMutableLiveData().observe(getActivity(), new Observer<ResponseBody>() {
            @Override
            public void onChanged(ResponseBody bytes) {
                try {
                    Do( bytes.bytes(),a_name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    void Do(byte[] a,String a_name) {
        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/clinicApp";
        try {

            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();

            }
            OutputStream fOut = null;
            File file = new File(fullPath, a_name + ".pdf");
            if (file.exists())
                showPDFfromUri();
            else {
                file.createNewFile();
                fOut = new FileOutputStream(file);
                fOut.write(a);
                fOut.flush();
                fOut.close();
                showPDFfromUri();
              }
            } catch(Exception e){
                Log.e("saveToExternalStorage()", e.getMessage());
            }

    }

    private void findViews() {
        pdfView = (PDFView) getView().findViewById(R.id.pdfView);
        MainActivity.toolbar.setTitle("PDF View Data");
    }

    private void getData(int a_id) {
        downloadViewModel.getFile(a_id);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        findViews();

        int a_id = ((MainActivity) getActivity()).getCurrentAttachmentID();
        a_name = ((MainActivity) getActivity()).getCurrentAttachmentName();
        pdfFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/clinicApp/" + a_name + ".pdf";
        if(new File(pdfFileName).exists()) showPDFfromUri();
        else {
            getData(a_id);
            writeAttachmentExternal(a_name);
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pdf_viewer, container, false);
    }


    private void showPDFfromUri() {
        pdfView.fromFile(new File(pdfFileName))
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(getContext()))
                .load();
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        //getActivity().setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

}
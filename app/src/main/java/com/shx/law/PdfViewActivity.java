package com.shx.law;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;

import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.shx.law.base.BaseActivity;
import com.shx.law.libs.dialog.DialogManager;
import com.shx.law.view.PDFView;

import java.io.File;

import static com.shx.law.R.id.pdfView;

public class PdfViewActivity extends BaseActivity implements OnPageChangeListener
        ,OnLoadCompleteListener, OnDrawListener {
    private PDFView mView;
    private String mUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        mView = (PDFView) findViewById(pdfView);
        mView.setFitsSystemWindows(true);
        mView.setMinZoom(1.0f);
        mView.setMidZoom(1.5f);
        mView.setMaxZoom(2f);

        mUrl=getIntent().getStringExtra("URL");
        int index=mUrl.lastIndexOf("/");
        String fileName=mUrl.substring(index);
        DialogManager.getInstance().showProgressDialogNotCancelbale(this);
        mView.fromUrl(mUrl,fileName, new PDFView.FileLoadingListener() {
            @Override
            public void onFileLoaded(File file) {
                Log.d("PDFView","PDF File 加载完成======");
                mView.fromFile(file)
                        .enableSwipe(true) // allows to block changing pages using swipe
                        .defaultPage(0)
                        // allows to draw something on the current page, usually visible in the middle of the screen
                        .onDraw(PdfViewActivity.this)
                        // allows to draw something on all pages, separately for every page. Called only for visible pages
                        .onLoad(PdfViewActivity.this) // called after document is loaded and starts to be rendered
                        .onPageChange(PdfViewActivity.this)
                        .swipeHorizontal(false)
                        .enableAntialiasing(true)
                        .load();
            }
        });
    }


    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
    }

    @Override
    public void loadComplete(int nbPages) {
        DialogManager.getInstance().dissMissProgressDialog();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }
}

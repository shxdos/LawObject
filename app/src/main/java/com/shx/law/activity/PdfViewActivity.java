package com.shx.law.activity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.shx.law.R;
import com.shx.law.base.BaseActivity;
import com.shx.law.common.LogGloble;
import com.shx.law.libs.dialog.DialogManager;
import com.shx.law.view.PDFView;

import java.io.File;

import static com.shx.law.R.id.pdfView;

public class PdfViewActivity extends BaseActivity implements OnPageChangeListener
        , OnLoadCompleteListener, OnDrawListener, OnErrorListener {
    private PDFView mView;
    private String mUrl;
    private boolean isLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        mView = (PDFView) findViewById(pdfView);
        mView.setFitsSystemWindows(true);
        mView.setMinZoom(1.0f);
        mView.setMidZoom(1.5f);
        mView.setMaxZoom(2f);

        mUrl = getIntent().getStringExtra("URL");
        int index = mUrl.lastIndexOf("/");
        String fileName = mUrl.substring(index);
        DialogManager.getInstance().showProgressDialogNotCancelbale(this);
        mView.fromUrl(mUrl, fileName, new PDFView.FileLoadingListener() {
            @Override
            public void onFileLoaded(File file) {
                isLoaded = true;
                Log.d("PDFView", "PDF File 加载完成======");
                mView.fromFile(file)
                        .enableSwipe(true) // allows to block changing pages using swipe
                        .defaultPage(0)
                        .onError(PdfViewActivity.this)
                        // allows to draw something on the current page, usually visible in the middle of the screen
                        .onDraw(PdfViewActivity.this)
                        // allows to draw something on all pages, separately for every page. Called only for visible pages
                        .onLoad(PdfViewActivity.this) // called after document is loaded and starts to be rendered
                        .onPageChange(PdfViewActivity.this)
                        .swipeHorizontal(false)
                        .enableAntialiasing(true)
                        .load();
            }

            @Override
            public void onFileLoadFail() {
                isLoaded = false;
                DialogManager.getInstance().dissMissProgressDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!isLoaded) {
            //文件没下载完成并退出该页面
            mView.cancleDownload();
            int index = mUrl.lastIndexOf("/");
            String fileName = mUrl.substring(index);
            final String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pdf/";
            final File file = new File(SDPath, fileName);
            if (file.exists()) {
                //文件存在
                file.delete();
            }
        }
        super.onBackPressed();
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

    @Override
    public void onError(Throwable t) {
        LogGloble.d("onError", "onError");
    }
}

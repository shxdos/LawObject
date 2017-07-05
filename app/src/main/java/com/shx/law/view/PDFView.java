package com.shx.law.view;

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.widget.Toast;

import com.shx.law.libs.http.HttpManager;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

/**
 * Created by 邵鸿轩 on 2017/7/4.
 */

public class PDFView extends com.github.barteksc.pdfviewer.PDFView {
    private Context mContext;
    public interface FileLoadingListener {
        void onFileLoaded(File file);
    }

    /**
     * Construct the initial view
     *
     * @param context
     * @param set
     */
    public PDFView(Context context, AttributeSet set) {
        super(context, set);
        mContext=context;
    }
    public void fromUrl(String url, String fileNmae, final FileLoadingListener listener){
        final String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/pdf/";
        final File file = new File(SDPath, fileNmae);
        if(file.exists()){
          //文件存在
            if(listener!=null){
                listener.onFileLoaded(file);
            }
        }else{
            HttpManager.getInstance().doDownloadFile(url, fileNmae, new FileCallBack(SDPath,fileNmae) {
                @Override
                public void onError(Call call, Exception e, int id) {
                    if(file.exists()){
                        //删除错误文件
                        file.delete();
                    }
                    Toast.makeText(mContext,"文件加载失败！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(File response, int id) {
                    listener.onFileLoaded(response);
                }
            });
        }
    }
}

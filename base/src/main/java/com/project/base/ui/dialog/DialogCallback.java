package com.project.base.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import androidx.annotation.NonNull;
import android.view.Window;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/11/12
 *     desc  :
 * </pre>
 */
public interface DialogCallback {
    @NonNull
    Dialog bindDialog(Activity activity);

    void setWindowStyle(Window window);
}

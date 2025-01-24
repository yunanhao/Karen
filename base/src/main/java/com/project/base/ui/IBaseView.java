package com.project.base.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/11/16
 *     desc  :
 * </pre>
 */
public interface IBaseView {

    void initData(@Nullable Bundle bundle);

    int bindLayout();

    void setContentView();

    void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView);

    void doBusiness();

    void onDebouncingClick(@NonNull View view);
}

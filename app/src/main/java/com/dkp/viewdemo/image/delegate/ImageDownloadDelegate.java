package com.dkp.viewdemo.image.delegate;

import java.io.File;

/**
 * 版权信息(@copyright Copyright 2019 JD.COM All Right Reserved)
 * 与该类相关联类(@see);
 * 作者(@author jiayizhan 部门:校园生态部-校园产研组);
 * 版本(@version V1.0);
 * 创建、开发日期(@date 2019-05-2210:39);
 * 最后修改日期 2019-05-22;
 * 复审人
 */
public interface ImageDownloadDelegate {
    void onSuccess(String path, File file);

    void onFailed(String path);
}

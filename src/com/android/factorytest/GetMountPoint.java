package com.android.factorytest;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class GetMountPoint
{
    private Context context;
    private final static String tag = "GetMountPoint";

    /** 构造方法 */
    private GetMountPoint(Context context)
    {
        this.context = context;
    }

    /** 之所以用这种方法初始化时为了防止使用的时候没有检查SDK版本导致出错 */
    public static GetMountPoint GetMountPointInstance(Context context)
    {
        if (14 <= Build.VERSION.SDK_INT && Build.VERSION.SDK_INT <= 23)
        {
            return new GetMountPoint(context);
        }
        else
        {
            Log.e(tag, "本类不支持当前SDK版本");
            return null;
        }
    }

    /** 核心操作-获取所有挂载点信息。 */
    public List<MountPoint> getMountPoint()
    {
        try
        {
            Class class_StorageManager = StorageManager.class;
            Method method_getVolumeList = class_StorageManager.getMethod("getVolumeList");
            Method method_getVolumeState = class_StorageManager
                    .getMethod("getVolumeState", String.class);
            StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            Class class_StorageVolume = Class.forName("android.os.storage.StorageVolume");
            Method method_isRemovable = class_StorageVolume.getMethod("isRemovable");
            Method method_getPath = class_StorageVolume.getMethod("getPath");
            Method method_getPathFile = null;
            if (Build.VERSION.SDK_INT >= 17)
            {// 自api16一下的版本在StorageVolume方法中没有getPathFile
                method_getPathFile = class_StorageVolume.getMethod("getPathFile");
            }
            Object[] objArray = (Object[]) method_getVolumeList.invoke(sm);

            //region 所有挂载点File---附带是内置存储卡还是外置存储卡的标志
            List<MountPoint> result = new ArrayList<>();
            for (Object value : objArray)
            {
                String path = (String) method_getPath.invoke(value);
                File file;
                if (Build.VERSION.SDK_INT >= 17)
                {
                    file = (File) method_getPathFile.invoke(value);
                }
                else
                {
                    file = new File(path);
                }

                boolean isRemovable = (boolean) method_isRemovable.invoke(value);

                boolean isMounted;
                String getVolumeState = (String) method_getVolumeState.invoke(sm, path);//获取挂载状态。
                isMounted = getVolumeState.equals(Environment.MEDIA_MOUNTED);

                result.add(new MountPoint(file, isRemovable, isMounted));
            }
            return result;
            //endregion
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /** 获取处于挂载状态的挂载点的信息 */
    public List<MountPoint> getMountedPoint()
    {
        List<MountPoint> result = this.getMountPoint();
        for (MountPoint value : result)
        {
            if (!value.isMounted)
            {
                result.remove(value);
            }
        }
        return result;
    }

    public class MountPoint
    {
        private File file;
        /** 用于判断是否为内置存储卡，如果为true就是代表本挂载点可以移除，就是外置存储卡，否则反之 */
        private boolean isRemovable;
        /** 用于标示，这段代码执行的时候这个出处卡是否处于挂载状态，如果是为true，否则反之 */
        private boolean isMounted;

        public MountPoint(File file, boolean isRemovable, boolean isMounted)
        {
            this.file = file;
            this.isMounted = isMounted;
            this.isRemovable = isRemovable;
        }

        public File getFile()
        {
            return file;
        }

        public boolean isRemovable()
        {
            return isRemovable;
        }

        public boolean isMounted()
        {
            return isMounted;
        }
    }
}


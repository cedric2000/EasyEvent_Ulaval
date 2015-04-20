package ca.easyevent.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Cédric on 02/04/2015.
 */
public class ImageManager {

    /*##############################################################################################
                                   ATTRIBUTS
   ###############################################################################################*/

    private Context context;
    private double targetWidth;
    private double targetHeight;

    /*##############################################################################################
                                   CONSTRUCTEUR
   ###############################################################################################*/

    public ImageManager(Context context, double targetWidth) {
        this.context = context;
        this.targetWidth = targetWidth;
    }


    /*##############################################################################################
                                        Acceseur
    ###############################################################################################*/

    public Drawable getResizeImage (int imageID) {

        //Get device dimensions
        BitmapDrawable bd=(BitmapDrawable) context.getResources().getDrawable(imageID);
        double imageHeight = bd.getBitmap().getHeight();
        double imageWidth = bd.getBitmap().getWidth();

        double ratio = targetWidth / imageWidth;
        int newImageHeight = (int) (imageHeight * ratio);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), imageID, options);
        options.inSampleSize = calculateInSampleSize(options,(int) targetWidth, newImageHeight);
        Drawable drawable = new BitmapDrawable(context.getResources(),
                this.getResizedBitmap(image, (int) targetWidth, newImageHeight));

        return drawable;
    }

    public Drawable getResizeImage (Bitmap image, BitmapFactory.Options options) {
        //Get device dimensions

        double imageHeight = image.getHeight();
        double imageWidth = image.getWidth();

        double ratio = targetWidth / imageWidth;
        int newImageHeight = (int) (imageHeight * ratio);

        options.inSampleSize = calculateInSampleSize(options,(int) targetWidth, newImageHeight);
        Drawable drawable = new BitmapDrawable(context.getResources(),
                this.getResizedBitmap(image, newImageHeight, (int) targetWidth));

        return drawable;
    }

    /*##############################################################################################
                                        FORMATER IMAGE
    ###############################################################################################*/

    //Resize Bitmap
    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }

    private int calculateInSampleSize(  BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /*##############################################################################################
                                    FICHIER IMAGE
    ###############################################################################################*/

    public static String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    /*##############################################################################################
                                CONTROL CAMERA
    ###############################################################################################*/

    private boolean isDeviceSupportCamera(Context context) {
        if (context.getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
}

package ch.stv_arch.stv_arch.Utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

//Easy way to apply font style more textview:

//Create Utils class in your package

//Put font file in assets

public class Utils {

    private static String font1 = "fonts/Brush Script Bold.ttf"; //oder definieren in strings
    public static Typeface fontsStyle;

    public static void TypeFace(TextView tv, AssetManager asm){

        fontsStyle=Typeface.createFromAsset(asm, font1 );
        tv.setTypeface(fontsStyle);
    }
}

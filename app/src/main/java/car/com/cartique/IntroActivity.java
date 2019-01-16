package car.com.cartique;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;
import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;
import car.com.cartique.app.Config;
import car.com.cartique.utility.Utility;

public class IntroActivity extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(R.mipmap.ic_menu_fistbum)
                .title("Welcome")
                .description("Check out the features")
                .build());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorAccent)
                        .buttonsColor(R.color.btn_login)
                        .image(R.mipmap.ic_menu_repair_circular)
                        .title("Service")
                        .description("Book a service or a repair instantly")
                        .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.btn_login)
                .buttonsColor(R.color.colorOrange)
                .image(R.mipmap.ic_menu_paint_circular)
                .title("Body Work")
                .description("Get quotations for scraches and dents within minutes")
                .build());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorOrange)
                        .buttonsColor(R.color.colorPrimaryPix)
                       .image(R.mipmap.ic_menu_track)
                        .title("Real Time Tracking")
                        .description("Keep track of any work that's being done instantly")
                        .build());
        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimaryPix)
                .buttonsColor(R.color.colorAccent)
                .image(R.mipmap.ic_menu_map)
                .title("Location")
                .description("Check out a list of service provider's from all over")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorAccent)
                .buttonsColor(R.color.btn_login)
                .image(R.mipmap.ic_menu_smile)
                .title("That's it")
                .description("Now enjoy the app")
                .build());
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Utility.saveSharedSetting(IntroActivity.this, Config.PREF_USER_FIRST_TIME, "false");
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}

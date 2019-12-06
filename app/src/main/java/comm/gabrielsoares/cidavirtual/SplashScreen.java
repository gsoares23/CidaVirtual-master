package comm.gabrielsoares.cidavirtual;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import comm.gabrielsoares.cidavirtual.R;


public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TextView tv = findViewById(R.id.tv);
        ImageView iv = findViewById(R.id.iv);
        Animation minhaAnim = AnimationUtils.loadAnimation(this, R.anim.minhatransicao);
        tv.startAnimation(minhaAnim);
        iv.startAnimation(minhaAnim);
        final Intent i = new Intent(this, TelaLoginTutores.class);
        Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
                timer.start();




    }
}

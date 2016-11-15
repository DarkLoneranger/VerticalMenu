package com.example.verticalmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


import com.example.verticalmenu.view.VerticalSatelliteMenu;

/**
 * Created by ZHAO on 2016/11/11.
 */
public class MainActivity extends AppCompatActivity {

    private ImageView iv_image;
    private VerticalSatelliteMenu verticalSatelliteMenu;
    boolean isOpen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        iv_image = (ImageView) findViewById(R.id.iv_image);
        verticalSatelliteMenu = (VerticalSatelliteMenu) findViewById(R.id.view);

        //设置一个开关控制菜单的开关
        //set a view to switch the menu
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! isOpen) {
                    verticalSatelliteMenu.open();
                }else{
                    verticalSatelliteMenu.close();
                }
                isOpen=!isOpen;
            }
        });

        //为菜单的子条目设置点击事件
        //set ClickListener for the VerticalMenuItem
        verticalSatelliteMenu.setOnVerticalMenuItemClickListener(new VerticalSatelliteMenu.onVerticalMenuItemClickListener() {
            @Override
            public void onItemClick(int number) {
                ToastUtil.showToast(MainActivity.this, number+"号被点击了");
            }
        });
    }


}

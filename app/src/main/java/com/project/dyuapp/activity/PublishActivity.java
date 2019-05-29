package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.project.dyuapp.R;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.ScreenManager;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.project.dyuapp.myutils.PublicStaticData.ID_BAIT;
import static com.project.dyuapp.myutils.PublicStaticData.ID_FISH_TOOL;
import static com.project.dyuapp.myutils.PublicStaticData.ID_LUYA;
import static com.project.dyuapp.myutils.PublicStaticData.ID_SKILL;
import static com.project.dyuapp.myutils.PublicStaticData.ID_TALk;

/**
 * @describe：发布
 * @author：刘晓丽
 * @createdate：2017/8/18 11:00
 */

public class PublishActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.publish_ll_yuleduanpian,
            R.id.publish_ll_shaiyuju,
            R.id.publish_ll_fayuhuo,
            R.id.publish_ll_zatan,
            R.id.publish_ll_jiqiao,
            R.id.publish_ll_erliao,
            R.id.publish_ll_luya,
            R.id.publish_ll_add_tool,
            R.id.publish_ll_add_zone,
            R.id.publish_iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //帖子顶级分类（52渔获战报、53问答、54渔具DIY、55路亚海钓、56鱼饵配方、57杂谈、58技巧）
            case R.id.publish_ll_yuleduanpian:
                //投稿视频
                startActivity(new Intent(this, CommenWebviewActivity.class)
                        .putExtra("title", "投稿视频")
                        .putExtra("url", HttpUrl.URL+"?m=Home&c=Index&a=hfive&content_id=9"));
                break;
            case R.id.publish_ll_shaiyuju:
                //晒渔具
                startActivity(new Intent(this, PublishPostActivity.class)
                        .putExtra("title", "晒渔具")
                        .putExtra("topId",ID_FISH_TOOL ));
                break;
            case R.id.publish_ll_fayuhuo:
                //发渔获
                startActivity(new Intent(this, PublishGetActivity.class));
                break;
            case R.id.publish_ll_zatan:
                //钓鱼杂谈
                startActivity(new Intent(this, PublishPostActivity.class)
                        .putExtra("title", "钓鱼杂谈")
                        .putExtra("topId",ID_TALk ));
                break;
            case R.id.publish_ll_jiqiao:
                //投稿技巧
                startActivity(new Intent(this, PublishPostActivity.class)
                        .putExtra("title", "技巧")
                        .putExtra("classify", "技巧分类")
                        .putExtra("topId",ID_SKILL ));
                break;
            case R.id.publish_ll_erliao:
                //投稿饵料
                startActivity(new Intent(this, PublishPostActivity.class)
                        .putExtra("title", "饵料")
                        .putExtra("classify", "饵料分类")
                        .putExtra("topId",ID_BAIT ));
                break;
            case R.id.publish_ll_luya:
                //路亚海钓
                startActivity(new Intent(this, PublishPostActivity.class)
                        .putExtra("title", "路亚海钓")
                        .putExtra("topId",ID_LUYA ));
                break;
            case R.id.publish_ll_add_tool:
                //添加渔具店
                startActivity(new Intent(PublishActivity.this, AppendFishingShopActivity.class));
                break;
            case R.id.publish_ll_add_zone:
                //添加钓场
                startActivity(new Intent(PublishActivity.this, AppendFishingPlaceActivity.class));
                break;
            case R.id.publish_iv_close:
                //关闭
                ScreenManager.getInstance().removeActivity(PublishActivity.this);
                overridePendingTransition(0, R.anim.anim_out);
                break;
        }
    }
}

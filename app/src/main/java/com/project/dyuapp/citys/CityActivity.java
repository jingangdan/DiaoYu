package com.project.dyuapp.citys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.citychange.CityChangeActivity;
import com.project.dyuapp.entity.LivingPlaceBean;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.project.dyuapp.myutils.PublicStaticData.CITY_CHANGE_CODE;

@SuppressLint("Registered")
public class CityActivity extends MyBaseActivity {
    @Bind(R.id.filter_edit)
    ClearEditText filterEdit;
    @Bind(R.id.lin_city_change)
    LinearLayout linCityChange;
    @Bind(R.id.country_lvcountry)
    ListView sortListView;
    @Bind(R.id.dialog)
    TextView dialog;
    @Bind(R.id.sidrbar)
    SideBar sideBar;

    private SortAdapter adapter;
    private ClearEditText mClearEditText;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private List<LivingPlaceBean.CBean> cList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("切换城市");

        fillInInformationHttp();
        initViews();
    }

    private void initViews() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();

        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
               // Toast.makeText(CityActivity.this, ((LivingPlaceBean.CBean) adapter.getItem(position)).getN(), Toast.LENGTH_SHORT).show();

                setResult(((LivingPlaceBean.CBean) adapter.getItem(position)).getN(), ((LivingPlaceBean.CBean) adapter.getItem(position)).getCid());
            }
        });

        sortListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideJianPan();
                return false;
            }
        });

        // 根据a-z进行排序源数据
        Collections.sort(cList, pinyinComparator);
        adapter = new SortAdapter(this, cList);
        sortListView.setAdapter(adapter);

        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /*获取城市数据*/
    public void fillInInformationHttp() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.COMMON_GETCITYS);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.LivingPlaceBeanList>(this) {
            @Override
            public void onSuccess(NetBean.LivingPlaceBeanList data, int d) {
                super.onSuccess(data, d);

                cList = filledData(data);
                Collections.sort(cList, pinyinComparator);
                adapter.updateListView(cList);
            }

            @Override
            public void onOther(JsonResult<NetBean.LivingPlaceBeanList> data, int d) {
                super.onOther(data, d);
                ToastUtils.getInstance(CityActivity.this).showMessage(data.getMessage());
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<LivingPlaceBean.CBean> filledData(NetBean.LivingPlaceBeanList date) {
        List<LivingPlaceBean.CBean> mSortList = new ArrayList<>();
        LivingPlaceBean.CBean cBean;

        for (int i = 0; i < date.size(); i++) {
            for (int j = 0; j < date.get(i).getC().size(); j++) {
                cBean = new LivingPlaceBean.CBean();
                cBean.setCid(date.get(i).getC().get(j).getCid());
                cBean.setN(date.get(i).getC().get(j).getN());
                //汉字转换成拼音
                String pinyin = characterParser.getSelling(date.get(i).getC().get(j).getN());
                String sortString = pinyin.substring(0, 1).toUpperCase();
                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    cBean.setSortLetters(sortString.toUpperCase());
                } else {
                    cBean.setSortLetters("#");
                }
                mSortList.add(cBean);
            }

        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<LivingPlaceBean.CBean> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = cList;
        } else {
            filterDateList.clear();
            for (LivingPlaceBean.CBean sortModel : cList) {
                String name = sortModel.getN();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    public void setResult(String selCity, String selCityCode) {
        Intent intent = new Intent();
        intent.putExtra("selCity", selCity);
        intent.putExtra("selCityCode", selCityCode);
        setResult(CITY_CHANGE_CODE, intent);
        CityActivity.this.finish();

    }

    public void hideJianPan(){
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

}

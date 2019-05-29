package com.project.dyuapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.SendMessageAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.callback.StateEntity;
import com.project.dyuapp.entity.LetterHistoryEntity;
import com.project.dyuapp.entity.LetterHistoryListEntity;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author gengqiufang
 * @describtion 发私信
 * @created at 2017/12/6 0006
 */
public class SendMessageActivity extends MyBaseActivity {

    @Bind(R.id.plv)
    PullToRefreshListView plv;
    @Bind(R.id.send_message_edt_content)
    EditText edtContent;
    @Bind(R.id.send_message_tv_send_message)
    TextView tvSendMessage;

    private ArrayList<LetterHistoryEntity> mData = new ArrayList<>();
    private ArrayList<LetterHistoryEntity> mDataOld = new ArrayList<>();
    private SendMessageAdapter mAdapter;

    private String toMemberId = "";
    private String strContent = "";
    private int page = 1;
    private int totalPage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_send_message);
        ButterKnife.bind(this);
        String title = "";
        setIvBack();

        if (!TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
            title = getIntent().getStringExtra("title");
            setTvTitle(title);
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("toMemberId"))) {
            toMemberId = getIntent().getStringExtra("toMemberId");
            initPlv();
            okHttpHistoryNum();
        }
    }

    private void initPlv() {
        mAdapter = new SendMessageAdapter(mData, this, toMemberId);
        plv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        plv.setAdapter(mAdapter);
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (page > 1) {
                    page--;
                    okHttpLetterHistory();
                } else {
                    plv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            plv.onRefreshComplete();
                            showMessage("已到顶~");
                        }
                    }, 1000);
                }

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    @OnClick(R.id.send_message_tv_send_message)
    public void onViewClicked() {
        strContent = edtContent.getText().toString();
        if (TextUtils.isEmpty(strContent)) {
            showMessage("内容不能为空");
        } else {
            okHttpSendLetter();
        }
    }

    //历史记录
    public void okHttpLetterHistory() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.letterHistory);
        commonOkhttp.putParams("to_member_id", toMemberId);//聊天对象ID
        commonOkhttp.putParams("page", page + "");//聊天对象ID
        commonOkhttp.putCallback(new MyGenericsCallback<LetterHistoryListEntity>(this) {
            @Override
            public void onSuccess(LetterHistoryListEntity data, int d) {
                super.onSuccess(data, d);
                if (page == totalPage) {
                    //第一次请求
                    mData.clear();
                    mDataOld.clear();
                }
                //历史数据
                mData.clear();
                mData.addAll(data);
                mData.addAll(mDataOld);
                mAdapter.notifyDataSetChanged();

                ArrayList<LetterHistoryEntity> list = new ArrayList<LetterHistoryEntity>();
                list.addAll(data);
                list.addAll(mDataOld);

                mDataOld = list;

                plv.onRefreshComplete();
                edtContent.setText("");
                if (page == totalPage) {
                    plv.getRefreshableView().setSelection(ListView.FOCUS_DOWN);
                }

            }

            @Override
            public void onOther(JsonResult<LetterHistoryListEntity> data, int d) {
                super.onOther(data, d);
                plv.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                plv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }

    //发私信
    public void okHttpSendLetter() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.sendLetter);
        commonOkhttp.putParams("to_member_id", toMemberId);//聊天对象ID
        commonOkhttp.putParams("content", strContent);//聊天内容
        commonOkhttp.putCallback(new MyGenericsCallback<LetterHistoryEntity>(this) {
            @Override
            public void onSuccess(LetterHistoryEntity data, int d) {
                super.onSuccess(data, d);
                mData.add(data);
                mAdapter.notifyDataSetChanged();
                edtContent.setText("");
            }
        });
        commonOkhttp.Execute();
    }

    //发私信历史记录总条数
    public void okHttpHistoryNum() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.historyNum);
        commonOkhttp.putParams("to_member_id", toMemberId);//聊天对象ID
        commonOkhttp.putCallback(new MyGenericsCallback<StateEntity>(this) {
            @Override
            public void onSuccess(StateEntity data, int d) {
                super.onSuccess(data, d);
                totalPage = data.getPagenum();
                page = totalPage;
                okHttpLetterHistory();
            }
        });
        commonOkhttp.Execute();
    }
}

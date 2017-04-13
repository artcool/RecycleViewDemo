package sinwao.com.recycleviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sinwao.com.recycleviewdemo.utils.MyDividerGridItemDecoration;
import sinwao.com.recycleviewdemo.utils.MyDividerItemDecoration;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;
    private List<String> mDatas;
    private TestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mRecycleView = (RecyclerView) findViewById(R.id.rv);
        /**
         * 设置布局管理器
         */
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
//        mRecycleView.setLayoutManager(new GridLayoutManager(this,4));
//        mRecycleView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        mDatas = new ArrayList<>();
        for (int i = 'A';i <= 'z';i++) {
            mDatas.add(""+(char)i);
        }
        adapter = new TestAdapter(this,mDatas);
        /**
         * 设置适配器
         */
        mRecycleView.setAdapter(adapter);

        /**
         * 分割线
         */
        mRecycleView.addItemDecoration(new MyDividerItemDecoration(this,MyDividerItemDecoration.VERTICAL_LIST));

        mRecycleView.setItemAnimator(new DefaultItemAnimator());


        adapter.setOnItemClickListener(new TestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Toast.makeText(MainActivity.this,"dian",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Toast.makeText(MainActivity.this,"hh",Toast.LENGTH_SHORT).show();
            }
        });



    }

    /**
     * 创建适配器
     */

}

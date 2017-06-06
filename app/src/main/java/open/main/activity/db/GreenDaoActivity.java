package open.main.activity.db;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import open.main.R;
import open.main.base.BaseActivity;
import open.main.bean.User;
import open.main.dbmanager.DBManager;
import open.main.utils.ToastUtil;

/**
 * 参考 http://blog.csdn.net/u012702547/article/details/52226163
 * http://www.cnblogs.com/whoislcj/p/5651396.html
 */

public class GreenDaoActivity extends BaseActivity {

    @InjectView(R.id.addOne)
    TextView addOne;
    @InjectView(R.id.addList)
    TextView addList;
    @InjectView(R.id.deleteOne)
    TextView deleteOne;
    @InjectView(R.id.updateOne)
    TextView updateOne;
    @InjectView(R.id.selectList)
    TextView selectList;
    @InjectView(R.id.selecListByAge)
    TextView selecListByAge;
    @InjectView(R.id.queryUserByType)
    TextView queryUserByType;

    DBManager dbManager;
    @InjectView(R.id.ed1)
    EditText ed1;
    @InjectView(R.id.ed2)
    EditText ed2;
    @InjectView(R.id.ed3)
    EditText ed3;
    @InjectView(R.id.deleteAll)
    TextView deleteAll;

    User user=new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        ButterKnife.inject(this);

        dbManager = DBManager.getInstance(this);
    }

    @OnClick({R.id.addOne, R.id.addList, R.id.deleteOne, R.id.updateOne, R.id.selectList,
            R.id.selecListByAge, R.id.queryUserByType,R.id.deleteAll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addOne:
                user.setAge(12);
                user.setMoney(100);
                user.setAddress("四川成都");
                user.setName("Cain");
                dbManager.insertUser(user);
                ToastUtil.showToast(this, dbManager.queryUserList() + "");
                break;
            case R.id.addList:
                List<User> list = new ArrayList<>();
                for (int i=0;i<4;i++){
                    User user1=new User();
                    user1.setAge(i);
                    user1.setMoney(i);
                    user1.setAddress("四川成都"+i);
                    user1.setName(i+"");
                    list.add(user1);
                }
                dbManager.insertUserList(list);
                ToastUtil.showToast(this, dbManager.queryUserList() + "");
                break;
            case R.id.deleteOne:
                dbManager.deleteUser(user);
                ToastUtil.showToast(this, dbManager.queryUserList() + "");
                break;
            case R.id.updateOne:
                user.setName("dadadadadadadada");
                dbManager.updateUser(user);
                ToastUtil.showToast(this, dbManager.queryUserList() + "");
                break;
            case R.id.selectList:
                ToastUtil.showToast(this, dbManager.queryUserList() + "");
                break;
            case R.id.selecListByAge:
                ToastUtil.showToast(this, dbManager.queryUserList(Integer.parseInt(ed1.getText().toString().trim())) + "");
                break;
            case R.id.queryUserByType:
                ToastUtil.showToast(this, dbManager.queryUserByType(Integer.parseInt(ed2.getText().toString().trim()),
                        ed3.getText().toString().trim()) + "");
                break;
            case R.id.deleteAll:
                dbManager.deleteUserAll();
                ToastUtil.showToast(this, dbManager.queryUserList() + "");
                break;
        }
    }
}

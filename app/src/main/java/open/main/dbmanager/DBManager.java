package open.main.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import open.main.bean.User;
import open.main.db.greendao.DaoMaster;
import open.main.db.greendao.DaoSession;
import open.main.db.greendao.UserDao;

/**
 * Created by Cain on 2017/6/6.
 *
 * GreenDao DB Manager
 *
 */

public class DBManager {
    private final static String dbName = "test_db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    /**
     * 插入一条记录
     *
     * @param user
     */
    public void insertUser(User user) {
        try {
            DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            UserDao userDao = daoSession.getUserDao();
            userDao.insert(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 插入用户集合
     *
     * @param users
     */
    public void insertUserList(List<User> users) {
        try {
            if (users == null || users.isEmpty()) {
                return;
            }
            DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            UserDao userDao = daoSession.getUserDao();
            userDao.insertInTx(users);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 删除一条记录
     *
     * @param user
     */
    public void deleteUser(User user) {
        try {
            DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            UserDao userDao = daoSession.getUserDao();
            userDao.delete(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 删除一条记录
     *
     * @param
     */
    public void deleteUserAll() {
        try {
            DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            UserDao userDao = daoSession.getUserDao();
            userDao.deleteAll();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 更新一条记录
     *
     * @param user
     */
    public void updateUser(User user) {
        try {
            DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            UserDao userDao = daoSession.getUserDao();
            userDao.update(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 查询用户列表
     */
    public List<User> queryUserList() {
        try {
            DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            UserDao userDao = daoSession.getUserDao();
            QueryBuilder<User> qb = userDao.queryBuilder();
            List<User> list = qb.list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询用户列表
     */
    public List<User> queryUserList(int age) {
        try {
            DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            UserDao userDao = daoSession.getUserDao();
            QueryBuilder<User> qb = userDao.queryBuilder();
            qb.where(UserDao.Properties.Age.eq(age)).orderAsc(UserDao.Properties.Age);
            List<User> list = qb.list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *多条件查询
     */
    public List<User> queryUserByType(int age,String name) {
        try {
            DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            UserDao userDao = daoSession.getUserDao();
            QueryBuilder<User> qb = userDao.queryBuilder();
            qb.where(qb.and(UserDao.Properties.Age.eq(age),UserDao.Properties.Name.eq(name)));
            qb.orderAsc(UserDao.Properties.Id);//排序
            List<User> list = qb.list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}

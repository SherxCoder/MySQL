package com.edu.swun.mysql;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private  static String dbUrl="jdbc:mysql://58fc13364cf64.gz.cdb.myqcloud.com:18302/swunDictionary";
    private static String dbDriverClass="com.mysql.jdbc.Driver";
    private  static String dbUserName="root";
    private  static String dbUserPassword="swunbs535";
    public static ResultSet resultSet=null;
    public static Connection connection;
    private  static PreparedStatement pStatement=null;
    private Button mButton;
    private TextView mTextView;
    private static final String TAG="MainActivity";
    String sql="select * from swunDictionary.user_register where user_id='610225'";

    Activity mActivity=this;
    String info=null;

  //关闭数据库连接操作
    public void close() {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        if (pStatement != null) {
            try {
                pStatement.cancel();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init();
        mButton= (Button) findViewById(R.id.login_mysql);
        mTextView= (TextView) findViewById(R.id.user_name);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Class.forName(dbDriverClass);
                            Log.d("加载驱动", "加载成功");
                            connection = DriverManager.getConnection(dbUrl, dbUserName, dbUserPassword);
                            Log.d("获取连接", "连接成功");
                            pStatement = connection.prepareStatement(sql);
                            resultSet = pStatement.executeQuery();

                            while (resultSet.next()) {

                                info = resultSet.getString("user_name");
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        mTextView.setText(info);

                                    }
                                });

                            }
                            Log.d("测name", "" + info);

                        } catch (SQLException e) {
                            e.printStackTrace();
                            Log.d("线程异常", "异常" );
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            Log.d("加载驱动", "加载失败");
                        }
                    }
                }).start();


            }


        });
    }
}
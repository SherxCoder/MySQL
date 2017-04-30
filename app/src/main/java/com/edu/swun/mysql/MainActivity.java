package com.edu.swun.mysql;

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
    String sql="select * from swunDictionary.user_register";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mButton= (Button) findViewById(R.id.login_mysql);
        mTextView= (TextView) findViewById(R.id.user_name);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    while(resultSet.next()){
                        String info=null;
                        info=resultSet.getString("user_name");
                        mTextView.setText(info);


                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    close();//关闭连接
                }
        }

    });
}

public void init(){
    try{
        Class.forName(dbDriverClass);
        connection= DriverManager.getConnection(dbUrl,dbUserName,dbUserPassword);
        pStatement=connection.prepareStatement(sql);
        resultSet=pStatement.executeQuery();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        Log.d(TAG,"类加载失败");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void close(){

        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        if(pStatement!=null){
            try {
                pStatement.cancel();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
        if(connection!=null){
            try{
                connection.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }

    }
}



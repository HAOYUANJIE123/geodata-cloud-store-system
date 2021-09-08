package test.dao;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by hao123 on 2019/9/27.
 */
public class finaluserAgent {

    String Agent_userid="";
    Agent_baseFunction agent_baseFunction=new Agent_baseFunction();
    ApplyTask applyTask=new ApplyTask();
    info_ApplyAtToPushAt info_applyAtToPushAt=new info_ApplyAtToPushAt();
    info_PushAtToFinalworker info_pushAtToFinalworker=new info_PushAtToFinalworker();





    public finaluserAgent(String agent_userid) throws SQLException {
        this.Agent_userid=agent_userid;
        createUseragent(Agent_userid);
    }

    //提交任务
    public void SubmitTask(HttpServletRequest request, HttpServletResponse response, Logger logger,
                           Properties properties, Properties hadoopProps){
        try {
            agent_baseFunction.agentTijiaoTask(request,response,logger, properties, hadoopProps);//调用agent提交任务
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //激活用户Agent
    public void createUseragent(String agentID) throws SQLException {
        Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
        Statement stmt = conn.createStatement();
        String sqlusedb = "use cloud";
        int result1 = stmt.executeUpdate(sqlusedb);
        String sql = " select * from userAgent where Agent_userid='"+agentID+"'";
        ResultSet rs = stmt.executeQuery(sql);
        String result="";
        while(rs.next()){
            result= rs.getString("Agent_userid").toString();
        }
        if(!result.equals(agentID))
        {
            sql = "insert into userAgent(Agent_userid,SelectableTaskid,finalselectTaskid,determind_worker,test) " + "values('"+agentID+"','','','','')";
            stmt.executeUpdate(sql);
        }
        conn.close();
    }
    //检索任务商店，接收符合条件的任务
    public void timeAction(){
        TimerTask task1 = new TimerTask() {
            public void run() {
                try {
                    applyTask.checkTaskstore(Agent_userid);
                    info_applyAtToPushAt.info_applyTask(Agent_userid);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };

        TimerTask task3 = new TimerTask() {
            public void run() {
                try {
                    info_pushAtToFinalworker.info_determindWorker(Agent_userid);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task1,0,60000);
        timer.schedule(task3,0,100000);

    }//三个time任务
    public void Threadpool(){
        ExecutorService thredPool = Executors.newFixedThreadPool(3);
        Future<String> f1 = thredPool.submit(new Callable<String>() {
            public String call() {

              //  timeAction();
                return null;
            }
        });


        //关闭线程池
        thredPool.shutdown();
    }//使用线程池并行进行多个任务
    }



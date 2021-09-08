package test.dao;

import test.vo.UserClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hao123 on 2019/9/27.
 */
public class info_PushAtToFinalworker {
    //产生用户Agent
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
    //发布agen向申请任务的对应agent确定任务
    public void info_determindWorker(String Agent_userid) throws SQLException {
        Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
        Statement stmt = conn.createStatement();
        String sqlusedb = "use cloud";
        int result1 = stmt.executeUpdate(sqlusedb);
        String sql = "select determind_worker from userAgent where Agent_userid='"+Agent_userid+"'";
        ResultSet rs =stmt.executeQuery(sql);
        String determind_worker="";
        while(rs.next()){
            determind_worker=rs.getString("determind_worker").toString();
        }
        //划分determind_worker任务部分
        List<String> WholeTaskDescriList=new LinkedList<>();//一个任务部分的整个描述
        String determind_workerTask=determind_worker;
        while(determind_workerTask.indexOf(";")>0){
            WholeTaskDescriList.add(determind_workerTask.substring(0,determind_workerTask.indexOf(";")));
            determind_workerTask=determind_workerTask.substring(determind_workerTask.indexOf(";")+1);

        }
        //遍历每个任务部分，将当前用户加到对应的任务部分去
        for(int j=0;j<WholeTaskDescriList.size();j++){

            String determind_workerTask1=WholeTaskDescriList.get(j);
            String determind_workerTaskid=determind_workerTask1.substring(0,determind_workerTask1.indexOf(","));//任务id
            String AllApplicantForTask=determind_workerTask1.substring(determind_workerTask1.indexOf(",")+1);//该任务的所有申请者

            List<String> ApplicationIDForTaskList=new LinkedList<>();//一个任务部分的整个描述
            while(AllApplicantForTask.indexOf("#")>0){
                ApplicationIDForTaskList.add(AllApplicantForTask.substring(0,AllApplicantForTask.indexOf("#")));
                AllApplicantForTask=AllApplicantForTask.substring(AllApplicantForTask.indexOf("#")+1);
            }

            //根据 ApplicationIDForTaskList，连接数据库获得对应的申请者信息
            List<UserClass> ApplicationListInfo = new LinkedList<>();
            UserClass receinfo;
            for (int i = 0; i < ApplicationIDForTaskList.size(); i++) {
                receinfo = new UserClass();
                sql = "select rwwcl,rwsbl,rwdql,score from user where user_id='" +  ApplicationIDForTaskList.get(i) + "'";
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    receinfo.setUser_id( ApplicationIDForTaskList.get(i));
                    receinfo.setRwwcl(Integer.parseInt(rs.getString("rwwcl")));
                    receinfo.setRwsbl(Integer.parseInt(rs.getString("rwsbl")));
                    receinfo.setRwdql(Integer.parseInt(rs.getString("rwdql")));
                    receinfo.setScore(Double.parseDouble(rs.getString("score")));
                    receinfo.setFinalsorce(receinfo.getScore() * (receinfo.getRwwcl() - receinfo.getRwsbl()));//最终得分=平均得分*（完成量-失败量）

                }
                ApplicationListInfo.add(receinfo);
            }


//            //基于receiver的信息做出判断,找出最后完成工作的用户id
            double[] a = new double[ApplicationListInfo.size()];
            for (int i = 0; i < ApplicationListInfo.size(); i++) {
                a[i] = ApplicationListInfo.get(i).getScore();
            }
            Arrays.sort(a);
            double fianlworker_sorce = a[ApplicationListInfo.size() - 1];
            String finalWorkerid = "";
            for (int i = 0; i < ApplicationListInfo.size(); i++) {
                if (ApplicationListInfo.get(i).getScore() == fianlworker_sorce) {
                    finalWorkerid = ApplicationListInfo.get(i).getUser_id();

                    String finalselectTaskid="";
                    sql = "select finalselectTaskid from userAgent where Agent_userid='"+finalWorkerid+"'";
                    rs =stmt.executeQuery(sql);
                    while(rs.next()){
                        finalselectTaskid=rs.getString("finalselectTaskid").toString();
                    }

                    if(!finalselectTaskid.contains(determind_workerTaskid+",")){
                        createUseragent(finalWorkerid);
                        String resultForDemtermind=finalselectTaskid+determind_workerTaskid+","+finalWorkerid+";";
                        sql="update userAgent set finalselectTaskid='"+resultForDemtermind+"' where Agent_userid='"+finalWorkerid+"'";
                        stmt.executeUpdate(sql);
                        sql="update rwb set finalworkerid='"+finalWorkerid+"',rwzt=1 where rw_id="+Integer.parseInt(determind_workerTaskid)+"";
                        stmt.executeUpdate(sql);
                        String Old_determind_worker="";
                        sql = "select determind_worker from userAgent where Agent_userid='"+Agent_userid+"'";
                        rs =stmt.executeQuery(sql);
                        while(rs.next()){
                            Old_determind_worker=rs.getString("determind_worker").toString();
                        }
                        String newdetermind_worker=Old_determind_worker.substring(Old_determind_worker.indexOf(determind_workerTaskid),Old_determind_worker.indexOf(";")+1);
                        newdetermind_worker=Old_determind_worker.replace(WholeTaskDescriList.get(j)+";","");
                        sql="update userAgent set determind_worker='"+newdetermind_worker+"' where Agent_userid='"+Agent_userid+"'";
                        stmt.executeUpdate(sql);
                    }
                }
            }



        }
        conn.close();
    }

}

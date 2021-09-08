package test.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hao123 on 2019/9/27.
 */
public class info_ApplyAtToPushAt {

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

    //申请agen向发布任务的对应agent说我想接你任务
    public void info_applyTask(String Agent_userid) throws SQLException {
        Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
        Statement stmt = conn.createStatement();
        String sqlusedb = "use cloud";
        int result1 = stmt.executeUpdate(sqlusedb);
        //获取自己可选任务列表中的rwid
        String sql = "select SelectableTaskid from userAgent where Agent_userid='"+Agent_userid+"'";
        ResultSet rs = stmt.executeQuery(sql);
        String SelectableTaskid="";
        while(rs.next()){
            SelectableTaskid=rs.getString("SelectableTaskid").toString();
        }
        //根据“;”划分SelectableTaskid
        List<String> SelectableTaskidList=new LinkedList<>();
        while(SelectableTaskid.indexOf(";")>0){
            SelectableTaskidList.add(SelectableTaskid.substring(0,SelectableTaskid.indexOf(";")));
            SelectableTaskid=SelectableTaskid.substring(SelectableTaskid.indexOf(";")+1);
        }
        //当用户agent的可选任务列表不为空时，向发布任务agent喊话进行任务注册
        if(SelectableTaskidList.size()!=0){

            //遍历可选任务列表
            for(int i=0;i<SelectableTaskidList.size();i++)
            {
                String determind_worker="";
                //首先根据任务id,追踪到发布该任务的用户agentid
                sql="select user_id from rwb where rw_id="+ Integer.parseInt(SelectableTaskidList.get(i))+"";
                rs =stmt.executeQuery(sql);
                String TaskPost_AgentID="";
                while(rs.next()){
                    TaskPost_AgentID=rs.getString("user_id").toString();
                }

                //查看发布任务的agent是否激活，如没有，则激活
                createUseragent(TaskPost_AgentID);

                //查看该TaskPost_AgentID的determind_worker内容
                sql = "select determind_worker from userAgent where Agent_userid='"+TaskPost_AgentID+"'";
                rs =stmt.executeQuery(sql);
                String determind_worker_1="";
                while(rs.next()){
                    determind_worker_1=rs.getString("determind_worker").toString();
                }
                determind_worker=determind_worker_1;
                sql="update userAgent set test='"+determind_worker+"' where Agent_userid='"+TaskPost_AgentID+"'";
                stmt.executeUpdate(sql);

                //查看determind_worker是否已经含有该任务id的部
                if(!determind_worker.contains(SelectableTaskidList.get(i)+",")){
                    //如果没有向它里面注册该任务id项目,并将自己写入任务中
                    String determind_worker1=determind_worker+SelectableTaskidList.get(i)+","+Agent_userid+"#;";
                    sql="update userAgent set determind_worker='"+determind_worker1+"' where Agent_userid='"+TaskPost_AgentID+"'";
                    stmt.executeUpdate(sql);
                    continue;
                }

                if (determind_worker.contains(SelectableTaskidList.get(i)+",")){
                    //determind_worker已经包含该任务部分，查看该任务部分是否包含当前申请agent
                    //根据‘；’划分determind_worker内容
                    List<String> WholeTaskDescriList=new LinkedList<>();//一个任务部分的整个描述
                    sql = "select determind_worker from userAgent where Agent_userid='"+TaskPost_AgentID+"'";
                    rs =stmt.executeQuery(sql);
                    String determind_workerTask="";
                    while(rs.next()){
                       determind_workerTask=rs.getString("determind_worker").toString();
                    }

                    while(determind_workerTask.indexOf(";")>0){
                        WholeTaskDescriList.add(determind_workerTask.substring(0,determind_workerTask.indexOf(";")));
                        determind_workerTask=determind_workerTask.substring(determind_workerTask.indexOf(";")+1);
                    }
                    //遍历每个任务部分，将当前用户加到对应的任务部分去
                    for(int j=0;j<WholeTaskDescriList.size();j++){

                        //遍历到包含申请任务的记录的部分，判断allApplication是否包含当前申请用户，如没有则加进去
                        if(WholeTaskDescriList.get(j).contains(SelectableTaskidList.get(i)+",")){
                            String determind_workerTask1=WholeTaskDescriList.get(j);
                            String determind_workerTaskid=determind_workerTask1.substring(0,determind_workerTask1.indexOf(","));
                            String AllApplicantForTask=determind_workerTask1.substring(determind_workerTask1.indexOf(",")+1);
                            //申请者不包含当前用户则加进去
                            if(!AllApplicantForTask.contains(Agent_userid+"#"))
                            {
                             AllApplicantForTask=AllApplicantForTask+Agent_userid+"#";
                            String newTaskDescri=determind_workerTaskid+","+AllApplicantForTask;
                            WholeTaskDescriList.set(j,newTaskDescri);
                            }
                            break;
                        }
                    }

                    //进行更新后的WholeTaskDescriList信息重组,并重新写入任务发布者的determind_worker
                    String newWholeTaskDescriList="";
                    for(int j=0;j<WholeTaskDescriList.size();j++)
                        newWholeTaskDescriList=newWholeTaskDescriList+WholeTaskDescriList.get(j)+";";
                    sql="update userAgent set determind_worker='"+newWholeTaskDescriList+"' where Agent_userid='"+TaskPost_AgentID+"'";
                    stmt.executeUpdate(sql);
                }
            }
            //删除当前agent的SelectableTaskid中已经向发布任务的agent注册的任务id
            sql="update userAgent set SelectableTaskid='' where Agent_userid='"+Agent_userid+"'";
            stmt.executeUpdate(sql);

        }
    conn.close();
    }

}

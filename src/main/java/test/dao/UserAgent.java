package test.dao;

import test.vo.TaskClass;
import test.vo.UserClass;
import test.vo.determind_workerTaskPJ;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hao123 on 2019/9/25.
 */
public class UserAgent {
    String Agent_userid="";

    public UserAgent(String agent_userid) throws SQLException {
        this.Agent_userid=agent_userid;
        createUseragent(Agent_userid);
    }

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


    //获取该agent对应用户的技能
    public List<String> get_UserSkill() throws SQLException {

        Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
        Statement stmt = conn.createStatement();
        String sqlusedb = "use cloud";
        int result1 = stmt.executeUpdate(sqlusedb);
        String sql = "select skill from user where user_id='"+Agent_userid+"'";
        ResultSet rs = stmt.executeQuery(sql);
        String skill="";
        List<String>SkillList=new LinkedList<>();
        while(rs.next()){
            skill=rs.getString("skill");
        }
        //基于skill的内容中的;分号将skill内容进行划分，并赋给Skill1-Skill5

        while(skill.indexOf(";")>0){
            SkillList.add(skill.substring(0,skill.indexOf(";")));
            skill=skill.substring(skill.indexOf(";")+1);
        }
//        sql="update userAgent set test='"+skill+"' where Agent_userid='"+Agent_userid+"'";
//        stmt.executeUpdate(sql);
        conn.close();
        return SkillList;
    }


    //agengt查看当前用户是否可接任务
    public int CheckUserState() throws SQLException {
        Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
        Statement stmt = conn.createStatement();
        String sqlusedb = "use cloud";
        int result1 = stmt.executeUpdate(sqlusedb);
        String sql = " select finalselectTaskid from userAgent where Agent_userid='"+Agent_userid+"'";
        ResultSet rs = stmt.executeQuery(sql);
        String finalselectTaskid="";
        List<String> finalselectTaskidList=new LinkedList<>();
        while(rs.next()){
            finalselectTaskid= rs.getString("finalselectTaskid").toString();
        }
        while(finalselectTaskid.indexOf(";")>0){
            finalselectTaskidList.add(finalselectTaskid.substring(0,finalselectTaskid.indexOf(";")));
            finalselectTaskid=finalselectTaskid.substring(finalselectTaskid.indexOf(";")+1);
            }
            return finalselectTaskidList.size();
        }




    //检索任务商店，接收符合条件的任务
    public void checkTaskstore() throws SQLException {
        //检索任务商店
        if(CheckUserState()<=3){
            Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "select rw_id, rwskill,descri from rwb where rwzt=0";
            ResultSet rs = stmt.executeQuery(sql);
            List<TaskClass> taskList=new LinkedList<>();
            TaskClass tc;
            //将任务状态为“0”的任务保存起来
            while(rs.next()){
                tc=new TaskClass();
                tc.setRwid(rs.getString("rw_id").toString());
                tc.setRwskill(rs.getString("rwskill").toString());
                tc.setDescri(rs.getString("descri").toString());
                taskList.add(tc);
            }
            //判断taskList中是否有符合自己技能的任务,如果有则将其写进可选任务中
            List<String> userSkill=get_UserSkill();


            for(int i=0;i<taskList.size();i++){
                boolean a=false;
                for(int j=0;j<userSkill.size();j++){
                    //如果任务的技能要求与用户的技能匹配，或者任务描述中包含用户技能则可接任务
                    if(taskList.get(i).getRwskill().equals(userSkill.get(j))||taskList.get(i).getDescri().contains(userSkill.get(j)))
                        a=true;
                }
                if(a)

                {

                    //先查看当前用户agent的SelectableTaskid是否包含新筛选的任务
                    sql = "select SelectableTaskid from userAgent where Agent_userid='"+Agent_userid+"'";
                     rs = stmt.executeQuery(sql);
                    String SelectableTaskid="";
                    while(rs.next()){
                        SelectableTaskid=rs.getString("SelectableTaskid").toString();
                    }
                 //如果不包含新的任务则将其写入SelectableTaskid
                    if(!SelectableTaskid.contains(taskList.get(i).getRwid()+";"))
                        SelectableTaskid=SelectableTaskid+taskList.get(i).getRwid()+";";
                    sql="update userAgent set SelectableTaskid='"+SelectableTaskid+"' where Agent_userid='"+Agent_userid+"'";
                    stmt.executeUpdate(sql);

                }
            }
            conn.close();
        }
    }


    //申请agen向发布任务的对应agent说我想接你任务
    public void info_applyTask() throws SQLException {
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
        List<String>SelectableTaskidList=new LinkedList<>();
        while(SelectableTaskid.indexOf(";")>0){
            SelectableTaskidList.add(SelectableTaskid.substring(0,SelectableTaskid.indexOf(";")));
            SelectableTaskid=SelectableTaskid.substring(SelectableTaskid.indexOf(";")+1);
        }

        //当用户agent的可选任务列表不为空时，向发布任务agent喊话进行任务注册
        if(SelectableTaskidList.size()!=0){
               //遍历可选任务列表


            for(int i=0;i<SelectableTaskidList.size();i++)
            {
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
                String determind_worker="";
                sql = "select determind_worker from userAgent where Agent_userid='"+TaskPost_AgentID+"'";
                rs =stmt.executeQuery(sql);

                while(rs.next()){
                    determind_worker=rs.getString("determind_worker").toString();
                }


                //查看determind_worker是否已经含有该任务id的部分
                if(!determind_worker.contains(SelectableTaskidList.get(i)+",")){
                    //如果没有向它里面注册该任务id项目,并将自己写入任务中
                  String  determind_worker1=determind_worker+SelectableTaskidList.get(i)+","+Agent_userid+"#;";

                    sql="update userAgent set determind_worker='"+determind_worker1+"' where Agent_userid='"+TaskPost_AgentID+"'";
                    stmt.executeUpdate(sql);
//                    //删除当前agent的SelectableTaskid中已经向发布任务的agent注册的任务id
                    sql = "select SelectableTaskid from userAgent where Agent_userid='"+Agent_userid+"'";
                    rs = stmt.executeQuery(sql);
                    String OldSelectableTaskid="";
                    while(rs.next()){
                        OldSelectableTaskid=rs.getString("SelectableTaskid").toString();
                    }
                    String newSelectableTaskid=OldSelectableTaskid.replace(SelectableTaskidList.get(i)+";","");
                    sql="update userAgent set SelectableTaskid='"+newSelectableTaskid+"' where Agent_userid='"+Agent_userid+"'";
                    stmt.executeUpdate(sql);
                }
                else {
                    //determind_worker已经包含该任务部分，查看该任务部分是否包含当前申请agent
                    //根据‘；’划分determind_worker内容
                    List<String> WholeTaskDescriList=new LinkedList<>();//一个任务部分的整个描述
                   String determind_workerTask=determind_worker;
                    while(determind_workerTask.indexOf(";")>0){
                        WholeTaskDescriList.add(determind_workerTask.substring(0,determind_workerTask.indexOf(";")));
                        determind_workerTask=determind_workerTask.substring(determind_workerTask.indexOf(";")+1);

                    }
                    //遍历每个任务部分，将当前用户加到对应的任务部分去
                    for(int j=0;j<WholeTaskDescriList.size();j++){

                        //遍历到包含申请任务的记录的部分，判断allApplication是否包含当前申请用户，如没有则加进去
                        if(WholeTaskDescriList.get(j).contains(SelectableTaskidList.get(i)+",")){
                           String determind_workerTask1=WholeTaskDescriList.get(i);
                            String determind_workerTaskid=determind_workerTask1.substring(0,determind_workerTask1.indexOf(","));
//                            String AllApplicantForTask=determind_workerTask1.substring(determind_workerTask1.indexOf(",")+1,determind_workerTask1.indexOf(";"));
                            String AllApplicantForTask=determind_workerTask1.substring(determind_workerTask1.indexOf(",")+1);
                           if(!AllApplicantForTask.contains(Agent_userid+"#"))
                                AllApplicantForTask=AllApplicantForTask+Agent_userid+"#";
                           String newTaskDescri=determind_workerTaskid+","+AllApplicantForTask;
                            WholeTaskDescriList.set(j,newTaskDescri);
                        }
                    }

                    //进行更新后的WholeTaskDescriList信息重组,并重新写入任务发布者的determind_worker
                    String newWholeTaskDescriList="";
                    for(int j=0;j<WholeTaskDescriList.size();j++)
                        newWholeTaskDescriList=newWholeTaskDescriList+WholeTaskDescriList.get(j)+";";
                    sql="update userAgent set determind_worker='"+newWholeTaskDescriList+"' where Agent_userid='"+TaskPost_AgentID+"'";
                    stmt.executeUpdate(sql);

                    //删除当前agent的SelectableTaskid中已经向发布任务的agent注册的任务id
                    sql = "select SelectableTaskid from userAgent where Agent_userid='"+Agent_userid+"'";
                    rs = stmt.executeQuery(sql);
                    String OldSelectableTaskid="";
                    while(rs.next()){
                        OldSelectableTaskid=rs.getString("SelectableTaskid").toString();
                    }
                    String newSelectableTaskid=OldSelectableTaskid.replace(SelectableTaskidList.get(i)+";","");
                    sql="update userAgent set SelectableTaskid='"+newSelectableTaskid+"' where Agent_userid='"+Agent_userid+"'";
                    stmt.executeUpdate(sql);
                }
            }
        }
                  conn.close();
    }

    //发布agen向申请任务的对应agent确定任务
    public void info_determindWorker() throws SQLException {
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

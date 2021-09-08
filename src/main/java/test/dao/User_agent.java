package test.dao;
import test.vo.TaskClass;
import test.vo.User;
import test.vo.UserClass;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import static java.lang.System.out;
import static test.dao.Agent1.change_a1_stateAs;

/**
 * Created by hao123 on 2019/9/20.
 */
public class User_agent {

String Agent_userid="";

    public User_agent() {
    }

    public  User_agent(String agent_userid) throws SQLException {
        this.Agent_userid=agent_userid;
        createUseragent();
    }

//
    //产生用户Agent
    public void createUseragent() throws SQLException {

        Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
        Statement stmt = conn.createStatement();
        String sqlusedb = "use cloud";
        int result1 = stmt.executeUpdate(sqlusedb);
        String sql = " select * from userAgent where Agent_userid='"+Agent_userid+"'";
        ResultSet rs = stmt.executeQuery(sql);
        String result="";
        while(rs.next()){
            result= rs.getString("Agent_userid").toString();
        }
      if(!result.equals(Agent_userid))
      {
          sql = "insert into userAgent(Agent_userid,SelectableTaskid,finalselectTaskid) " + "values('"+Agent_userid+"','','')";
        stmt.executeUpdate(sql);
      }
        conn.close();
    }
    //消灭用户Agent
    public void deleteUseragent() throws SQLException {
        Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
        Statement stmt = conn.createStatement();
        String sqlusedb = "use cloud";
        int result1 = stmt.executeUpdate(sqlusedb);
        String sql = "delete from userAgent where Agent_userid='"+Agent_userid+"'";
        int result = stmt.executeUpdate(sql);
        stmt.executeUpdate(sql);
        conn.close();
    }
    //改变用户工作状态
    public  void change_user_stateAs() throws SQLException {
        Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
        Statement stmt = conn.createStatement();
        String sqlusedb = "use cloud";
        int result1 = stmt.executeUpdate(sqlusedb);
        String sql ="select rw_id from rwb where finalworkerid='"+Agent_userid+"'and rwzt=1";
        ResultSet rs = stmt.executeQuery(sql);
        String finalselectTaskid="";
        while(rs.next()){
         finalselectTaskid=finalselectTaskid+rs.getString("rw_id").toString()+";";;
        }
         sql ="select finalselectTaskid from userAgent where Agent_userid='"+Agent_userid+"'";
         rs = stmt.executeQuery(sql);
        String Old_finalselectTaskid="";
        while(rs.next()){
             Old_finalselectTaskid=rs.getString("finalselectTaskid").toString();
        }
        if(!Old_finalselectTaskid.contains(finalselectTaskid)){
        sql="UPDATE userAgent SET finalselectTaskid=+'"+Old_finalselectTaskid+finalselectTaskid+"' where Agent_userid='"+Agent_userid+"'";
        stmt.executeUpdate(sql);
        }
        sql="select finalselectTaskid from userAgent where Agent_userid='"+Agent_userid+"'";
        rs= stmt.executeQuery(sql);
        while(rs.next()){
            finalselectTaskid=rs.getString("finalselectTaskid").toString();
        }
        if(!finalselectTaskid.equals("")){
            sql = "UPDATE user SET UserState='1' where user_id='"+Agent_userid+"'";
            stmt.executeUpdate(sql);
        }
       else {
            sql = "UPDATE user SET UserState='0' where user_id='"+Agent_userid+"'";
            stmt.executeUpdate(sql);
        }
        conn.close();
    }

    //查看用户的工作状态
    public boolean check_UserState() throws SQLException {
        Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
        Statement stmt = conn.createStatement();
        String sqlusedb = "use cloud";
        int result1 = stmt.executeUpdate(sqlusedb);
        String sql = "select UserState from user where user_id='"+Agent_userid+"'";
        ResultSet rs = stmt.executeQuery(sql);
        String result="";
        while(rs.next()){
            result=rs.getString("UserState").toString();
        }
        conn.close();
        return (result.equals("0"));//检索用户的工作状态是否繁忙，0为闲true，1为忙
    }

    //获取该agent对应用户的技能
    public List<String> get_UserSkill() throws SQLException {
        List<String>SkillList=new LinkedList<>();
        Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
        Statement stmt = conn.createStatement();
        String sqlusedb = "use cloud";
        int result1 = stmt.executeUpdate(sqlusedb);
        String sql = "select skill from user where user_id='"+Agent_userid+"'";
        ResultSet rs = stmt.executeQuery(sql);
        String skill="";
        while(rs.next()){
           skill=rs.getString("skill");
        }
        //基于skill的内容中的;分号将skill内容进行划分，并赋给Skill1-Skill5
        if (skill.indexOf(";")>0){
            SkillList.add(skill.substring(0,skill.indexOf(";")));
            skill=skill.substring(skill.indexOf(";")+1);
        }
        if (skill.indexOf(";")>0){
            SkillList.add(skill.substring(0,skill.indexOf(";")));
            skill=skill.substring(skill.indexOf(";")+1);
        }
        if (skill.indexOf(";")>0){
            SkillList.add(skill.substring(0,skill.indexOf(";")));
            skill=skill.substring(skill.indexOf(";")+1);
        }
        if (skill.indexOf(";")>0){
            SkillList.add(skill.substring(0,skill.indexOf(";")));
            skill=skill.substring(skill.indexOf(";")+1);
        }
        if (skill.indexOf(";")>0){
            SkillList.add(skill.substring(0,skill.indexOf(";")));
        }
        conn.close();
        return SkillList;
    }

    //检索任务商店，接收符合条件的任务
    public void checkTaskstore() throws SQLException {
        //检索任务商店
        if(check_UserState()){
            Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "select rwid, rwskill,descri from rwb where rwzt=0";
            ResultSet rs = stmt.executeQuery(sql);
            List<TaskClass> taskList=new LinkedList<>();
            TaskClass tc;
            //将任务状态为“0”的任务保存起来
            while(rs.next()){
                tc=new TaskClass();
                tc.setRwid(rs.getString("rwid").toString());
                tc.setRwskill(rs.getString("rwskill").toString());
                tc.setDescri(rs.getString("descri").toString());
                taskList.add(tc);
            }

            //判断taskList中是否有符合自己技能的任务,如果有则将其写进可选任务中
            for(int i=0;i<taskList.size();i++){
                boolean a=false;
                for(int j=0;j<get_UserSkill().size();j++){
                    //如果任务的技能要求与用户的技能匹配，或者任务描述中包含用户技能则可接任务
                    if(taskList.get(i).getRwskill().equals(get_UserSkill().get(j))||taskList.get(i).getDescri().contains(get_UserSkill().get(j)))
                        a=true;
                }

                //判断成功后将任务id写入userAgent表的SelectableTaskid中，将Agent_userid即用户id写入到对应任务的receiver中
                if(a){
                   int  result2 = stmt.executeUpdate(sqlusedb);
                     sql = "select SelectableTaskid from userAgent where user_id='"+Agent_userid+"'";
                    ResultSet rs1 = stmt.executeQuery(sql);
                    String SelectableTaskid="";
                    while(rs.next()){
                        SelectableTaskid=rs.getString("SelectableTaskid").toString();
                    }
                    if(SelectableTaskid.equals(""))
                    SelectableTaskid=taskList.get(i).getRwid()+";";
                    else
                        SelectableTaskid=SelectableTaskid+taskList.get(i).getRwid()+";";
                    sql="UPDATE userAgent SET SelectableTaskid="+SelectableTaskid+" where user_id='"+Agent_userid+"'";
                    stmt.executeQuery(sql);
                    sql="select receiver from rwb where rw_id="+ Integer.parseInt(taskList.get(i).getRwid())+"";
                    stmt.executeQuery(sql);
                    String receiver="";
                    while(rs.next()){
                        receiver=rs.getString("receiver").toString();
                    }
                    if(receiver.equals(""))
                        receiver=Agent_userid+";";
                    else
                        receiver=receiver+Agent_userid+";";
                    sql="update rwb set receive='"+receiver+"' where rw_id="+ Integer.parseInt(taskList.get(i).getRwid())+"";
                    stmt.executeUpdate(sql);
                    conn.close();
                }
            }
        }
    }



    //筛选最终要完成任务的用户
    public void SelectFinalWorker() throws SQLException {
        Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
        Statement stmt = conn.createStatement();
        String sqlusedb = "use cloud";
        int result1 = stmt.executeUpdate(sqlusedb);
        String sql = "select receiver,rwid from rwb where user_id='" + Agent_userid + "'AND rwzt=0";
        ResultSet rs = stmt.executeQuery(sql);
        List<String> receiverList;
        String receiver;
        List<Integer> rwidArr=new LinkedList<>();
        List<String> receiverArr = new LinkedList<>();
        while (rs.next()) {
            if(!rs.getString("receiver").toString().equals(""))
            { rwidArr.add(Integer.parseInt(rs.getString("rwid")));
            receiverArr.add(rs.getString("receiver"));}
        }
        //任务表中可能含有多个发起人的任务
        for(int j=0;j<=rwidArr.size();j++) {

            //以下为对一个任务的操作
             receiver =receiverArr.get(j);
            receiverList = new LinkedList<>();
            //将receiver进行划分
            while (receiver.indexOf(";") > 0) {
                receiverList.add(receiver.substring(0, receiver.indexOf(";")));
                receiver = receiver.substring(receiver.indexOf(";") + 1);
            }

            //根据receiverlist，连接数据库获得对应的receiver信息
            List<UserClass> receiverListInfo = new LinkedList<>();
            UserClass receinfo;
            for (int i = 0; i < receiverList.size(); i++) {
                receinfo = new UserClass();
                sql = "select rwwcl,rwsbl,rwdql,score from user where user_id='" + receiverList.get(i) + "'";
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    if (Integer.parseInt(rs.getString("rwdql")) <= 3)//如果receiver的当前量小于等于3才有资格
                    {
                        receinfo.setUser_id(receiverList.get(i));
                        receinfo.setRwwcl(Integer.parseInt(rs.getString("rwwcl")));
                        receinfo.setRwsbl(Integer.parseInt(rs.getString("rwsbl")));
                        receinfo.setRwdql(Integer.parseInt(rs.getString("rwdql")));
                        receinfo.setScore(Double.parseDouble(rs.getString("score")));
                        receinfo.setFinalsorce(receinfo.getScore() * (receinfo.getRwwcl() - receinfo.getRwsbl()));//最终得分=平均得分*（完成量-失败量）
                    }
                }
                receiverListInfo.add(receinfo);
            }
            //基于receiver的信息做出判断,找出最后完成工作的用户id
            double[] a = new double[receiverListInfo.size()];
            for (int i = 0; i < receiverListInfo.size(); i++) {
                a[i] = receiverListInfo.get(i).getScore();
            }
            Arrays.sort(a);
            double fianlworker_sorce = a[receiverListInfo.size() - 1];
            String finalWorkerid = "";
            for (int i = 0; i < receiverListInfo.size(); i++) {
                if (receiverListInfo.get(i).getScore() == fianlworker_sorce) {
                    finalWorkerid = receiverListInfo.get(i).getUser_id();
                    sql="UPDATE rwb SET finalworkerid="+finalWorkerid+",rwzt=1 where rw_id="+rwidArr.get(j)+"";//更新该任务的最终申请用户id，以及任务状态为1
                    stmt.executeQuery(sql);
                    sql="UPDATE user SET rwdql=rwdql+1 where user_id='"+finalWorkerid+"'";//最终申请用户id的任务当前量+1
                    stmt.executeQuery(sql);
                }
            }
        }
        conn.close();
    }

public void timeChecktaskStore(){
    TimerTask task = new TimerTask() {
        public void run() {
            try {
                checkTaskstore();//检索任务商店
                SelectFinalWorker();//选择最终任务完成用户
                change_user_stateAs();//改变用户状态
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
    Timer timer = new Timer();
    timer.schedule(task,0,5000);
}






}

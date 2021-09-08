package test.dao;
import test.vo.TaskClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
/**
 * Created by hao123 on 2019/9/27.
 */
public class ApplyTask {

    //获取该agent对应用户的技能
    public List<String> get_UserSkill(String Agent_userid) throws SQLException {

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
    public int CheckUserState(String Agent_userid) throws SQLException {
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
    public void checkTaskstore(String Agent_userid) throws SQLException {
        //检索任务商店
        if(CheckUserState(Agent_userid)<=3){
            Connection conn = DatabaseConn.getConn();// 使用DatabaseConn类来的getConn方法来建立mysql数据库,zs 建立数据库连接对象
            Statement stmt = conn.createStatement();
            String sqlusedb = "use cloud";
            int result1 = stmt.executeUpdate(sqlusedb);
            String sql = "select rw_id, rwskill,descri from rwb where rwzt=0 and user_id !='"+Agent_userid+"'";
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
            List<String> userSkill=get_UserSkill(Agent_userid);


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
}

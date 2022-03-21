package model.mapper;

import model.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StudentMapper {
    @Insert("insert into student values" +
            "(#{sid}, #{name},#{sex},#{birthday}," +
            "#{province},#{hobby},#{phone})")
    int addStudent(Student student);

    @Select("select * from  student where sid = #{sid}")
    Student getStudentBySid(String sid);

    @Select("select * from student")
    List<Student> getAllStudent();

    //TODO 根据给定的sid更新学生信息
    @Update("update student set " +
            "sid=#{student.sid},name=#{student.name}," +
            "sex=#{student.sex},birthday=#{student.birthday}," +
            "province=#{student.province},hobby=#{student.hobby}," +
            "phone=#{student.phone}" +
            " where sid=#{sid}")
    int updateStudentInfo(@Param("sid") String sid, @Param("student") Student student);

    @Delete("delete from student where sid = #{sid}")
    int deleteStudentBySid(String sid);
}

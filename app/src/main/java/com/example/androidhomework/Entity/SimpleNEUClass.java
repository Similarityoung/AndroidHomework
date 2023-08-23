package com.example.androidhomework.Entity;

import java.util.List;

public class SimpleNEUClass {
    private Integer day; //星期几？
    private String name; //课程名字？
    private String position; //教室？ 体育课没有教室的哦~~~~
    private List<Integer> sections; //第几节？ 这是一个数组哦，数组里面有1和2说明这节课在第1节和第2节
    private String teacher; //授课老师？ 这里不是课程老师，而是这一次课的授课老师哦~ 两者不一样
    private List<Integer> weeks; //第几周有？ 一个数组，有1,3,5,7说明就是1,3,5,7周有

    public SimpleNEUClass() {
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        String place = position.isEmpty() ? "无教室信息" : position;
        return place;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<Integer> getSections() {
        return sections;
    }

    public void setSections(List<Integer> sections) {
        this.sections = sections;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<Integer> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Integer> weeks) {
        this.weeks = weeks;
    }

    public String getTime() {

        if(sections.get(0) == 1){
            if(sections.size()==4)
            return "8:30-12:30";
            else
                return "8:30-10:20";
        }
        if(sections.get(0) == 3){
            return "10:40-12:30";
        }
        if(sections.get(0) == 5){
            if(sections.size()==4)
                return "14:00-18:00";
            else
                return "14:00-15:50";
        }
        if(sections.get(0) == 7){
            return "16:10-18:00";
        }
        if(sections.get(0) == 9){
            if(sections.size()==2)
            return "18:30-20:20";
            else if (sections.size()==3)
                return "18:30-9:30";
            else
                return "18:30-10:20";
        }
        return "不对劲";
    }

    @Override
    public String toString() {
        return "-------- 课程信息 --------" + System.lineSeparator() +
                "星期几：" + day + System.lineSeparator() +
                "名称：" + name + System.lineSeparator() +
                "教室：" + (position.isEmpty() ? "无教室信息" : position) + System.lineSeparator() +
                "节数：" + sections + System.lineSeparator() +
                "授课教师：" + teacher + System.lineSeparator() +
                "周数：" + weeks + System.lineSeparator();
    }
}
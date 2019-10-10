package cn.systemdesigner.spark.core;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.List;

public final class UserCourseCount {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder().master("local")
                .appName("UserCourseCount")
                .getOrCreate();

        JavaRDD<String> rawData = spark.read().textFile(args[0]).javaRDD();
        JavaRDD<UserCourse> data = rawData.map(
                new Function<String, UserCourse>() {
                    public UserCourse call(String line) {
                        String[] fields = line.split(",");
                        UserCourse userCourse = new UserCourse(fields[0], fields[1], fields[2],
                                fields[3], fields[4], fields[5], fields[6], fields[7], fields[8]);
                        return userCourse;
                    }
                });
        JavaPairRDD<String, Integer> records_JPRDD =
                data.mapToPair(new PairFunction<UserCourse, String, Integer>() {
                    public Tuple2<String, Integer> call(UserCourse record) {
                        return new Tuple2<String, Integer>(record.getUid(), 1);
                    }
                });

        JavaPairRDD<String, Integer> counts = records_JPRDD.reduceByKey((i1, i2) -> i1 + i2);

        List<Tuple2<String, Integer>> output = counts.collect();
        for (Tuple2<?, ?> tuple : output) {
            System.out.println(tuple._1 + ": " + tuple._2());
        }
        spark.stop();
    }
}

class UserCourse {
    private String uid;
    private String nickname;
    private String birthdate;
    private String gender;
    private String district;
    private String lastLoginTime;
    private String termId;
    private String courseId;
    private String selectDate;

    public UserCourse(String uid, String nickname, String birthdate, String gender, String district,
                      String lastLoginTime, String termId, String courseId, String selectDate) {
        this.uid = uid;
        this.nickname = nickname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.district = district;
        this.lastLoginTime = lastLoginTime;
        this.termId = termId;
        this.courseId = courseId;
        this.selectDate = selectDate;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getSelectDate() {
        return selectDate;
    }

    public void setSelectDate(String selectDate) {
        this.selectDate = selectDate;
    }
}





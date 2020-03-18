package com.zyb.mini.mall.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.zyb.mini.mall.config.OSSConfig;
import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.exception.ApiException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * java8 time包
 * 时间操作工具类
 *
 * @author tanxin
 * @date 2019/5/8
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {
    /**
     * 获取当前系统日期时间
     *
     * @return
     */
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前系统日期
     *
     * @return
     */
    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    /**
     * 获取本周第一天的日期时间
     * 星期1开始
     *
     * @param ldt 日期时间
     * @return
     */
    public static LocalDateTime getWeekDayStart(LocalDateTime ldt) {
        // 获取今天是本周第几天
        int dayOfWeek = ldt.getDayOfWeek().getValue();
        // 本周第一天
        LocalDateTime time = ldt.plusDays(-dayOfWeek);
        LocalDateTime startTime = LocalDateTime.of(time.getYear(), time.getMonthValue(), time.getDayOfMonth(), 00, 00, 00);
        return startTime;
    }

    /**
     * 获取下周第一天的日期时间
     * 星期1开始
     *
     * @param ldt 日期时间
     * @return
     */
    public static LocalDateTime getWeekDayEnd(LocalDateTime ldt) {
        // 获取今天是本周第几天
        int dayOfWeek = ldt.getDayOfWeek().getValue();
        // 本周最后一天
        LocalDateTime time = ldt.plusDays((7 - dayOfWeek));
        LocalDateTime endTime = LocalDateTime.of(time.getYear(), time.getMonthValue(), time.getDayOfMonth(), 00, 00, 00);
        return endTime;
    }


    /**
     * 获取本月第一天
     *
     * @param ldt 日期时间
     * @return
     */
    public static LocalDateTime getMonthDayStart(LocalDateTime ldt) {
        LocalDateTime startTime = LocalDateTime.of(ldt.getYear(), ldt.getMonthValue(), 1, 00, 00, 00);
        return startTime;
    }

    /**
     * 获取下个月的第一天
     * 上个月为12月则为明年的第一天
     *
     * @param ldt
     * @return
     */
    public static LocalDateTime getMonthDayEnd(LocalDateTime ldt) {
        int monthNum = ldt.getMonthValue();
        if (monthNum == 12) {
            return LocalDateTime.of(ldt.getYear() + 1, 1, 1, 00, 00, 00);
        }
        LocalDateTime endTime = LocalDateTime.of(ldt.getYear(), ldt.getMonthValue() + 1, 1, 00, 00, 00);
        return endTime;
    }

    /**
     * 获取本季度第一天的日期时间
     *
     * @param ldt 日期时间
     * @return
     */
    public static LocalDateTime getQuarterDayStart(LocalDateTime ldt) {
        int monthNum = ldt.getMonthValue();
        int startMonth = 1;
        if (1 <= monthNum && monthNum < 4) {
            startMonth = 1;
        }
        if (4 <= monthNum && monthNum < 7) {
            startMonth = 4;
        }
        if (7 <= monthNum && monthNum < 10) {
            startMonth = 7;
        }
        if (10 <= monthNum) {
            startMonth = 10;
        }

        LocalDateTime startTime = LocalDateTime.of(ldt.getYear(), startMonth, 1, 00, 00, 00);
        return startTime;
    }

    /**
     * 获取本季度的下个季度第一天的日期时间
     * Q4季度取到的为下一年的第一天
     *
     * @param ldt 日期时间
     * @return
     */
    public static LocalDateTime getQuarterDayEnd(LocalDateTime ldt) {
        int monthNum = ldt.getMonthValue();
        int endMonth = 4;
        if (1 <= monthNum && monthNum < 4) {
            endMonth = 4;
        }
        if (4 <= monthNum && monthNum < 7) {
            endMonth = 7;
        }
        if (7 <= monthNum && monthNum < 10) {
            endMonth = 10;
        }
        if (10 <= monthNum) {
            return LocalDateTime.of(ldt.getYear() + 1, 1, 1, 00, 00, 00);
        }
        LocalDateTime endTime = LocalDateTime.of(ldt.getYear(), endMonth, 1, 00, 00, 00);
        return endTime;
    }

    /**
     * 获取本年第一天
     *
     * @param ldt 日期时间
     * @return
     */
    public static LocalDateTime getYearDayStart(LocalDateTime ldt) {
        LocalDateTime startTime = LocalDateTime.of(ldt.getYear(), 1, 1, 00, 00, 00);
        return startTime;
    }

    /**
     * 获取下一年的第一天
     *
     * @param ldt 日期时间
     * @return
     */
    public static LocalDateTime getYearDayEnd(LocalDateTime ldt) {
        LocalDateTime endTime = LocalDateTime.of(ldt.getYear() + 1, 1, 1, 00, 00, 00);
        return endTime;
    }

    /**
     * 获取2个时间的时间差
     * (毫秒数)
     *
     * @param ldt  时间1
     * @param ldt2 时间2
     * @return 毫秒数
     */
    public static Long getTimeDiff(LocalDateTime ldt, LocalDateTime ldt2) {
        Duration duration = Duration.between(ldt, ldt2);
        return duration.toMillis();
    }

    /**
     * 获取2个时间的时间差
     * (秒数)
     *
     * @param ldt  时间1
     * @param ldt2 时间2
     * @return
     */
    public static Long getTimeDiffBySecond(LocalDateTime ldt, LocalDateTime ldt2) {
        Duration duration = Duration.between(ldt, ldt2);
        return duration.getSeconds();
    }

    /**
     * 获取结束时间和当前时间的时间差
     * (秒数)
     *
     * @param endTime 结束时间
     * @return
     */
    public static Long getTimeDiffBySecond(LocalDateTime endTime) {
        Long nowSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        Long endSecond = endTime.toEpochSecond(ZoneOffset.of("+8"));
        return endSecond - nowSecond;
    }

    /**
     * 获取秒数
     *
     * @param now
     * @return
     */
    public static Long getSecond(LocalDateTime now) {
        return now.toEpochSecond(ZoneOffset.of("+8"));
    }

    /**
     * 获取当前时间是今天得低多少min
     *
     * @param now
     * @return
     */
    public static int getMinuteByDay(LocalDateTime now) {
        return now.getHour() * 60 + now.getMinute();
    }

    /**
     * 获取这个月最后一天
     *
     * @param localDate 这个月开始第一天
     * @return 这个月最后一天
     */
    public static LocalDate getLocalDateByMonthLastDay(LocalDate localDate) {
        return localDate.plusMonths(1).plusDays(-1);
    }

    /**
     * 时间搓转time
     *
     * @param timestamp 时间戳
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime timestamp2LocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneOffset.of("+8"));
    }


    /**
     * date转 localDateTime
     *
     * @param date {@link Date}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.of("+8"));
        return localDateTime;
    }

    /**
     * 将相差的毫秒数转成 HH：mm：ss 的 形式
     *
     * @param millis 毫秒数
     * @return
     */
    public static String MillisToHmS(Long millis) {
        // 获得小时数
        Long h = millis / (1000 * 60 * 60);
        Long m = (millis % (1000 * 60 * 60)) / (1000 * 60);
        Long s = ((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000;
        String hStr = h.toString().length() < 2 ? "0" + h.toString() : h.toString();
        String mStr = m.toString().length() < 2 ? "0" + m.toString() : m.toString();
        String sStr = s.toString().length() < 2 ? "0" + s.toString() : s.toString();
        return hStr + ":" + mStr + ":" + sStr;
    }

    public static void main(String[] args) {
//        LocalDateTime ldt = LocalDateTime.of(2019, 3, 10, 15, 22, 36);
//        LocalDateTime ldt2 = LocalDateTime.of(2019, 3, 17, 18, 32, 46);
//        Long l = DateUtils.getTimeDiff(ldt, ldt2);
//        System.out.println(l);
//        System.out.println(DateUtils.MillisToHmS(l));
        DateUtils dateUtils  =new DateUtils();
        dateUtils.deleteImage();
    }

    @Transactional
    public void deleteImage() {
        String endPoint = OSSConfig.END_POINT;
        String accessKeyId = OSSConfig.ACCESS_KEY_ID;
        String accessKeySecret = OSSConfig.ACCESS_KEY_SECRET;
        String bucketName = OSSConfig.BUCKET;
        OSSClient client = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        List<String> keys = new ArrayList<String>();
        keys.add("cs/11.jpg");
        keys.add("cs/5a27cf8975cb5.jpg");
        DeleteObjectsResult deleteObjectsResult = client.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        client.shutdown();
    }

    /**
     * 获取两个日期间隔的所有日期
     *
     * @param startTime 格式必须为'2019-09-26'
     * @param endTime   格式必须为'2019-09-26'
     * @return
     */
    public static List<String> getBetweenDate(String startTime, String endTime) {
        List<String> list = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(startTime);
        LocalDate endDate = LocalDate.parse(endTime);

        if (startDate.isEqual(endDate)) {
            list.add(startDate.toString());
            return list;
        }
        if (startDate.isAfter(endDate)) {
            throw new ApiException(Status.REQUEST_BAD, "开始时间不能大于结束时间");
        }

        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        Stream.iterate(startDate, d -> {
            return d.plusDays(1);
        }).limit(distance + 1).forEach(f -> {
            list.add(f.toString());
        });
        return list;
    }
}

package cool.scx.lab.reflect_and_invoke;

import cool.scx.common.util.StopWatch;

import java.sql.SQLException;

/**
 * <p>SystemConfigService class.</p>
 *
 * @author scx567888
 * @version 1.0.0
 */

public class SystemConfigService {
    public SystemConfigService() throws SQLException {
        for (int i = 0; i < 9; i++) {
            test(i);
        }
    }

    public static void test(int j) throws SQLException {
//        var queryRunner = new QueryRunner(ScxContext.dao().dataSource());
//        List<?> list1=null;
//        List<?> list2=null;
//        var sss= ofBeanList(Student.class);
//       var bbb= new BeanListHandler<>(Student.class);
//
//        StopWatch.start("123"+j);
//        for (int i = 0; i < 9999; i++) {
//            list2 = queryRunner.query("""
//      SELECT name, phone_number AS phoneNumber, bind_open_id_list AS bindOpenIDList, class_hour AS classHour, recharged_class_hour AS rechargedClassHour, gift_class_hour AS giftClassHour, used_class_hour AS usedClassHour, student_source AS studentSource, id, created_date AS createdDate, updated_date AS updatedDate FROM rn_student
//                """,bbb);
//        }
//         var s= StopWatch.stopToMillis("123"+j);
//        StopWatch.start("456"+j);
//        for (int i = 0; i < 9999; i++) {
//            list1 = sqlRunner().query(ofNormal("""
//             SELECT name, phone_number AS phoneNumber, bind_open_id_list AS bindOpenIDList, class_hour AS classHour, recharged_class_hour AS rechargedClassHour, gift_class_hour AS giftClassHour, used_class_hour AS usedClassHour, student_source AS studentSource, id, created_date AS createdDate, updated_date AS updatedDate FROM rn_student
//                """),sss);
//        }
//        System.out.println(s);
//        System.out.println(StopWatch.stopToMillis("456" + j));
//        System.out.println(list1);
//        System.out.println(list2);
    }
}

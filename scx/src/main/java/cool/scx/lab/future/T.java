package cool.scx.lab.future;

/**
 * todo 参考 vertx 的Future 但是要实现 remove 监听器的功能
 */
public class T {

    public static void main(String[] args) {
        Promise<String> promise = Promise.promise();
        promise.success("123");
        promise.onSuccess(c -> {
            System.out.println(c);
        });
        
//        io.vertx.core.Promise.promise();
//        s.complete();
//        s.fail();
//        var a=new Promise<>();
//        
//        a.onSuccess((s)->{
//            System.out.println(s); 
//        });


    }

}

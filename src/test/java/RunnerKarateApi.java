import com.intuit.karate.junit5.Karate;

public class RunnerKarateApi {

    @Karate.Test
    Karate testAll() {
        //"classpath:karateApi/order.feature"
        return Karate.run("classpath:karateApi");
    }
}
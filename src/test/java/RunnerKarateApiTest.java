import com.intuit.karate.junit5.Karate;

public class RunnerKarateApiTest {

    @Karate.Test
    Karate testAll() {
        return Karate.run("classpath:karateApi");
    }
}
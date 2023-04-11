package shop.mtcoding.securityapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnvVarTest {
    
    @Value("${meta.name}")
    private String name;

    @Test
    public void property_test(){
        String name = System.getProperty("meta.name");
        System.out.println("RESULT : " + name);
    }
    @Test
    public void secret_test(){
        String key = System.getenv("HS512_SECRET");
        System.out.println("KEY : " + key);
    }
}

package tech.gengsa.testrediscache;

import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableCaching
public class TestRedisCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestRedisCacheApplication.class, args);
    }

    @Bean
    CommandLineRunner test(Test test, Test2 test2) {
        return (args) -> {
            System.out.println("测试 Map<String, String>");
            System.out.println(test.get());
            System.out.println(test.getFromCache());

            System.out.println("测试 Map<Integer, String>");
            var data = test2.getFromCache();
            System.out.println(data);
            for (Integer key : data.keySet()) {
                System.out.println(key);
            }
            var cacheData = test2.getFromCache();
            System.out.println(cacheData);
            for (Integer key : cacheData.keySet()) {
                System.out.println(key);
            }
        };
    }

    @Component
    class Test {
        public Map<String, String> get() {
            return Map.of("a", "A", "b", "B");
        }

        @Cacheable(value = "string-to-string-map")
        public Map<String, String> getFromCache() {
            return Map.of("a", "A", "b", "B");
        }
    }

    @Component
    class Test2 {
        public Map<Integer, String> get() {
            return Map.of(Integer.valueOf(1), "A", Integer.valueOf(2), "B");
        }

        @Cacheable(value = "integer-to-string-map")
        public Map<Integer, String> getFromCache() {
            return Map.of(Integer.valueOf(1), "A", Integer.valueOf(2), "B");
        }
    }

}

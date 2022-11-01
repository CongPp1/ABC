package com.example.demo.Database;

import com.example.demo.Entity.Product;
import com.example.demo.Repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration // Cac method trong class co annotation nay se chay ngay khi app chay
public class Database {
    //Now connect with mysql using JPA
/*
docker run -d --rm --name mysql-spring-boot-tutorial \
-e MYSQL_ROOT_PASSWORD=123456 \
-e MYSQL_USER=hoangnd \
-e MYSQL_PASSWORD=123456 \
-e MYSQL_DATABASE=test_db \
-p 3309:3306 \
--volume mysql-spring-boot-tutorial-volume:/var/lib/mysql \
mysql:latest

mysql -h localhost -P 3309 --protocol=tcp -u hoangnd -p

* */
    private static final Logger logger =  LoggerFactory.getLogger(Database.class); // Thay the cho System.out.println();

    @Bean // chay method ngay khi app duoc chay
    CommandLineRunner initDB(ProductRepository productRepository){ // Tao ra 1 so du lieu fake ban dau
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                Product product1 = new Product( "Ipad Air 2022", 2022, 500.2,"");
//                Product product2 = new Product( "Ipad Pro M1", 2022, 1000.2,"");
////                System.out.println("Insert data" + productRepository.save(product1)); // Insert ban ghi nay vao database
////                System.out.println("Insert data" + productRepository.save(product2)); // Insert ban ghi nay vao database
//                logger.info("Insert data" + productRepository.save(product1));
//                logger.info("Insert data" + productRepository.save(product2));
            }
        };
    }
}

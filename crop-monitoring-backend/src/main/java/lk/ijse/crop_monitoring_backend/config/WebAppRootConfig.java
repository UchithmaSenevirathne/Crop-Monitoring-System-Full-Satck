package lk.ijse.crop_monitoring_backend.config;

import jakarta.persistence.EntityManagerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "lk.ijse.crop_monitoring_backend")
@EnableJpaRepositories(basePackages = "lk.ijse.crop_monitoring_backend")
@EnableTransactionManagement
public class WebAppRootConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // Spring Data JPA repositories using JavaConfig

    @Bean
    public DataSource dataSource() {

        var dbms = new DriverManagerDataSource();
        dbms.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dbms.setUrl("jdbc:mysql://localhost:3306/crop_monitoring?createDatabaseIfNotExist=true");
        dbms.setUsername("root");
        dbms.setPassword("1234");
        return dbms;
    }

    //entity manager hadanna
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("lk.ijse.crop_monitoring_backend.entity");
        factory.setDataSource(dataSource());
        return factory;
    }

    //transaction walata
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}

package com.info.demo_batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {

    // 1. ItemReader: Read from a CSV file called 'sample-data.csv'
    @Bean
    public FlatFileItemReader<User> reader() {
        return new FlatFileItemReaderBuilder<User>()
                .name("userItemReader")
                .resource(new ClassPathResource("sample-data.csv")) // File in src/main/resources
                .delimited()
                .names("name", "age") // Maps CSV columns to User object fields
                .targetType(User.class)
                .build();
    }

    // 2. ItemProcessor: Simple processor to transform the data
    @Bean
    public UserItemProcessor processor() {
        return new UserItemProcessor();
    }

    // 3. ItemWriter: Write to a database table called 'users'
    @Bean
    public JdbcBatchItemWriter<User> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<User>()
                .dataSource(dataSource)
                .sql("INSERT INTO users (name, age) VALUES (:name, :age)")
                .beanMapped() // Uses Java Bean properties (getters) to map to SQL parameters (:name, :age)
                .build();
    }

    // 4. Define the Step
    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      FlatFileItemReader<User> reader, UserItemProcessor processor, JdbcBatchItemWriter<User> writer) {
        return new StepBuilder("step1", jobRepository)
                .<User, User>chunk(10, transactionManager) // Process data in chunks of 10 items
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    // 5. Define the Job
    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .start(step1) // You can flow to other steps with .next(step2)
                .build();
    }
}

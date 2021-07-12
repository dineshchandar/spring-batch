package com.spring.batch.config;

import com.spring.batch.listener.*;
import com.spring.batch.model.Birthday;
import com.spring.batch.repository.BirthdayRepository;
import com.spring.batch.util.CustomItemProcessor;
import com.spring.batch.util.CustomItemWriter;
import com.spring.batch.util.CustomRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class BirthdayJobConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Value("${BASE_URL}")
    String baseUrl;

    @Value("${URI}")
    String uri;

    @Value("${CHUNK_SIZE}")
    Integer chunkSize;

    @Value("${FETCH_SIZE}")
    Integer fetchSize;

    @Value("${PAGE_SIZE}")
    Integer pageSize;

    @Value("${MAX_ITEM_COUNT}")
    Integer maxItemCount;

    @Value("${WHERE_CLAUSE}")
    String whereClause;

    @Value("${THREAD_SLEEP_TIME}")
    Integer threadSleepTime;

    @Autowired
    BirthdayRepository birthdayRepository;

    @Autowired
    private DataSource ds;

    @Bean
    public Job BirthdayJob() throws Exception {

        return jobBuilderFactory.get("ReminderJob").incrementer(new RunIdIncrementer())
                                .listener(new JobCompletionListener()).preventRestart()
                                .flow(stepMonthlyReminder()).end().build();
    }

    @Bean
    public Step stepMonthlyReminder() throws Exception {

        return stepBuilderFactory
                .get("stepMonthlyReminder").<Birthday, Birthday>chunk(chunkSize)
                .reader(pagingItemReader())
                .processor(new CustomItemProcessor(baseUrl, uri, birthdayRepository))
                .writer(new CustomItemWriter(ds)).listener(new CustomChunkListener(threadSleepTime))
//                .listener(new ReaderListener())
//                .listener(new WriterListener())
//                .listener(new ProcessListener())
                .listener(new StepListener()).build();

    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<Birthday> pagingItemReader() throws Exception {
        JdbcPagingItemReader<Birthday> reader = new JdbcPagingItemReader<>();

        reader.setDataSource(this.ds);
        reader.setFetchSize(fetchSize);
        reader.setPageSize(pageSize);
        if (maxItemCount > 0) {
            reader.setMaxItemCount(maxItemCount);
        }

        reader.setRowMapper(new CustomRowMapper());

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("email_id", Order.ASCENDING);

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause("from birthdays");
        queryProvider.setSortKeys(sortKeys);
        queryProvider.setWhereClause(whereClause);

        reader.setQueryProvider(queryProvider);
        reader.setSaveState(false);
        reader.afterPropertiesSet();

        return reader;
    }
}

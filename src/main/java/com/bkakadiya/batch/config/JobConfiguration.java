package com.bkakadiya.batch.config;

import com.bkakadiya.batch.entity.*;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.orm.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.*;

import javax.sql.*;

@Configuration
public class JobConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;

	private int chunkSize = 10000;

	/*

	@Autowired
	@Qualifier("snowflakeDatasource")
	private DataSource snowflakeDatasource;


	@Bean
	public FlatFileItemReader<Customer> customerItemReader() {
		FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
		reader.setLinesToSkip(1);
		reader.setResource(new ClassPathResource("/csv/customer.csv"));

		DefaultLineMapper<Customer> customerLineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(new String[] {"C_CUSTKEY","C_NAME","C_ADDRESS","C_NATIONKEY","C_PHONE","C_ACCTBAL","C_MKTSEGMENT","C_COMMENT"});

		customerLineMapper.setLineTokenizer(tokenizer);
		customerLineMapper.setFieldSetMapper(new CustomerFieldSetMapper());
		customerLineMapper.afterPropertiesSet();
		reader.setLineMapper(customerLineMapper);
		return reader;
	}


	@Bean
	public JdbcPagingItemReader snowFlakeSampleCustomerItemReader(DataSource dataSource, PagingQueryProvider queryProvider) {
		Map<String, Object> parameterValues = new HashMap<>();

		return new JdbcPagingItemReaderBuilder<Customer>()
				.name("customerReader")
				.dataSource(dataSource)
				.queryProvider(queryProvider)
				.parameterValues(parameterValues)
				.rowMapper(new CustomerRowMapper())
				.pageSize(chunkSize)
				.build();
	}

	@Bean
	public SqlPagingQueryProviderFactoryBean queryProvider() {
		SqlPagingQueryProviderFactoryBean provider = new SqlPagingQueryProviderFactoryBean();

		provider.setSelectClause("select C_CUSTKEY,C_NAME,C_ADDRESS,C_NATIONKEY,C_PHONE,C_ACCTBAL,C_MKTSEGMENT,C_COMMENT ");
		provider.setFromClause("from customer");

		return provider;
	}


	*/



	@Autowired
	@Qualifier("snowflakeEntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean snowflakeEntityManagerFactory;

	@Bean
	public JpaPagingItemReader snowFlakeSampleCustomerItemReader() {

		JpaNativeQueryProvider<Customer> queryProvider= new JpaNativeQueryProvider<>();
		JpaPagingItemReader<Customer> reader = new JpaPagingItemReader<>();

		try {
			queryProvider.setSqlQuery("select C_CUSTKEY,C_NAME,C_ADDRESS,C_NATIONKEY,C_PHONE,C_ACCTBAL,C_MKTSEGMENT,C_COMMENT from CUSTOMER ");
			queryProvider.setEntityClass(Customer.class);
			queryProvider.afterPropertiesSet();

			//reader.setParameterValues(Collections.<String, Object>singletonMap("limit", chunkSize));
			reader.setEntityManagerFactory(snowflakeEntityManagerFactory.getObject());
			reader.setPageSize(chunkSize);
			reader.setQueryProvider(queryProvider);
			reader.afterPropertiesSet();
			reader.setSaveState(true);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return reader;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public JdbcBatchItemWriter<Customer> customerItemWriter() {
		JdbcBatchItemWriter<Customer> itemWriter = new JdbcBatchItemWriter<>();

		itemWriter.setDataSource(this.dataSource);
		itemWriter.setSql("INSERT INTO CUSTOMER VALUES (:id, :name, :address, :nationKey, :phone, :accountBal, :marketSegment, :comment)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
		itemWriter.afterPropertiesSet();

		return itemWriter;
	}


	@Bean
	public Step step1() throws Exception {

		return stepBuilderFactory.get("step1")
				.<Customer, Customer>chunk(chunkSize)
				//.reader(snowFlakeSampleCustomerItemReader(snowflakeDatasource, queryProvider().getObject()))
				.reader(snowFlakeSampleCustomerItemReader())
				.writer(customerItemWriter())
				.build();
	}

	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("job")
				.start(step1())
				.build();
	}
}
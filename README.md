# Snowflake-spring-batch-rdbms
This sample spring batch pulls data from Snowflake and saves it into RDBMS (H2)

1. Pull data from Snowflake Sample in chunks 
2. Saves them in to RDBMS (H2 for this sample)

# Snowflake setup and Details that you will need
1. You will need Snowflake account. You can request trial if you don't have from your org. 
2. JDBC URL will need following details
   1. Account id: Once you login to snowflake web console, you will see that after region name. if it shows eu-west-1/mx01588, that indicates your account id is **mx01588.eu-west-1**  note that account id should include region name as well
   2. Warehouse: Name of warehouse. You can find it from web console using Admin > Warehouses
   3. Username and password: This is your account details.

# Change application.properties
Update **app.datasource.snowflake.datasource.url** into application.properties with above details of snowflake

# Create DB Table in H2
Create Customer table on H2 if its not there from console at t http://localhost:8080/h2-console/

`
create or replace table CUSTOMER (
C_CUSTKEY NUMBER(38,0),
C_NAME VARCHAR(25),
C_ADDRESS VARCHAR(40),
C_NATIONKEY NUMBER(38,0),
C_PHONE VARCHAR(15),
C_ACCTBAL NUMBER(12,2),
C_MKTSEGMENT VARCHAR(10),
C_COMMENT VARCHAR(117)
);`

# Run
1. Run SnowflakeSpringBatchRdbmsApplication. 
2. If everything is configured properly then you should be able to see that application is pulling data from Snowflake_sample_data > TPCH_SF1 > Customer and placing it in to H2 Customer table.
3. Open H2 Console at http://localhost:8080/h2-console/ You should be able to see data into Customer table. 
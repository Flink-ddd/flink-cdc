Flink CDC (Change Data Capture) is a real-time data synchronization tool based on Apache Flink. It is designed to capture and process database change data and meet the high requirements for data real-time and consistency in big data processing scenarios. Flink CDC emerged to solve the following core needs:

Real-time data synchronization: In big data scenarios, enterprises need to synchronize incremental changes in the database to data warehouses, data lakes, or stream processing systems in real time to ensure the timeliness and consistency of data.

Stream-batch integration: With the increasing demand for stream processing, Flink has become a high-performance stream-batch processing framework. Through CDC, real-time data capture and streaming processing of the database can be achieved, further enriching Flink's application scenarios.

Avoid the bottleneck of traditional data synchronization: Traditional data synchronization methods, such as scheduled tasks or batch processing, have major shortcomings in real-time performance and efficiency. CDC can directly capture the change data of the database in a streaming manner, which is significant. Reduced latency and computational costs.

This project currently supports capturing MySQL and MongoDB database and writing the changed data to kafka, other features include data analysis and trying flink-SQL methods. in progressing..... 

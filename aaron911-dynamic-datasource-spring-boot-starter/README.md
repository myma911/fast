##多数据源的配置，需要引用aaron911-dynamic-datasource-spring-boot-starter
#dynamic:
#  datasource:
#    slave1:
#      driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver
#      url: jdbc:sqlserver://localhost:1433;DatabaseName=aaron911-fast
#      username: sa
#      password: 123456
#    slave2:
#      driver-class-name: org.postgresql.Driver
#      url: jdbc:postgresql://localhost:5432/aaron911-fast
#      username: root
#      password: 123456
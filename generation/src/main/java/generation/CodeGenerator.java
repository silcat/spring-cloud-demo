package generation;

/**
 * 代码生成器，根据数据表名称生成对应的Model、Mapper、Service、Controller简化开发。
 */
public class CodeGenerator {

    public static void main(String[] args) {
        genCode("bank");
    }

    private static void genCode(String... tableNames) {

        CodeGeneratorTemplate build = CodeGeneratorTemplate.builder()
                .MODULE_PROJECT_NAME("client-one")
                .MODEL_PROJECT_NAME("common")
                .BASE_COMMON_PACKAGE("com.example.common")
                .BASE_MODULE_PACKAGE("com.example.clientone")
                .JDBC_USERNAME("admin")
                .JDBC_PASSWORD("admin")
                .JDBC_URL("jdbc:mysql://172.16.1.71:3306/rrd_server?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai")
                .build();
        build.genService(tableNames);
    }
}
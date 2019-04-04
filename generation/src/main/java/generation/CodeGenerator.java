package generation;

/**
 * 代码生成器，根据数据表名称生成对应的Model、Mapper、Service、Controller简化开发。
 */
public class CodeGenerator {

    public static void main(String[] args) {
        genCode("account_tbl");
    }

    private static void genCode(String... tableNames) {

        CodeGeneratorTemplate build = CodeGeneratorTemplate.builder()
                .MODULE_PROJECT_NAME("client-one")
                .MODEL_PROJECT_NAME("common")
                .BASE_COMMON_PACKAGE("com.example.common")
                .BASE_MODULE_PACKAGE("com.example.clientone")
                .JDBC_USERNAME("root")
                .JDBC_PASSWORD("root")
                .JDBC_URL("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai")
                .build();
        build.genService(tableNames);
        build.genModelAndMapper(tableNames);
    }
}
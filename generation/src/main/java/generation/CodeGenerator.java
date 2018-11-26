package generation;

/**
 * 代码生成器，根据数据表名称生成对应的Model、Mapper、Service、Controller简化开发。
 */
public class CodeGenerator {
    //JDBC配置，请修改为你项目的实际配置
    private static final String JDBC_URL = "jdbc:mysql://172.16.1.71:3306/rrd_server?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
    private static final String JDBC_USERNAME = "admin";
    private static final String JDBC_PASSWORD = "admin";
    private static String BASE_COMMON_PACKAGE = "com.example.common";//model对应的moduel基础包名称
    private static String BASE_MAPPER_PACKAGE = "com.example.clientone";//controller,service ,mapper对应的moduel基础包名称
    public static  String  MODULE_PROJECT_PATH = System.getProperty("user.dir")+"\\"+"service-a";//controller,service ,mapper对应的moduel
    public static  String  MODEL_PROJECT_PATH = System.getProperty("user.dir")+"\\"+"common";//model对应的moduel

    public static void main(String[] args) {
        genCode("bank");

    }

    private static void genCode(String... tableNames) {
        CodeGeneratorTemplate.BASE_COMMON_PACKAGE = BASE_COMMON_PACKAGE;
        CodeGeneratorTemplate.BASE_MODULE_PACKAGE = BASE_MAPPER_PACKAGE;
        CodeGeneratorTemplate.JDBC_URL = JDBC_URL;
        CodeGeneratorTemplate.JDBC_USERNAME = JDBC_USERNAME;
        CodeGeneratorTemplate.JDBC_PASSWORD = JDBC_PASSWORD;
        CodeGeneratorTemplate.MODEL_PROJECT_PATH = MODEL_PROJECT_PATH;
        CodeGeneratorTemplate.MODULE_PROJECT_PATH =MODULE_PROJECT_PATH;

        CodeGeneratorTemplate.genModelAndMapper(tableNames);

    }
}
package generation;

import freemarker.template.TemplateExceptionHandler;
import lombok.Builder;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 代码生成器，根据数据表名称生成对应的Model、Mapper、Service、Controller简化开发。
 */
public class CodeGeneratorTemplate {
    //JDBC配置，请修改为你项目的实际配置
    private   String  JDBC_URL;
    private   String  JDBC_USERNAME ;
    private   String  JDBC_PASSWORD ;
    private   String  MODULE_PROJECT_NAME ;//项目在硬盘上的基础路径
    private   String  MODEL_PROJECT_NAME ;//项目在硬盘上的基础路径
    private   String  BASE_COMMON_PACKAGE ;//项目基础包名称
    private   String  BASE_MODULE_PACKAGE ;//项目基础包名称
    private   String  JDBC_DIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private   String  MODULE_PROJECT_PATH ;//项目在硬盘上的基础路径
    private   String  MODEL_PROJECT_PATH ;//项目在硬盘上的基础路径
    private   String  JAVA_PATH = "/src/main/java"; //java文件路径
    private   String  RESOURCES_PATH = "/src/main/resources";//资源文件路径
    private   String  TEMPLATE_FILE_PATH = System.getProperty("user.dir")+"\\"+"generation"+ "/src/main/resources/template";//模板位置
    private   String  MODEL_PACKAGE ;//Model所在包
    private   String  MAPPER_PACKAGE;//Mapper所在包
    private   String  SERVICE_PACKAGE;//Service所在包
    private   String  SERVICE_IMPL_PACKAGE;//ServiceImpl所在包
    private   String  CONTROLLER_PACKAGE ;//Controller所在包
    private   String  MAPPER_INTERFACE_REFERENCE ;//Mapper插件基础接口的完全限定名

    private   String  PACKAGE_PATH_SERVICE;//生成的Service存放路径
    private   String  PACKAGE_PATH_SERVICE_IMPL;//生成的Service实现存放路径
    private   String  PACKAGE_PATH_CONTROLLER ;//生成的Controller存放路径

    private   String  AUTHOR = "CodeGenerator";//@author
    private   String  DATE = new SimpleDateFormat("yyyy/MM/dd").format(new Date());//@date

    @Builder
    public CodeGeneratorTemplate(String JDBC_URL, String JDBC_USERNAME, String JDBC_PASSWORD, String MODULE_PROJECT_NAME, String MODEL_PROJECT_NAME, String BASE_COMMON_PACKAGE, String BASE_MODULE_PACKAGE) {
        this.JDBC_URL = JDBC_URL;
        this.JDBC_USERNAME = JDBC_USERNAME;
        this.JDBC_PASSWORD = JDBC_PASSWORD;
        this.MODULE_PROJECT_NAME = MODULE_PROJECT_NAME;
        this.MODEL_PROJECT_NAME = MODEL_PROJECT_NAME;
        this.BASE_COMMON_PACKAGE = BASE_COMMON_PACKAGE;
        this.BASE_MODULE_PACKAGE = BASE_MODULE_PACKAGE;
       this. MODULE_PROJECT_PATH = System.getProperty("user.dir")+"\\"+MODULE_PROJECT_NAME;//项目在硬盘上的基础路径
       this. MODEL_PROJECT_PATH = System.getProperty("user.dir")+"\\"+MODEL_PROJECT_NAME;//项目在硬盘上的基础路径
       this. MODEL_PACKAGE = BASE_COMMON_PACKAGE + ".model."+moduleNameConvertMapping(MODULE_PROJECT_NAME);//Model所在包
       this. MAPPER_PACKAGE = BASE_MODULE_PACKAGE  + ".mapper";//Mapper所在包
       this. SERVICE_PACKAGE = BASE_MODULE_PACKAGE  + ".service";//Service所在包
       this. SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//ServiceImpl所在包
       this. CONTROLLER_PACKAGE = BASE_MODULE_PACKAGE  + ".web";//Controller所在包
       this. MAPPER_INTERFACE_REFERENCE = BASE_MODULE_PACKAGE+ ".core.Mapper";//Mapper插件基础接口的完全限定名
       this. PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);//生成的Service存放路径
       this. PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(SERVICE_IMPL_PACKAGE);//生成的Service实现存放路径
       this. PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);//生成的Controller存放路径
    }

    public void genModelAndMapper(String... tableNames) {
        Arrays.stream(tableNames)
                .forEach(tableName ->{
                    genModelAndMapper(tableName);
                });
    }
   public void genService(String... tableNames) {
        Arrays.stream(tableNames)
                .forEach(tableName ->{
                    genService(tableName);
                });
    }

   private  void genAll(String... tableNames) {
        Arrays.stream(tableNames)
                .forEach(tableName ->{
                    genModelAndMapper(tableName);
                    genService(tableName);
                    genController(tableName);
                });
    }

   private  void genModelAndMapper(String tableName) {
        Context context = new Context(ModelType.FLAT);
        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", MAPPER_INTERFACE_REFERENCE);
        context.addPluginConfiguration(pluginConfiguration);

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(MODEL_PROJECT_PATH + JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(MODEL_PACKAGE);
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(MODULE_PROJECT_PATH + RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(MODULE_PROJECT_PATH + JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();

            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<String>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }

        String modelName = tableNameConvertUpperCamel(tableName);
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");
    }

   private  void genService(String tableName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("baseModelPackage", BASE_COMMON_PACKAGE);
            data.put("baseModulePackage", BASE_MODULE_PACKAGE);
            data.put("baseModuleName", moduleNameConvertMapping(MODULE_PROJECT_NAME));
            File file = new File(MODULE_PROJECT_PATH+ JAVA_PATH + PACKAGE_PATH_SERVICE + modelNameUpperCamel + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("service.ftl").process(data,
                    new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Service.java 生成成功");

            File file1 = new File(MODULE_PROJECT_PATH  + JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + modelNameUpperCamel + "ServiceImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("service-impl.ftl").process(data,
                    new FileWriter(file1));
            System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Service失败", e);
        }
    }

   private  void genController(String tableName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            data.put("baseRequestMapping", tableNameConvertMappingPath(tableName));
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", BASE_COMMON_PACKAGE);

            File file = new File(MODEL_PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONTROLLER + modelNameUpperCamel + "Controller.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            //cfg.getTemplate("controller-restful.ftl").process(data, new FileWriter(file));
            cfg.getTemplate("controller.ftl").process(data, new FileWriter(file));

            System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Controller失败", e);
        }

    }

    private  freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private  String tableNameConvertLowerCamel(String tableName) {
        StringBuilder result = new StringBuilder();
        if (tableName != null && tableName.length() > 0) {
            tableName = tableName.toLowerCase();//兼容使用大写的表名
            boolean flag = false;
            for (int i = 0; i < tableName.length(); i++) {
                char ch = tableName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }

    private  String tableNameConvertUpperCamel(String tableName) {
        String camel = tableNameConvertLowerCamel(tableName);
        return camel.substring(0, 1).toUpperCase() + camel.substring(1);

    }

    private  String tableNameConvertMappingPath(String tableName) {
        tableName = tableName.toLowerCase();//兼容使用大写的表名
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }
    private  String moduleNameConvertMapping(String moduleName) {
        return moduleName.contains("-")? moduleName.replaceAll("-", "") : moduleName;
    }

    private  String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }
}

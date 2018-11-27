package ${baseModulePackage}.service.impl;

import ${baseModulePackage}.mapper.${modelNameUpperCamel}Mapper;
import ${baseModelPackage}.model.${modelNameUpperCamel};
import ${baseModulePackage}.service.${modelNameUpperCamel}Service;
import ${baseModelPackage}.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by ${author} on ${date}.
 */
@Service
@Transactional
public class ${modelNameUpperCamel}ServiceImpl extends AbstractService<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Service {
    @Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

}

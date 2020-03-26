package com.eugene.sumarry.jwtutil.processor;

import com.eugene.sumarry.jwtutil.anno.EnableJwtSign;
import com.eugene.sumarry.jwtutil.entry.JwtSign;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class ImportJWTRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String enableJwtSignAnno = EnableJwtSign.class.getName();

        if (importingClassMetadata.hasAnnotation(enableJwtSignAnno)) {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
            beanDefinition.setBeanClass(JwtSign.class);

            registry.registerBeanDefinition("jwtSign", beanDefinition);
        }
    }

}

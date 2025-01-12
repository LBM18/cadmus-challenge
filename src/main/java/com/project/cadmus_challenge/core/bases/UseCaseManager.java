package com.project.cadmus_challenge.core.bases;

import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UseCaseManager implements IUseCaseManager, BeanFactoryAware {
    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (AutowireCapableBeanFactory) beanFactory;
    }

    @Override
    public void prepare(UseCase<?> usecase) {
        this.beanFactory.autowireBean(usecase);
    }

    @Override
    public void complete(UseCase<?> usecase) {
    }

    @Override
    public void destroy(UseCase<?> usecase) {
        this.beanFactory.destroyBean(usecase);
    }
}

package ru.alex3koval.notificationService.configuration.di.appImplModule;

import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import ru.alex3koval.notificationService.configuration.AppEnvironment;
import ru.alex3koval.eventing.impl.factory.MessageListenerContainerFactory;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class AppImplModuleConfiguration {
    private final AppEnvironment env;

    /*@Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory("localhost");

        cf.setUsername(env.amqp().conn().username());
        cf.setPassword(env.amqp().conn().password());

        return cf;
    }*/

    @Bean
    Exchange fanoutExchange() {
        return ExchangeBuilder
            .fanoutExchange("fanoutExchange")
            .durable(true)
            .build();
    }

    @Bean
    MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter();
    }

    @Bean
    JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        AppEnvironment.Mailer mailerProps = env.mailer();

        mailSender.setHost(mailerProps.host());
        mailSender.setPort(mailerProps.port());
        mailSender.setUsername(mailerProps.username());
        mailSender.setPassword(mailerProps.password());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailerProps.protocol());

        return mailSender;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerClassLoaderConfig() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();

        configurer.setDefaultEncoding("UTF-8");

        return configurer;
    }

    @Bean
    MessageListenerContainerFactory messageListenerContainerFactory(
        ConnectionFactory connectionFactory
    ) {
        return new MessageListenerContainerFactory(connectionFactory);
    }
}

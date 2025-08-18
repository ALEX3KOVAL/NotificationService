package ru.alex3koval.notificationService.eventHandlingApp.appHook;

import org.springframework.amqp.core.MessageListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import ru.alex3koval.eventing.impl.manager.ListenerManager;
import ru.alex3koval.notificationService.configuration.vo.QueueVO;
import ru.alex3koval.notificationService.eventHandlingApp.listener.mail.MailHasBeenSentListener;
import ru.alex3koval.notificationService.eventHandlingApp.listener.mail.MailSendingHasBeenFailedListener;
import ru.alex3koval.notificationService.eventHandlingApp.listener.phone.OtpHasBeenSentViaPhoneListener;

public class ApplicationContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        registerListeners(
            event.getApplicationContext()
        );
    }

    private void registerListeners(ApplicationContext applicationContext) {
        ListenerManager listenerManager = applicationContext.getBean(ListenerManager.class);

        registerMailListeners(
            listenerManager,
            applicationContext
        );

        registerPhoneListeners(
            listenerManager,
            applicationContext
        );
    }

    private void registerMailListeners(
        ListenerManager listenerManager,
        ApplicationContext applicationContext
    ) {
        QueueVO queue = QueueVO.MAIL;

        registerListener(
            listenerManager,
            applicationContext,
            queue,
            MailHasBeenSentListener.class
        );

        registerListener(
            listenerManager,
            applicationContext,
            queue,
            MailSendingHasBeenFailedListener.class
        );
    }

    private void registerPhoneListeners(
        ListenerManager listenerManager,
        ApplicationContext applicationContext
    ) {
        QueueVO queue = QueueVO.PHONE;

        registerListener(
            listenerManager,
            applicationContext,
            queue,
            OtpHasBeenSentViaPhoneListener.class
        );
    }

    private <T extends MessageListener> void registerListener(
        ListenerManager listenerManager,
        ApplicationContext applicationContext,
        QueueVO queue,
        Class<T> listenerClazz
    ) {
        listenerManager.registerListener(
            queue.name(),
            applicationContext.getBean(listenerClazz),
            queue.getAmountOfConsumers()
        );
    }
}

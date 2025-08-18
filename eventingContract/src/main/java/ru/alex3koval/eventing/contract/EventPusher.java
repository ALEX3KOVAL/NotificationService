package ru.alex3koval.eventing.contract;

public interface EventPusher {
    void push(Event event) throws InterruptedException;
}

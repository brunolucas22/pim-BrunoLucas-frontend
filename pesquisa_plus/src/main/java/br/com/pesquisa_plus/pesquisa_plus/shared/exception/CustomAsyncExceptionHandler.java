package br.com.pesquisa_plus.pesquisa_plus.shared.exception;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

  @Override
  public void handleUncaughtException(
    Throwable throwable, Method method, Object... obj) {

      System.out.println("Exception message: " + throwable.getMessage() + ", Method name: " + method.getName());
      
  }
  
}
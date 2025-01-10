package br.com.andre.springJava.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);

}

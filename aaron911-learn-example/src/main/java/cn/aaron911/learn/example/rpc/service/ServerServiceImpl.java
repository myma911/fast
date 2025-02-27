package cn.aaron911.learn.example.rpc.service;

public class ServerServiceImpl implements IService {

    @Override
    public String hello(String name) {  
        return "Yo man Hello，I am" + name;  
    }  
}  
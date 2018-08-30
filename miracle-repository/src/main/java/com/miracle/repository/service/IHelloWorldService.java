package com.miracle.repository.service;

import com.miracle.repository.model.HelloWorld;

import java.util.List;

/**
 * HelloWorld示例业务处理接口
 * Created at 2018-08-27 23:54:26
 * @author Allen
 */
public interface IHelloWorldService {

    List<HelloWorld> listAll();
}

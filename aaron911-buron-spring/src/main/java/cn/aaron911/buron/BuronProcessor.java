package cn.aaron911.buron;

import javax.servlet.http.HttpServletRequest;


public interface BuronProcessor {

    BuronResponse process(HttpServletRequest request);
}

package cn.aaron911.buron;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0
 * 
 */
public interface BuronProcessor {

    BuronResponse process(HttpServletRequest request);
}

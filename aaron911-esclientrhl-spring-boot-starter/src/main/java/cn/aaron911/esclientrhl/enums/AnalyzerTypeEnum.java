package cn.aaron911.esclientrhl.enums;

/**
 * 
 * description: 分词器类型
 * 
 **/
public enum AnalyzerTypeEnum {
	
	/**
	 * 支持中文采用的方法为单字切分。他会将词汇单元转换成小写形式，并去除停用词和标点符号
	 */
    standard,
    
    /**
     * 首先会通过非字母字符来分割文本信息，然后将词汇单元统一为小写形式。该分析器会去掉数字类型的字符
     */
    simple,
    
    /**
     * 仅仅是去除空格，对字符没有lowcase化,不支持中文
     */
    whitespace,
    
    /**
     * StopAnalyzer的功能超越了SimpleAnalyzer，在SimpleAnalyzer的基础上增加了去除英文中的常用单词（如the，a等）
     */
    stop,
    keyword,
    pattern,
    fingerprint,
    
    /**
     * 语言分词器（英文）
     */
    english,
    
    /**
     * ik中文智能分词 https://github.com/medcl/elasticsearch-analysis-ik/
     */
    ik_smart,
    
    /**
     * ik中文分词
     */
    ik_max_word,
}

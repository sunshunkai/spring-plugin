package io.terminus.gaia.core.json;

import io.terminus.gaia.rule.RulesItem;

import java.util.List;
import java.util.Map;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
public interface Desensitizate {

     /**
      *
      * @param object
      * @param zClass
      * @return
      */
     Object process(Object object, Class zClass);
}

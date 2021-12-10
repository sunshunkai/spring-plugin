//package json;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.core.JsonFactory;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
//import io.terminus.gaia.bean.Book;
//import io.terminus.gaia.bean.User;
//import io.terminus.gaia.core.json.custome.CustomFilter;
//import io.terminus.gaia.core.json.fastjson.FastjsonDesensitizeFilter;
//import io.terminus.gaia.core.json.jackson.JacksonDesensitizeFilter;
//import io.terminus.gaia.core.json.jackson.SecurityMappingJsonFactory;
//import io.terminus.gaia.load.LoadRule;
//import io.terminus.gaia.load.ResourceLoadRule;
//import io.terminus.gaia.rule.RulesItem;
//import io.terminus.trantorframework.json.jackjson.OverrideAnySetterBeanDeserializerFactory;
//import io.terminus.trantorframework.json.jackjson.config.TrantorModule;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.BeanUtils;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author 孙顺凯（惊云）
// * @date 2021/11/14
// */
//public class FastJsonTest {
//
//    private LoadRule loadRule = new ResourceLoadRule();
//
//    private CustomFilter customFilter = new CustomFilter();
//
//    @Test
//    public void valueFilter() throws JsonProcessingException {
//        User user = new User();
//        user.setAge(24);
//        user.setName("zhangsan");
//        user.setId(1L);
//        user.setBaseName("baseName");
//
//        Book book = new Book();
//        book.setId(10L);
//        book.setBookName("JAVA开发");
////        book.setUser(user);
//
//        Book book1 = new Book();
//        book1.setId(11L);
//        book1.setBookName("MySQL 性能调优");
////        book1.setUser(user);
//
//        List<Book> bookList = new ArrayList<>();
//        bookList.add(book);
//        bookList.add(book1);
//
//        user.setBookList(bookList);
//
////        String a = JSON.toJSONString(user, new FastjsonDesensitizeFilter(rulesItem()), SerializerFeature.DisableCircularReferenceDetect);
////        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
////        String a = JSON.toJSONString(user);
//
////        Object o = JSONObject.toJSON(a);
////        System.out.print(a);
//
////        ObjectMapper objectMapper = new ObjectMapper();
//
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(String.class, new JacksonDesensitizeFilter());
////        objectMapper.registerModule(simpleModule);
//
////        String s = objectMapper.writeValueAsString(user);
//
////        User user1 = objectMapper.readValue(s, User.class);
////
////        System.out.println(s);
//
//
//        JsonFactory jsonFactory = new SecurityMappingJsonFactory();
//
////        ObjectMapper objectMapper1 = new ObjectMapper(
////                jsonFactory, null,
////                new DefaultDeserializationContext.Impl(OverrideAnySetterBeanDeserializerFactory.instance))
////                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
////                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
////                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
////                .registerModule(new JavaTimeModule());
//////                .registerModule(new TrantorModule());
////
////        objectMapper1.registerModule(simpleModule);
////
////        String s = objectMapper1.writeValueAsString(user);
////
////        System.out.print(s);
//
//    }
//
//    private Map<String,List<RulesItem>> rulesItem(){
//        Map<String,List<RulesItem>> rulesItem = new HashMap<>();
//        rulesItem.put("name",null);
//        rulesItem.put("bookName",null);
//        return rulesItem;
//    }
//}

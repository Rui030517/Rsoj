package com.yupi.onlineoj.judge.codesandbox;

import com.yupi.onlineoj.judge.codesandbox.impl.ExampleCodeBoxImpl;
import com.yupi.onlineoj.judge.codesandbox.model.ExecuteCodeReponse;
import com.yupi.onlineoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.onlineoj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodeSandBoxTest {

    @Value("${codesandbox.type:example}")
    private String type;

    @Test
    void executeCode() {
        CodeSandBox codeSandBox = new ExampleCodeBoxImpl();
        String code = "int main(){}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> input = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .input(input)
                .language(language)
                .build();

        ExecuteCodeReponse executeCodeReponse = codeSandBox.ExecuteCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeReponse);
    }

    @Test
    void executeCodeByProxy() {
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        String code = "public class Main {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "     int a = Integer.parseInt(args[0]);\n" +
                "     int b = Integer.parseInt(args[1]);\n" +
                "     System.out.println(\"结果：\" + (a + b));\n" +
                "    }\n" +
                "}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> input = Arrays.asList("10 10", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .input(input)
                .language(language)
                .build();

        ExecuteCodeReponse executeCodeReponse = codeSandBox.ExecuteCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeReponse);
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String type = scanner.next();
            //使用工厂模式，根据用户传入的字符串来生成对应的代码沙箱实现类
            CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
            String code = "int main(){}";
            String language = QuestionSubmitLanguageEnum.JAVA.getValue();
            List<String> input = Arrays.asList("1 2", "3 4");
            ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                    .code(code)
                    .input(input)
                    .language(language)
                    .build();

            ExecuteCodeReponse executeCodeReponse = codeSandBox.ExecuteCode(executeCodeRequest);
//            Assertions.assertNotNull(executeCodeReponse);
        }
    }
}
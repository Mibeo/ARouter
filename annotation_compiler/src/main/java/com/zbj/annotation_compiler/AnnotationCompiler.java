package com.zbj.annotation_compiler;

import com.google.auto.service.AutoService;
import com.zbj.annotation.BindPath;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * Created by bingjia.zheng on 2019/8/13.
 */
@AutoService(Processor.class)  //注册我们的注解处理器
public class AnnotationCompiler extends AbstractProcessor {
    //生成文件的对象
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    /**
     * 声明这个注解处理器要处理的注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }

    /**
     * 声明我们的注解处理器支持的java版本
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * 自动生成代码
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        //初始化数据
        Map<String, String> map = new HashMap<>();
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            //获取到key节点
            String key = typeElement.getAnnotation(BindPath.class).value();
            //获取到Activity的类对象的名字
            String activityName = typeElement.getQualifiedName().toString();
            map.put(key, activityName);
            //开始写文件
            if (map.size() > 0) {
                Writer writer = null;
                //创建一个新的类名
                String utilName = "ActivityUtil" + System.currentTimeMillis();
                //创建文件
                try {
                    JavaFileObject sourceFile = filer.createSourceFile("com.netease.util." + utilName);
                    writer = sourceFile.openWriter();
                    writer.write("package com.netease.util;\n");
                    writer.write("import com.zbj.arouter2.ARouter;\n" +
                            "import com.zbj.arouter2.IRouter;\n" +
                            "\n" +
                            "public class " + utilName + " implements IRouter {\n" +
                            "    @Override\n" +
                            "    public void putActivity() {");
                    Iterator<String> iterator = map.keySet().iterator();
                    while (iterator.hasNext()) {
                        String Key = iterator.next();
                        String activityname = map.get(Key);
                        writer.write("ARouter.getInstance().putActivity(\"" + Key + "\"," + activityname + ".class);\n");
                    }
                    writer.write("}\n}\n");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }
}

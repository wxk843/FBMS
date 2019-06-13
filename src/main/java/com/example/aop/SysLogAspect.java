package com.example.aop;

/**
 * @author deray.wang
 * @date 2019/5/7 16:40
 */

import com.example.annotation.MyLog;
import com.example.entity.SysLog;
import com.example.service.SysLogService;
import com.example.util.IpAdrressUtil;
import com.example.util.JacksonUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import net.sf.json.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * 系统日志：切面处理类
 */
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private SysLogService sysLogService;

    private static String[] types = { "java.lang.Integer", "java.lang.Double",
            "java.lang.Float", "java.lang.Long", "java.lang.Short",
            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
            "java.lang.String", "int", "double", "long", "short", "byte",
            "boolean", "char", "float" };

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation(com.example.annotation.MyLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        //保存日志
        SysLog sysLog = new SysLog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        sysLog.setAdminId((long) 1);

        //获取操作
//        Operation operation = method.getAnnotation(Operation.class);
//        if (operation != null) {
//            String value = operation.value();
//            sysLog.setOperation(value);//保存获取的操作
//        }
        //获取操作
        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog != null) {
            String value = myLog.value();
            sysLog.setTitle(value);//保存获取的操作
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();

        //获取请求的方法名
        //String methodName = method.getName();
        System.out.println(method.getName());
        String methodName = joinPoint.getSignature().getName();
        //sysLog.setMethod(className + "." + methodName);
        System.out.println(methodName);
        //请求的参数
        Object[] args = joinPoint.getArgs();
        String classType = joinPoint.getTarget().getClass().getName();
        System.out.println(classType);

        Class<?> clazz =null;
        try {
            clazz = Class.forName(classType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String clazzName = clazz.getName();
        String clazzSimpleName = clazz.getSimpleName();

        System.out.println(clazzName);
        System.out.println(clazzSimpleName);

        String[] paramNames = new String[]{};
        try {
            paramNames = getFieldsName(this.getClass(), clazzName, methodName);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        String logContent = writeLogInfo(paramNames, joinPoint);
        System.out.println(logContent);
        System.out.println(args);
        //将参数所在的数组转换成json
        String params = "";
        try {
            //JSON.toJSONString(request.getParameterMap(),SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue);
            params = JacksonUtil.obj2json(args);
            //params = Arrays.toString(args);

        } catch (Exception e) {
            e.printStackTrace();
        }
        sysLog.setContent(params);

        //请求的时间
        //sysLog.setCreateDate(new Date());

        //获取用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            sysLog.setUsername(authentication.getName());
        }

        //获取用户ip地址
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()) .getRequest();
        sysLog.setIp(IpAdrressUtil.getIpAdrress(request));

        //设置请求地址
        sysLog.setUrl(request.getRequestURI());
//        //获取浏览器信息
//        Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
//        //获取浏览器版本号
//        Version version = browser.getVersion(request.getHeader("User-Agent"));
//        String info = browser.getName() + "/" + version.getVersion();
//        System.out.println(info);
        sysLog.setUseragent(request.getHeader("User-Agent"));

        //开始调用时间
        // 计时并调用目标函数
        long start = System.currentTimeMillis();
//        //记录返回参数
//        Object result = pjp.proceed();
//        System.out.println(result);
        Long time = System.currentTimeMillis() - start;
//
//        //设置消耗总时间
        System.out.println(time);

        //调用service保存SysLog实体类到数据库
        sysLogService.saveLog(sysLog);
    }


    private static String writeLogInfo(String[] paramNames, JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        boolean clazzFlag = true;
        for(int k=0; k<args.length; k++){
            Object arg = args[k];
            sb.append(paramNames[k]+" ");
            // 获取对象类型
            String typeName = arg.getClass().getTypeName();

            for (String t : types) {
                if (t.equals(typeName)) {
                    sb.append("=" + arg+"; ");
                }
            }
            if (clazzFlag) {
                sb.append(getFieldsValue(arg));
            }
        }
        return sb.toString();
    }
    /**
     * 得到方法参数的名称
     * @param cls
     * @param clazzName
     * @param methodName
     * @return
     * @throws NotFoundException
     */
    private static String[] getFieldsName(Class cls, String clazzName, String methodName) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        //ClassClassPath classPath = new ClassClassPath(this.getClass());
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++){
            paramNames[i] = attr.variableName(i + pos);	//paramNames即参数名
        }
        return paramNames;
    }

    /**
     * 得到参数的值
     * @param obj
     */
    public static String getFieldsValue(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        String typeName = obj.getClass().getTypeName();
        for (String t : types) {
            if(t.equals(typeName))
                return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("【");
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                for (String str : types) {
                    if (f.getType().getName().equals(str)){
                        sb.append(f.getName() + " = " + f.get(obj)+"; ");
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append("】");
        return sb.toString();
    }
}

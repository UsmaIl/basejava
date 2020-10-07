import com.dofler.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume();
        Field firstFieldResume = resume.getClass().getDeclaredFields()[0];
        firstFieldResume.setAccessible(true);
        System.out.println(firstFieldResume.getName());
        System.out.println(firstFieldResume.get(resume));
        firstFieldResume.set(resume, "new uuid");
        System.out.println(firstFieldResume);

        Method methodToStringResume = resume.getClass().getMethod("toString");
        System.out.println(methodToStringResume.invoke(resume));
    }
}

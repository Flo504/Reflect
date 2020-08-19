package fr.flo504.reflect;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

public class Reflect {

    private Reflect() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    public static Class<?> getClass(String name){
        Objects.requireNonNull(name, "The class name can not be null");
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not find "+name+" class", e);
        }
    }

    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes){
        Objects.requireNonNull(clazz, "The class can not be null");
        try {
            return clazz.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes){
        Objects.requireNonNull(clazz, "The class can not be null");
        Objects.requireNonNull(methodName, "The method name can not be null");
        try{
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Field getField(Class<?> clazz, String fieldName){
        Objects.requireNonNull(clazz, "The class can not be null");
        Objects.requireNonNull(fieldName, "The field name can not be null");
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object newInstance(Constructor<?> constructor, Object... parameters){
        Objects.requireNonNull(constructor, "The constructor can not be null");
        try {
            return constructor.newInstance(parameters);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invoke(Method method, Object instance, Object... parameters){
        Objects.requireNonNull(method, "The method can not be null");
        try {
            return method.invoke(instance, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invokeStatic(Method method, Object... parameters){
        return invoke(method, null, parameters);
    }

    public static Object get(Field field, Object instance){
        Objects.requireNonNull(field, "The field can not be null");
        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getStatic(Field field){
        return get(field, null);
    }

    public static void set(Field field, Object instance, Object value){
        Objects.requireNonNull(field, "The field can not be null");
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setStatic(Field field, Object value){
        set(field, null, value);
    }

    public static boolean classExist(String className){
        return getOptionalClass(className).isPresent();
    }

    public static Optional<Class<?>> getOptionalClass(String className){
        Objects.requireNonNull(className, "The class name can not be null");

        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException ignored) {}

        return Optional.ofNullable(clazz);
    }

    public final static class Commons {
        
        
        public static final String VERSION_NAME;
        public static final int VERSION;
        public static final String MINECRAFT;
        public static final String CRAFTBUKKIT;
        
        static{
            final String versionName = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            final int version = Integer.parseInt(versionName.substring(1).split("_")[1]);
            final String npack = "net.minecraft.server." + versionName + ".";
            final String cpack = Bukkit.getServer().getClass().getPackage().getName() + ".";

            VERSION_NAME = versionName;
            VERSION = version;
            MINECRAFT = npack;
            CRAFTBUKKIT = cpack;
        }
        
    }

}

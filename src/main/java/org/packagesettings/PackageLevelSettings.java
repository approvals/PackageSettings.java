package org.packagesettings;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.spun.util.ObjectUtils;
import com.spun.util.io.StackElementLevelSelector;

public class PackageLevelSettings
{
  public static Map<String, Object> get()
  {
    Map<String, Object> settings = new HashMap<String, Object>();
    StackElementLevelSelector stack = new StackElementLevelSelector(1);
    try
    {
      HashSet<String> done = new HashSet<String>();
      StackTraceElement trace[] = new Error().getStackTrace();
      for (StackTraceElement element : trace)
      {
        String packageName = getNextLevel(element.getClassName());
        settings.putAll(getSettingsFor(packageName, done));
      }
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
    return settings;
  }
  private static Map<String, Object> getSettingsFor(String packageName, HashSet<String> done)
  {
    if (packageName == null || done.contains(packageName)) { return Collections.emptyMap(); }
    Map<String, Object> settings = new HashMap<String, Object>();
    settings.putAll(getSettingsFor(getNextLevel(packageName), done));
    try
    {
      Class<?> clazz = ObjectUtils.loadClass(packageName + ".PackageSettings");
      Field[] declaredFields = clazz.getDeclaredFields();
      for (Field field : declaredFields)
      {
        if (Modifier.isStatic(field.getModifiers()))
        {
          settings.put(field.getName(), getFieldValue(field));
        }
      }
    }
    catch (ClassNotFoundException e)
    {
      //Ignore
    }
    System.out.println("Settings for : " + packageName + "\n" + settings);
    done.add(packageName);
    return settings;
  }
  private static Settings getFieldValue(Field field)
  {
    try
    {
      return new Settings(field.get(null), field.getDeclaringClass().getName());
    }
    catch (Throwable t)
    {
      //ignore
    }
    return null;
  }
  public static String getNextLevel(String className)
  {
    int last = className.lastIndexOf(".");
    return (last < 0) ? null : className.substring(0, last);
  }
}

# PackageSettings
tl;dr package level settings in java

PackageSettings is a way to set default constants.

```java
package com.yourcompany.tests;
public class PackageSettings
{
  public static String defaultReporter = "ImageReporter";
}
```

If anything from com.yourcompany.tests calls you you can then access the settnigs
```java
PackageLevelSettings.get().get("defaultReport").getValue()); // ImageReporter
```

PackageSettings also collapses with the element of least surprise.

For example if you also had:
```java
package com.yourcompany;
public class PackageSettings
{
  public static String defaultReporter = "DiffReporter";
  public static String frontLoadedReporter = "JenkinsReporter";

}
```
Then you would have
```java
 Map<String, Settings> settings = PackageLevelSettings.get();
 settings.get("defaultReport").getValue()); // ImageReporter
 settings.get("frontLoadedReporter").getValue()); // JenkinsReporter
```

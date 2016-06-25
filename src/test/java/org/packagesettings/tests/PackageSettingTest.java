package org.packagesettings.tests;

import static org.junit.Assert.assertEquals;

import org.approvaltests.Approvals;
import org.junit.Test;
import org.packagesettings.PackageLevelSettings;

public class PackageSettingTest
{
  @Test
  public void testSettings() throws Exception
  {
    Approvals.verify(PackageLevelSettings.get());
  }
  @Test
  public void testGetPackageLevel() throws Exception
  {
    assertEquals("java.lang", PackageLevelSettings.getNextLevel("java.lang.String"));
    assertEquals(null, PackageLevelSettings.getNextLevel("java"));
  }
}
